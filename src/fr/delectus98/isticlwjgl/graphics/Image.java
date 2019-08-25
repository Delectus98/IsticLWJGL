package fr.delectus98.isticlwjgl.graphics;


import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Image represents a 2D graphic RGBA8 image that can be edit by the CPU.
 * Contrary to Texture it can not be sent to the GPU to be drawn.
 * Contrary to Texture, data are saved into the RAM.
 * @see Texture Texture (DRAM only)
 */
public class Image implements ConstImage {
    private static final int bpp = 4;

    private int width;
    private int height;
    private byte[] buffer;

    /**
     * Create a default image with no pixels that needs to be initialized after.
     */
    public Image(){
        width = 0;
        height = 0;
        buffer = null;
    }

    /**
     * Create a default image with default dimension w*h with white background color.
     * @param w image width
     * @param h image height
     */
    public Image(int w, int h) {
        this.create(w,h);
    }

    /**
     * Change current image's dimension with dimension w*h with white background color.
     * @param w image width.
     * @param h image height.
     */
    public void create(int w, int h) {
        width = w;
        height = h;

        buffer = new byte[w*h*bpp];
        for (int i=0 ; i < buffer.length ; ++i) {
            buffer[i] = (byte)255;
        }
    }

    /**
     * Creates an image from a byte buffer.
     * @param buffer byte buffer with 4 component RGBA for each pixel and with [0 to 255] range value for each components. The byte buffer must contains w * h * 4 bytes.
     * @param w image width.
     * @param h image height.
     */
    public Image(ByteBuffer buffer, int w, int h) {
        width = w;
        height = h;

        this.buffer = new byte[w*h*bpp];
        buffer.get(this.buffer);
    }

    /**
     * Creates an image from a byte array.
     * @param buffer byte array with 4 component RGBA for each pixel and with [0 to 255] range value for each components. The byte buffer must contains w * h * 4 bytes.
     * @param w image width.
     * @param h image height.
     */
    public Image(byte[] buffer, int w, int h) {
        width = w;
        height = h;

        this.buffer = buffer;
    }

    /**
     * Create an image loaded from a PNG file.
     * @param file specified file
     * @throws IOException thrown when image can't be load from file
     */
    public Image(String file) throws IOException {
        this.loadFromFile(file);
    }


    /**
     * Loads current image from a PNG/JPG/BMP file.
     * @param file specified file
     * @throws IOException thrown when image can't be load from file
     */
    public void loadFromFile(String file) throws IOException {
        InputStream input = null;
        try {
            //get an InputStream from our URL
            input = new FileInputStream(file);

            BufferedImage i = ImageIO.read(input);

            width = i.getWidth();
            height = i.getHeight();

            ByteBuffer buf = convertImage(i);

            buffer = new byte[buf.capacity()];

            buf.get(buffer);
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * Converts BufferedImage to ByteBuffer
     * @param image specified BufferedImage
     * @return conversion to ByteBuffer
     */
    public static ByteBuffer convertImage(BufferedImage image) {
        final int BYTES_PER_PIXEL = 4;
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL);

        for(int y = 0; y < image.getHeight(); y++)
        {
            for(int x = 0; x < image.getWidth(); x++)
            {
                int pixel = pixels[y * image.getWidth() + x];

                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip();

        return buffer;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    /**
     * Gives the color of a given pixel at coordinates [x,y].
     * @param x x-coordinates.
     * @param y y-coordinates.
     * @return converted pixel in [0-255] range to [0-1]
     */
    public Color getPixel(int x, int y) {
        int offset = x * bpp + y * width * bpp;

        return new Color(((buffer[offset]) & 0xFF) / 255.f, ((buffer[offset+1]) & 0xFF) / 255.f, ((buffer[offset+2]) & 0xFF) / 255.f, ((buffer[offset+3]) & 0xFF) / 255.f);
    }

    /**
     * Sets the color of a given pixel at coordinates [x,y]
     * @param x x-coordinates.
     * @param y y-coordinates.
     * @param c openGL color
     */
    public void setPixel(int x, int y, ConstColor c) {
        int offset = x * bpp + y * width * bpp;

        // openGL color colors are in range [0-1] and must be converted to [0-255]
        buffer[offset  ] = (byte)(c.getR()*255);
        buffer[offset+1] = (byte)(c.getG()*255);
        buffer[offset+2] = (byte)(c.getB()*255);
        buffer[offset+3] = (byte)(c.getA()*255);
    }

    /**
     * Creates an PNG image file using a specific name and specific extension.
     * @param filename file name with extension. (PNG)
     * @throws IOException thrown when image save did not correctly end up
     */
    public void saveAs(String filename) throws IOException {
        DataBufferByte dbb = new DataBufferByte(buffer, buffer.length);
        WritableRaster raster = Raster.createInterleavedRaster(
                dbb,
                width, height,
                width * bpp,
                bpp,
                new int []{ 0, 1, 2, 3},
                null);

        ColorModel colorModel =  new ComponentColorModel(
                ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[] { 8, 8, 8, 8},
                true,
                false,
                Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE);

        BufferedImage bfImage = new BufferedImage(colorModel, raster, false, null);

        String format = null;
        int index = filename.lastIndexOf('.');
        try {
            format = filename.substring(index+1, filename.length()).toLowerCase();
            if (format == null || !format.equals("png")) {
                throw new IOException("Image PNG format required.");
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new IOException("Image format required.");
        }

        ImageIO.write(bfImage, format, new File(filename));
    }

    public static void main(String[] args) throws IOException {
        Image image = new Image();

        image.loadFromFile("character.png");

        /*image.create(50,50);

        for (int i=0 ; i < 50 ; ++i) {
            for (int j=0 ; j < 50 ; j++) {
                image.setPixel(i,j, new Color(0.f, 1.f, 1.f, (i+j)/(100.f)));
            }
        }*/

        try {
            image.saveAs("test_copy.png");
            image.setPixel(0,0, Color.Yellow);
            System.out.println(image.getPixel(0,0).toString());
            System.out.println(image.getPixel(1,0).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
