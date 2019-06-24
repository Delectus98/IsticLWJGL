package Graphics;


/**
 * Represents a 2D point/vector/dimension.
 */
public class Vector2f extends org.lwjgl.util.vector.Vector2f implements Comparable<Vector2f> {
    /*public float x;
    public float y;*/

    public Vector2f(){
        this.x = this.y = 0;
    }

    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f v) {
        x = v.x;
        y = v.y;
    }

    public Vector2f(Vector2i v) {
        x = v.x;
        y = v.y;
    }

    // self-operation
    public Vector2f add(Vector2f vec2) {
        this.x += vec2.x;
        this.y += vec2.y;
        return this;
    }

    public Vector2f fact(float f) {
        this.x *= f;
        this.y *= f;
        return this;
    }

    public Vector2f normalize() {
        double l = this.length();
        this.x /= l;
        this.y /= l;
        return this;
    }

    public Vector2f negate() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    //
    public Vector2f neg(){
        return new Vector2f(-this.x, -this.y);
    }

    public Vector2f mul(float f) {
        return new Vector2f(this.x * f, this.y * f);
    }

    public Vector2f sum(Vector2f v2){
        Vector2f vt = new Vector2f(this.x, this.y);
        vt.x += v2.x;
        vt.y += v2.y;
        return vt;
    }

    public Vector2f unit() {
        double l = this.length();
        return new Vector2f((float)(this.x / l), (float)(this.y / l));
    }

    public double slength() {
        return x*x+y*y;
    }

    @Override
    public Vector2f clone() {
        return new Vector2f(x,y);
    }

    @Override
    public boolean equals(Object v) {
        if (v == this) return true;
        if (v instanceof Vector2f) {
            Vector2f v2 = (Vector2f)v;
            return v2.x == x && v2.y == y;
        } else return false;
    }

    @Override
    public int compareTo(Vector2f o) {
        if (o.x < this.x) return 1;
        else if (o.x > this.x) return -1;
        else {
            if (o.y < this.y) return 1;
            else if (o.y > this.y) return -1;
            else {
                return 0;
            }
        }
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
