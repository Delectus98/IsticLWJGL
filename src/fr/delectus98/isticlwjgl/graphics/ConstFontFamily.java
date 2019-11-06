package fr.delectus98.isticlwjgl.graphics;


import java.util.Set;

public interface ConstFontFamily {
    Set<Integer> getCharset();
    float getFontImageWidth();
    float getFontImageHeight();
    float getCharX(char c);
    float getCharY(char c);
    float getCharWidth(int c);
    float getCharHeight();
    boolean isUnicodeEnable(int unicode);
    ConstTexture getTexture();
}
