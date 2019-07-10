package fr.delectus98.isticlwjgl.graphics;


import static org.lwjgl.opengl.GL11.*;

/**
 * "Display list" Text
 */
public class Text extends Shape {
    public static final int REGULAR = 0;
    public static final int BOLD = 1;
    public static final int ITALIC = 2;

    private float px;
    private int style;
    private String string = "";
    private FontFamily font = null;

    public Text(FontFamily font, String str) {
        this(font, str, REGULAR);
    }

    public Text(FontFamily font, String str, int styles) {
        this.px = font.getFontImageHeight();

        this.font = font;
        this.string = str;
        this.style = styles;

        //buffer = new VertexBuffer(str.length() * 3 * 2, 3 , new int[]{3,4,2}, VertexBuffer.Mode.TRIANGLES, VertexBuffer.Usage.STREAM );

        update();
    }

    public void setString(String content) {
        this.string = content;

        //buffer.create(content.length() * 3 * 2, 3, new int[]{3, 4, 2}, VertexBuffer.Mode.TRIANGLES);

        update();
    }

    @Override
    protected void updateColor(){
        /*float[] c = new float[buffer.getVertices() * 3 * 2 * 4];
        for (int i = 0 ; i < buffer.getVertices() * 3 * 2 ; ++i) {
            c[i*4 + 0] = color.r;
            c[i*4 + 1] = color.g;
            c[i*4 + 2] = color.b;
            c[i*4 + 3] = color.a;
        }

        buffer.update(1, c);*/
    }

    @Override
    protected void update() {
        this.resize();
    }

    private void resize() {
        float xTmp = x;
        float charHeight = font.getCharHeight();

        float italicOffset = ((style & ITALIC) == ITALIC) ? (this.px / 10.f) : 0.f;
        float boldOffsetX = ((style & BOLD) == BOLD) ? (charHeight / 3.f) : 0.f;
        float boldOffsetY = ((style & BOLD) == BOLD) ? (charHeight / 100.f) : 0.f;



        for (char c : string.toCharArray()) {
            float width = font.getCharWidth(c);

            xTmp += width * super.sx + boldOffsetX;
        }

        width = xTmp - x + italicOffset;
        height = charHeight * super.sy + boldOffsetY;
    }

    public String getString() {
        return string;
    }

    @Override
    public void draw() {
        //Shader.unbind();

        font.getTexture().bind();
        glEnable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);

        float xTmp = x;
        final float height = font.getCharHeight();

        final float italicOffset = ((style & ITALIC) == ITALIC) ? (this.px / 10.f) : 0.f;
        final float boldOffsetX = ((style & BOLD) == BOLD) ? (height / 3.f) : 0.f;
        final float boldOffsetY = ((style & BOLD) == BOLD) ? (height / 100.f) : 0.f;

        glColor4f(color.r, color.g, color.b, color.a);

        for (char c : string.toCharArray()) {
            float width = font.getCharWidth(c);

            float cw = 1f / font.getFontImageWidth() * width;
            float ch = 1f / font.getFontImageHeight() * height;
            float cx = 1f / font.getFontImageWidth() * font.getCharX(c);
            float cy = 1f / font.getFontImageHeight() * font.getCharY(c);


            glTexCoord2f(cx, cy);
            glVertex3f(xTmp + ox + italicOffset, y + oy, 0);

            glTexCoord2f(cx + cw, cy);
            glVertex3f(xTmp + ox + width * super.sx  + italicOffset + boldOffsetX, y + oy, 0);

            glTexCoord2f(cx + cw, cy + ch);
            glVertex3f(xTmp + ox + width * super.sx  + boldOffsetX, y + oy + height * super.sy + boldOffsetY, 0);

            glTexCoord2f(cx, cy + ch);
            glVertex3f(xTmp + ox , y + oy + height * super.sy  + boldOffsetY, 0);

            xTmp += width * super.sx + boldOffsetX;
        }
        glEnd();

    }

    public int getStyle(){
        return style;
    }

    public FontFamily getFont(){
        return font;
    }

    @Override
    public FloatRect getBounds() {
        return new FloatRect(x - ox, y - oy, width, height);
    }

}
