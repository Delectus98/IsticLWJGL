package fr.delectus98.isticlwjgl.graphics;

public interface ConstTexture {
    /**
     * Sets this (glId) as the current Texture
     */
    void bind();

    /**
     * Sets this (glId) as the current Texture (Gl_TEXTURE0 + pos)
     * @param pos uniform texture position inside shader
     */
    void bind(int pos);

    /**
     * Current texture's width in pixels
     * @return image pixel width
     */
    int getWidth();

    /**
     * Current texture's height in pixels
     * @return image pixel height
     */
    int getHeight();


    /**
     * Tests if two textures are same by comparing their 'glId'. Both texture must be into DRAM (it means that their 'glId' must be different to 0).
     * @param o comparison object
     * @return true if both texture are into DRAM and have same 'glId'
     */
    @Override
    boolean equals(Object o);

    Image toImage();
}
