package fr.delectus98.isticlwjgl.graphics;

/**
 * Represents a 3D point/vector/dimension.
 */
public class Vector3f extends org.lwjgl.util.vector.Vector3f implements Comparable<Vector3f> {
    /*public float x;
    public float y;
    public float z;*/

    /**
     * Generates a vector placed at (x,y,z).
     * @param x x coordinates
     * @param y y coordinates
     * @param z z coordinates
     */
    public Vector3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Generates a vector placed at (v.x,v.y,v.z).
     * @param v copied vector
     */
    public Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Default constructor. Generate a vector placed at (0,0,0).
     */
    public Vector3f(){
        this.x = this.y = this.z = 0;
    }

    /**
     * Translates the coordinates.
     * @param vec3 added coordinates
     * @return this
     */
    public Vector3f add(Vector3f vec3) {
        this.x += vec3.x;
        this.y += vec3.y;
        this.z += vec3.z;
        return this;
    }

    /**
     * Multiplies the coordinates by a factor.
     * @param f factor
     * @return this
     */
    public Vector3f fact(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
        return this;
    }

    /**
     * The vector will be normalized.
     * Means that the length will be equal to 1.
     * Coordinates must be different to (0,0,0).
     * @return this
     */
    public Vector3f normalize() {
        double l = this.length();
        this.x /= l;
        this.y /= l;
        this.z /= l;
        return this;
    }

    /**
     * The vector will be inverted.
     * @return this
     */
    public Vector3f negate(){
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    /**
     * Builds an inverted vector using negative 'this' coordinates.
     * "this" remains unchanged.
     * @return inverted vector
     */
    public Vector3f neg(){
        return new Vector3f(-this.x, -this.y, -this.z);
    }

    /**
     * Builds a vector with a norm 'f' times longer.
     * "this" remains unchanged.
     * @param f specified factor
     * @return a vector with a norm 'f' times longer than 'this'
     */
    public Vector3f mul(float f) {
        return new Vector3f(this.x * f, this.y * f, this.z * f);
    }

    /**
     * Builds a new translated vector.
     * "this" remains unchanged.
     * @param v3 added coordinates
     * @return sum of two vectors
     */
    public Vector3f sum(Vector3f v3){
        Vector3f vt = new Vector3f(this.x, this.y, this.z);
        vt.x += v3.x;
        vt.y += v3.y;
        vt.z += v3.z;
        return vt;
    }

    /**
     * Builds a normalized vector using 'this' coordinates. Means that the length of the returned vector will be equals to 1. Coordinates must be different to (0,0,0).
     * "this" remains unchanged.
     * @return normalized vector
     */
    public Vector3f unit() {
        double l = this.length();
        return new Vector3f((float)(this.x / l), (float)(this.y / l), (float)(this.z / l));
    }

    /**
     * Computes the squared length of 'this'.
     * @return squared length of 'this'
     */
    public double slength() {
        return x*x+y*y+z*z;
    }

    /**
     * Computes the length of 'this'.
     * @return length of the vector
     */
    /*public double length(){
        return Math.sqrt(x*x+y*y+z*z);
    }*/

    /**
     * Computes the scalar of two vectors.
     * @param v1 first vector
     * @param v2 second vector
     * @return scalar of v1 and v2 (v1 * v2)
     */
    public static float scalar(Vector3f v1, Vector3f v2){
        return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
    }

    /**
     * Computes the normal of two distinct vectors.
     * @param v1 first vector
     * @param v2 second vector
     * @return product of v1 and v2 (v1 ^ v2)
     */
    public static Vector3f product(Vector3f v1, Vector3f v2) {
        Vector3f vt = new Vector3f((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
        vt.negate();
        return vt;
    }

    /**
     * Computes radian between two vectors
     * @param a first specified vector
     * @param b first specified vector
     * @return radian angle of two vectors
     */
    public static double radianBetween(Vector3f a, Vector3f b)
    {
        return Math.acos(scalar(a, b) / (a.length() * b.length()));
    }

    /**
     * Copy vector coordinates
     * @return vector copy
     */
    @Override
    public Vector3f clone() {
        return new Vector3f(x,y,z);
    }

    /**
     * Checks if two vectors are equal
     * @param o compared object
     * @return if both vectors are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Vector3f) {
            Vector3f v = (Vector3f)o;
            return x == v.x && y == v.y && z == v.z;
        } else {
            return false;
        }
    }

    /**
     * Compare two 3D vectors
     * @param o comparable
     * @return 0 if both vectors are equal or -1 if 'this' is lower or 1 if 'this' is greater
     */
    @Override
    public int compareTo(Vector3f o) {
        if (o.x < this.x) return 1;
        else if (o.x > this.x) return -1;
        else {
            if (o.y < this.y) return 1;
            else if (o.y > this.y) return -1;
            else {
                if (o.z < this.z) return 1;
                else if (o.z > this.z) return -1;
                else {
                    return 0;
                }
            }
        }
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
