package fr.delectus98.isticlwjgl.system;

import fr.delectus98.isticlwjgl.graphics.Vector2f;
import fr.delectus98.isticlwjgl.graphics.Vector2i;
import fr.delectus98.isticlwjgl.math.Matrix4f;
import fr.delectus98.isticlwjgl.opengl.GLM;

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
    private Vector2i invertAxis = new Vector2i(1,1);

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

    /**
     * Invert camera axis view
     * @param x_axis enable x inversion
     * @param y_axis enable y inversion
     */
    public void invert(boolean x_axis, boolean y_axis) {
        invertAxis.x = x_axis ? -1 : 1;
        invertAxis.y = y_axis ? -1 : 1;
        updatable = true;
    }

    @Override
    public Matrix4f getViewMatrix() {
        return Matrix4f.translate(center.x, center.y, 0)
                .scale2(invertAxis.x * zoom, invertAxis.y * zoom,1)
                .rotate2(angle, 0,0,1)
                .translate2(-center.x, -center.y, 0);
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
