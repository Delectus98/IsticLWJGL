package fr.delectus98.isticlwjgl.graphics;


public final class Color extends ConstColor {
    public float r,g,b,a;

    /**
     * Generating OpenGL Color
     * @param r Red value [must be between 0 and 1]
     * @param g Green value [must be between 0 and 1]
     * @param b Blue value [must be between 0 and 1]
     *          Alpha default value is 1
     */
    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1;
    }

    /**
     * Generating OpenGL Color
     * @param r Red value [must be between 0 and 1]
     * @param g Green value [must be between 0 and 1]
     * @param b Blue value [must be between 0 and 1]
     * @param a Opacity value [must be between 0 and 1]
     */
    public Color(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    // Default colors
    public static final ConstColor Blue = new Color(0,0,1,1);
    public static final ConstColor Green = new Color(0,1,0,1);
    public static final ConstColor Red = new Color(1,0,0,1);
    public static final ConstColor Black = new Color(0,0,0,1);
    public static final ConstColor White = new Color(1,1,1,1);
    public static final ConstColor Yellow = new Color(1,1,0,1);
    public static final ConstColor Magenta = new Color(1,0,1,1);
    public static final ConstColor Cyan = new Color(0,1,1,1);
    public static final ConstColor Transparent = new Color(0,0,0,0);


    @Override
    public float getR() {
        return r;
    }

    @Override
    public float getG() {
        return g;
    }

    @Override
    public float getB() {
        return b;
    }

    @Override
    public float getA() {
        return a;
    }
}
