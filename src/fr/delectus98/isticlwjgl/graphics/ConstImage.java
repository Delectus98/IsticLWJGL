package fr.delectus98.isticlwjgl.graphics;

import java.io.IOException;

public interface ConstImage {
    int getWidth();

    int getHeight();

    /**
     * Gives the color of a given pixel at coordinates [x,y].
     * @param x x-coordinates.
     * @param y y-coordinates.
     * @return converted pixel in [0-255] range to [0-1]
     */
    Color getPixel(int x, int y);


    /**
     * Creates an PNG image file using a specific name and specific extension.
     * @param filename file name with extension. (PNG)
     * @throws IOException thrown when image save did not correctly end up
     */
    void saveAs(String filename) throws IOException ;

}
