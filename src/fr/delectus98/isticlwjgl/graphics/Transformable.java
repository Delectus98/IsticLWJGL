package fr.delectus98.isticlwjgl.graphics;

/**
 * Transformable is a 2D component that can be transformed using translation, scaling and rotating
 */
public abstract class Transformable {
    protected float x=0, y=0; // position coordinates
    protected float ox=0, oy=0; // origin coordinates
    protected float sx=1,sy=1; // scale factors
    protected float radian = 0; // rotation angle
    protected float cos = 1; // precomputed cosines
    protected float sin = 0; // precomputed sinus


    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        update();
    }
    public void move(float x, float y) {
        this.x += x;
        this.y += y;
        update();
    }
    public void setOrigin(float x, float y){
        this.ox = x;
        this.oy = y;
        update();
    }
    public void setScale(float x, float y){
        this.sx = x;
        this.sy = y;
        update();
    }
    public void setRotation(float radian) {
        this.radian = radian;
        cos = (float)Math.cos(this.radian);
        sin = (float)Math.sin(this.radian);
        update();
    }
    public void rotate(float radian) {
        this.radian += radian;
        cos = (float)Math.cos(this.radian);
        sin = (float)Math.sin(this.radian);
        update();
    }

    public float getX() { return x;}
    public float getY() { return y;}
    public float getOriginX() { return ox;}
    public float getOriginY() { return oy;}
    public float getScaleX() { return sx;}
    public float getScaleY() { return sy;}
    public float getRotation() { return radian;}
    public Vector2f getPosition(){
        return new Vector2f(x, y);
    }
    public Vector2f getOrigin(){
        return new Vector2f(ox, oy);
    }
    public Vector2f getScale(){
        return new Vector2f(sx, sy);
    }

    /**
     * Updates VBO/VAO vertices using scale, origin, rotation and position.
     */
    protected abstract void update();
}
