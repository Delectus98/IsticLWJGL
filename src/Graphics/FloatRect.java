package Graphics;

/**
 * FloatRect represents coordinates and dimension of a rectangle
 */
public class FloatRect {
    public float l, t, w, h;

    /**
     * Generates a rectangle with all value set default to 0
     */
    public FloatRect() {
        this.l = 0;
        this.t = 0;
        this.w = 0;
        this.h = 0;
    }

    /**
     * Generates a rectangle
     * @param l left coordinate
     * @param t top coordinate
     * @param w width
     * @param h height
     */
    public FloatRect(float l, float t, float w, float h) {
        this.l = l;
        this.t = t;
        this.w = w;
        this.h = h;
    }

    /**
     * Checks if point if inside rectangle
     * @param x x coordinates
     * @param y y coordinates
     * @return true if point if inside rectangle else false
     */
    public boolean contains(float x, float y) {
        return x >= l && x <= l + w && y >= t && y <= t + h;
    }

}
