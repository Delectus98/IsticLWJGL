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

    /**
     * Generating OpenGL Color
     * @param r Red value [must be between 0 and 255]
     * @param g Green value [must be between 0 and 255]
     * @param b Blue value [must be between 0 and 255]
     */
    public Color(int r, int g, int b){
        this.r = r / 255.f;
        this.g = g / 255.f;
        this.b = b / 255.f;
        this.a = 1.f;
    }

    /**
     * Generating OpenGL Color
     * @param r Red value [must be between 0 and 255]
     * @param g Green value [must be between 0 and 255]
     * @param b Blue value [must be between 0 and 255]
     * @param a Opacity value [must be between 0 and 255]
     */
    public Color(int r, int g, int b, int a){
        this.r = r / 255.f;
        this.g = g / 255.f;
        this.b = b / 255.f;
        this.a = a / 255.f;
    }

    // Default colors
    public static final ConstColor Blue = new Color(0,0,255,255);
    public static final ConstColor Green = new Color(0,255,0,255);
    public static final ConstColor Red = new Color(255,0,0,255);
    public static final ConstColor Black = new Color(0,0,0,255);
    public static final ConstColor White = new Color(255,255,255,1);
    public static final ConstColor Yellow = new Color(255,255,0,255);
    public static final ConstColor Magenta = new Color(255,0,255,255);
    public static final ConstColor Cyan = new Color(0,255,255,255);
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
