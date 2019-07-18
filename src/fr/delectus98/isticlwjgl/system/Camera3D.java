package fr.delectus98.isticlwjgl.system;


import fr.delectus98.isticlwjgl.graphics.Vector2i;
import fr.delectus98.isticlwjgl.graphics.Vector3f;
import fr.delectus98.isticlwjgl.opengl.GLM;
import org.lwjgl.util.vector.Matrix4f;


import static org.lwjgl.opengl.GL11.*;

/**
 * Camera3D is an interface designed to control RenderTarget Views.
 * Camera3D enables GL_DEPTH_TEST.
 * @see Camera2D
 */
public class Camera3D extends Camera {

    protected Vector3f _center = new Vector3f(0,0,0);
    protected Vector3f _up = new Vector3f(0,-1,0);
    protected Vector3f _eye  = new Vector3f(0,0,1);
    protected float _fov = 90.f;
    protected float _znear = 1;
    protected float _zfar = 1000;
    protected float _aspectRatio = 3.f/4.f;
    protected Vector2i invertAxis = new Vector2i(1,1);

    /**
     * Generates Camera with default settings :
     * center = (0,0,0)
     * eye = (0,0,1)
     * up = (0,1,0)
     * fov = (90)
     * znear = 1;
     * zfar = 1000;
     * aspectRatio = 3/4
     */
    public Camera3D(){}

    /**
     * Generates Camera using specific settings
     * @param fov field of view in degree
     * @param znear near depth buffer limit
     * @param zfar far depth buffer limit
     * @param target for view aspect
     */
    public Camera3D(float fov, float znear, float zfar, RenderTarget target) {
        this._fov = fov;
        this._znear = znear;
        this._zfar = zfar;
        this._aspectRatio = (float)target.getDimension().x / (float)target.getDimension().y;
    }

    /**
     * Generates Camera using specific settings
     * @param fov field of view in degree
     * @param znear near depth buffer limit
     * @param zfar far depth buffer limit
     * @param aspect view aspect
     */
    public Camera3D(float fov, float znear, float zfar, float aspect){
        this._fov = fov;
        this._znear = znear;
        this._zfar = zfar;
        this._aspectRatio = aspect;
    }

    public float getFOV() {
        return _fov;
    }

    public float getAspectRatio() {
        return _aspectRatio;
    }

    public float getZNear() {
        return _znear;
    }

    public float getZFar() {
        return _zfar;
    }

    public Vector3f getOrientation() {
        return new Vector3f(_eye);
    }

    public Vector3f getCenter() {
        return new Vector3f(_center);
    }

    public Vector3f getUpVector()  {
        return new Vector3f(_up);
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
        return GLM.lookAt(_center/*.toLwjgl()*/, _eye.sum(_center)/*.toLwjgl()*/,  _up/*.toLwjgl()*/);
    }
    @Override
    public Matrix4f getProjectionMatrix() {
        return GLM.perspective(/*(float)Math.toRadians(_fov)*/invertAxis.x * _fov, invertAxis.y * _aspectRatio, _znear, _zfar);
    }

    public void setFOV(float fov) {
        this._fov = fov;
        updatable = true;
    }

    public void setAspectRatio(float aspect) {
        this._aspectRatio = aspect;
        updatable = true;
    }

    public void setZNear(float znear) {
        _znear = znear;
        updatable = true;
    }

    public void setZFar(float zfar) {
        _zfar = zfar;
        updatable = true;
    }

    public void move(Vector3f motion) {
        _center.add(motion);
        updatable = true;
    }

    public void setPosition(Vector3f position) {
        _center = position.clone();
        updatable = true;
    }

    /**
     * Sets the orientation of the camera where it look to
     * @see Camera3D#lookAt(Vector3f)
     * @param eye
     */
    public void look(Vector3f eye) {
        _eye = eye.unit();
        updatable = true;
    }

    /**
     * Sets the position where the camera look to
     * @see Camera3D#look(Vector3f)
     * @param target
     */
    public void lookAt(Vector3f target) {
        _eye = target.sum(_center.neg()).unit();
        updatable = true;
    }

    /**
     * Sets the direction where the camera's top is
     * @param up
     */
    public void setUpVector(Vector3f up) {
        _up = up.unit();
        updatable = true;
    }

    @Override
    public void apply() {
        updatable = false;
        glEnable(GL_DEPTH_TEST);

        /// change fov
        glMatrixMode(GL_PROJECTION);
        glLoadMatrixf(getProjectionBuffer());

        /// change position
        glMatrixMode(GL_MODELVIEW);
        glLoadMatrixf(getViewBuffer());
    }






}
