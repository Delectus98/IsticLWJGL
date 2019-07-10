package fr.delectus98.isticlwjgl.graphics;

/**
 * Drawable is a graphic component that can be drawn by a Renderer.
 *
 */
public interface Drawable {
    /**
     * Draws graphic component within a previously designated Frame Buffer.
     * An OpenGL context must be initialized before the call.
     * A RenderTarget must be activated before calling 'draw'.
     */
    void draw();
}
