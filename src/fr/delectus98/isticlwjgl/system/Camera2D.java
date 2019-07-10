package fr.delectus98.isticlwjgl.system;

import fr.delectus98.isticlwjgl.graphics.Vector2f;
import fr.delectus98.isticlwjgl.opengl.GLM;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Camera2D is an interface designed to control RenderTarget Views.
 * @see Camera2D
 */
//TODO orthogonal matrices must consider zoom and angle
public class Camera2D extends Camera {
    private Vector2f screenDimension;
    private Vector2f center;
    private float zoom = 1;
    private float angle = 0;
    private float znear = -1.f;
    private float zfar = 1.f;

    public Camera2D(Vector2f dimension){
        screenDimension = dimension;
        center = new Vector2f(dimension.x / 2.f,dimension.y / 2.f);
    }

    public Camera2D(RenderTarget target){
        this(new Vector2f(target.getDimension()));
    }

    public void setDimension(Vector2f dimension) {
        this.screenDimension.x = dimension.x;
        this.screenDimension.y = dimension.y;
        updatable = true;
    }

    public Vector2f getDimension() {
        return screenDimension.clone();
    }

    public Vector2f getCenter() {
        return center.clone();
    }

    public float getZoom() {
        return zoom;
    }

    public float getZnear() {
        return znear;
    }

    public float getZfar() {
        return zfar;
    }

    public float getRotation() {
        return angle;
    }

    @Override
    public Matrix4f getViewMatrix() {
        return new Matrix4f()
                .translate(new org.lwjgl.util.vector.Vector2f(center.x, center.y))
                .scale(new Vector3f(zoom,zoom,1))
                .rotate(angle, new Vector3f(0,0,1))
                .translate(new org.lwjgl.util.vector.Vector2f(-center.x, -center.y));
    }
    @Override
    public Matrix4f getProjectionMatrix() {
        return GLM.ortho(center.x - screenDimension.x/2.f, center.x + screenDimension.x/2.f, center.y + screenDimension.y/2.f, center.y - screenDimension.y/2.f, znear, zfar);
    }

    public void move(Vector2f motion) {
        center.x += motion.x;
        center.y += motion.y;
        updatable = true;
    }

    public void setCenter(Vector2f center) {
        this.center = center;
        updatable = true;
    }

    public void zoom(float zoom) {
        this.zoom += zoom;
        updatable = true;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
        updatable = true;
    }

    /**
     *
     * @param angle radian
     */
    public void setRotation(float angle) {
        this.angle = angle;
        updatable = true;
    }

    /**
     *
     * @param angle radian
     */
    public void rotate(float angle) {
        this.angle += angle;
        updatable = true;
    }

    public void setZnear(float znear) {
        this.znear = znear;
        updatable = true;
    }

    public void setZfar(float zfar) {
        this.zfar = zfar;
        updatable = true;
    }


    @Override
    public void apply() {
        updatable = false;
        glDisable(GL_DEPTH_TEST);

        /// change fov
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glLoadMatrixf(getProjectionBuffer());

        /// change position
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glLoadMatrixf(getViewBuffer());

    }
}
