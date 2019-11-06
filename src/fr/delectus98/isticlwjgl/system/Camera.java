package fr.delectus98.isticlwjgl.system;


import fr.delectus98.isticlwjgl.graphics.ConstShader;
import fr.delectus98.isticlwjgl.opengl.GLM;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

/**
 * A camera decides how fr.delectus98.isticlwjgl.graphics will be draw by transforming their coordinates according to camera settings/properties.
 * Camera is an interface designed to control RenderTarget Views.
 * @see Viewport
 */
public abstract class Camera {
    ////EXPERIMENTAL (permet a un RenderTarget de savoir si sa camera a changé d'état)
    protected boolean updatable = true; // updatable -> checked? Display List
    //protected boolean uniformBlockUpdatable = true; // updatable -> checked?

    public final boolean hasSinceBeenUpdated() {
        /*boolean tmp = updatable;
        updatable = false;
        return tmp;*/
        return updatable;
    }

    //public abstract Matrix4f getModelMatrix() ;

    public abstract Matrix4f getViewMatrix() ;

    public abstract Matrix4f getProjectionMatrix() ;

    public final float[] getViewBuffer() {
        return GLM.toFloatArray(getViewMatrix());
    }

    public final float[] getProjectionBuffer() {
        return GLM.toFloatArray(getProjectionMatrix());
    }

    /**
     * Sets up MVP matrices to a RenderTarget for Display list mode.
     */
    public abstract void apply() ;

    /**
     * Sets up MVP matrix using GLSL matrix uniform location
     * @param mvpUniform uniform MVP matrix location
     */
    public final void setUniformMVP(int mvpUniform) {
        if (mvpUniform < 0) {
            System.out.println("MVP Uniform matrix do not exist in the shader.");
            return;
        }

        GL20.glUniformMatrix4fv(mvpUniform, false, GLM.toFloatArray((Matrix4f.mul(getProjectionMatrix(), getViewMatrix(), null))));
    }

    /**
     * Sets up MVP matrix using GLSL matrix uniform location
     * @param mvpUniform uniform MVP matrix location
     * @param model model matrix to build MVP matrix
     */
    public final void setUniformMVP(int mvpUniform, Matrix4f model) {
        if (mvpUniform < 0) {
            System.out.println("MVP Uniform matrix do not exist in the shader.");
            return;
        }

        GL20.glUniformMatrix4fv(mvpUniform, false, GLM.toFloatArray((Matrix4f.mul(getProjectionMatrix(), Matrix4f.mul(getViewMatrix(), model, null), null))));
    }

    /**
     * Sets up MVP matrix to shader using GLSL matrix name
     * @param shader target
     * @param mvp MVP matrix name
     */
    @Deprecated
    public final void apply(ConstShader shader, String mvp) {
        final int MVP = shader.getUniformLocation(mvp);
        this.setUniformMVP(MVP);
    }


    /**
     * Applies M/V/P Matrices to shader using M/V/P matrices names
     * @param shader target
     * @param model model matrix
     * @param view view matrix
     * @param projection projection matrix
     */
    @Deprecated
    public final void apply(ConstShader shader, String model, String view, String projection) {
        this.setUniformMVP(shader, model, view, projection);
    }

    /**
     * Sets up MVP matrices to shader using GLSL matrices names
     * @param shader target
     * @param model uniform Model matrix name
     * @param view uniform View matrix name
     * @param projection uniform Projection matrix name
     */
    @Deprecated
    public final void setUniformMVP(ConstShader shader, String model, String view, String projection) {
        final int M = shader.getUniformLocation(model);
        final int V = shader.getUniformLocation(view);
        final int P = shader.getUniformLocation(projection);
        this.setUniformMVP(M,V,P);
    }

    /**
     * Sets MVP matrices using GLSL matrices uniform locations
     * @param modelUniform uniform Model matrix location
     * @param viewUniform uniform View matrix location
     * @param projectionLayout uniform Projection matrix location
     */
    @Deprecated
    public final void setUniformMVP(int modelUniform, int viewUniform, int projectionLayout){
        if (modelUniform < 0 || viewUniform < 0 || projectionLayout < 0) {
            System.out.println("MVP Uniform matrix do not exist in the shader.");
            return;
        }

        // send MVP Data
        //GL20.glUniformMatrix4fv(modelUniform, false, getModelBuffer());
        GL20.glUniformMatrix4fv(modelUniform, false, GLM.toFloatArray(new Matrix4f()));
        GL20.glUniformMatrix4fv(viewUniform, false, getViewBuffer());
        GL20.glUniformMatrix4fv(projectionLayout, false, getProjectionBuffer());
    }

    @Deprecated
    public static void setUniformMVP(int uniformModel, float[] model, int uniformView, float[] view, int uniformProjection, float[] projection) {
        if (uniformModel < 0 || uniformView < 0 || uniformProjection < 0) {
            System.out.println("MVP Uniform matrix do not exist in the shader.");
            return;
        }

        // send MVP Data
        GL20.glUniformMatrix4fv(uniformModel, false, model);
        GL20.glUniformMatrix4fv(uniformView, false, view);
        GL20.glUniformMatrix4fv(uniformProjection, false, projection);
    }

}
