package fr.delectus98.isticlwjgl.system;

import fr.delectus98.isticlwjgl.graphics.Vector2f;
import fr.delectus98.isticlwjgl.graphics.Vector3f;
import fr.delectus98.isticlwjgl.math.Matrix4f;
import fr.delectus98.isticlwjgl.opengl.GLM;

import static org.lwjgl.opengl.GL11.*;

/**
 * Camera2D is an interface designed to control RenderTarget Views.
 * @see Camera2D
 */
//TODO orthogonal matrices must consider zoom and angle
public class CameraOrtho extends Camera3D {
    private Vector2f screenDimension;
    private float zoom = 1;

    public CameraOrtho(Vector2f dimension, float znear, float zfar){
        screenDimension = dimension.clone();
        this._znear = znear;
        this._zfar = zfar;
        _center = new Vector3f(0, 0, 0);
        _up = new Vector3f(0, 1, 0);
    }

    public CameraOrtho(RenderTarget target, float znear, float zfar){
        this(new Vector2f(target.getDimension()), znear, zfar);
    }

    public void setDimension(Vector2f dimension) {
        this.screenDimension.x = dimension.x;
        this.screenDimension.y = dimension.y;
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

    public Vector2f getDimension() {
        return screenDimension.clone();
    }

    @Override
    public Matrix4f getViewMatrix() {
        return GLM.lookAt(_center, _eye.sum(_center),  _up)
                .scale(invertAxis.x * zoom, invertAxis.y * zoom,zoom);
    }
    @Override
    public Matrix4f getProjectionMatrix() {
        return GLM.ortho(-screenDimension.x / 2.f, screenDimension.x / 2.f, screenDimension.y / 2.f, - screenDimension.y / 2.f, _znear, _zfar);
    }

    @Override
    public void apply() {
        updatable = false;
        glEnable(GL_DEPTH_TEST);

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
