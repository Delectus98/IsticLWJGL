package fr.delectus98.isticlwjgl.graphics;


public class Vector2i implements Comparable<Vector2i> {
    public int x, y;

    public Vector2i(){
        x = y = 0;
    }

    public Vector2i(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vector2i(Vector2i v){
        x = v.x;
        y = v.y;
    }

    public Vector2i(Vector2f v) {
        x = (int)v.x;
        y = (int)v.y;
    }

    // self-operation
    public Vector2i add(fr.delectus98.isticlwjgl.math.Vector2f vec2) {
        this.x += vec2.x;
        this.y += vec2.y;
        return this;
    }

    public Vector2i fact(float f) {
        this.x *= f;
        this.y *= f;
        return this;
    }

    public Vector2i normalize() {
        double l = this.length();
        this.x /= l;
        this.y /= l;
        return this;
    }

    public Vector2i negate() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    //
    public Vector2i neg(){
        return new Vector2i(-this.x, -this.y);
    }

    public Vector2i mul(int f) {
        return new Vector2i(this.x * f, this.y * f);
    }

    public Vector2i sum(Vector2i v2){
        Vector2i vt = new Vector2i(this.x, this.y);
        vt.x += v2.x;
        vt.y += v2.y;
        return vt;
    }

    public Vector2f unit() {
        double l = this.length();
        return new Vector2f((float)(this.x / l), (float)(this.y / l));
    }

    public int slength() {
        return x*x+y*y;
    }

    public float length() {
        return (float)Math.sqrt(x*x + y*y);
    }

    @Override
    public Vector2i clone() {
        return new Vector2i(x,y);
    }

    @Override
    public boolean equals(Object v) {
        if (v == this) return true;
        if (v instanceof Vector2i) {
            Vector2i v2 = (Vector2i)v;
            return v2.x == x && v2.y == y;
        } else return false;
    }

    @Override
    public int compareTo(Vector2i o) {
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
