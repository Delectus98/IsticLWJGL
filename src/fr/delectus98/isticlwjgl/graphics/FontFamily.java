package fr.delectus98.isticlwjgl.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import fr.delectus98.isticlwjgl.system.GlObject;

import static org.lwjgl.opengl.GL11.*;

/**
 * TTF font.
 * https://www.oreilly.com/library/view/learning-java-4th/9781449372477/ch20s06.html
 * @author TheBoneJarmer
 */
public class FontFamily extends GlObject implements ConstFontFamily {

    //Constants
    private static final Map<Integer,String> CHARS = new HashMap<Integer,String>() {{
        put(0, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        put(1, "abcdefghijklmnopqrstuvwxyz");
        put(2, "0123456789");
        put(3, "ÄÖÜäöüßéèçàùËë§`^~µ%¤£€@");
        put(4, " $+-*/=%\"'&_(),.;:?!\\|<>[]{}");
    }};

    //Variables
    private final Map<Integer,String> charset;//layout
    private java.awt.Font font;
    private FontMetrics fontMetrics;
    private Texture texture = null;


    //Getters
    public Set<Integer> getCharset() {
        return new HashSet<>(charset.values().stream().map(s -> s.chars().boxed().collect(Collectors.toList())).reduce((s0, s1) -> {s1.addAll(s0); return s1;}).orElse(new ArrayList<>()));
    }
    public float getFontImageWidth() {
        return (float) charset.values().stream().mapToDouble(e -> fontMetrics.getStringBounds(e, null).getWidth()).max().getAsDouble();
    }
    public float getFontImageHeight() {
        return (float) charset.keySet().size() * (this.getCharHeight());
    }
    public float getCharX(char c) {
        String originStr = charset.values().stream().filter(e -> e.contains("" + c)).findFirst().orElse("" + c);
        return (float) fontMetrics.getStringBounds(originStr.substring(0, originStr.indexOf(c)), null).getWidth();
    }
    public float getCharY(char c) {
        float lineId = (float) charset.keySet().stream().filter(i -> charset.get(i).contains("" + c)).findFirst().orElse(0);
        return this.getCharHeight() * lineId;
    }
    public float getCharWidth(int c) {
        return fontMetrics.charWidth(c);
    }
    public float getCharHeight() {
        return (float) (fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent());
    }

    //Conversions
    private ByteBuffer asByteBuffer(BufferedImage bufferedImage) {
        ByteBuffer byteBuffer;

        //Draw the characters on our image
        Graphics2D imageGraphics = (Graphics2D) bufferedImage.getGraphics();
        imageGraphics.setFont(font);
        imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        imageGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // draw every CHAR by line...
        imageGraphics.setColor(java.awt.Color.WHITE);
        charset.keySet().forEach(i -> imageGraphics.drawString(charset.get(i), 0, fontMetrics.getMaxAscent() + (this.getCharHeight() * i)));

        //Generate texture data
        int[] pixels = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0, bufferedImage.getWidth());
        byteBuffer = ByteBuffer.allocateDirect((bufferedImage.getWidth() * bufferedImage.getHeight() * 4));

        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                int pixel = pixels[y * bufferedImage.getWidth() + x];
                byteBuffer.put((byte) ((pixel >> 16) & 0xFF));   // Red component
                byteBuffer.put((byte) ((pixel >> 8) & 0xFF));    // Green component
                byteBuffer.put((byte) (pixel & 0xFF));           // Blue component
                byteBuffer.put((byte) ((pixel >> 24) & 0xFF));   // Alpha component. Only for RGBA
            }
        }

        byteBuffer.flip();

        return byteBuffer;
    }

    //Constructors
    public FontFamily(String path, float size) throws IOException {
        this(CHARS, path, size);
    }

    public FontFamily(Map<Integer,String> charset, String path, float size) throws IOException {
        this.charset = charset;

        try {
            this.font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
        } catch (FontFormatException e) {
            throw new IOException("Font must be True Type Font (*.ttf).");
        }

        BufferedImage bufferedImage;

        //Generate buffered image
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Graphics2D graphics = gc.createCompatibleImage(1, 1, Transparency.TRANSLUCENT).createGraphics();
        graphics.setFont(font);

        fontMetrics = graphics.getFontMetrics();
        bufferedImage = graphics.getDeviceConfiguration().createCompatibleImage((int) getFontImageWidth(),(int) getFontImageHeight(),Transparency.TRANSLUCENT);

        //Generate texture
        int fontTextureId = glGenTextures();

        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, fontTextureId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,(int) getFontImageWidth(),(int) getFontImageHeight(),0, GL_RGBA, GL_UNSIGNED_BYTE, asByteBuffer(bufferedImage));

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        texture = new Texture(fontTextureId, (int) getFontImageWidth(),(int) getFontImageHeight());

        this.glId = fontTextureId;
    }

    public boolean isUnicodeEnable(int unicode){
        return font.canDisplay(unicode);
    }

    public ConstTexture getTexture() {
        return texture;
    }

    @Override
    public void free() {
        texture.free();
    }
}