package fr.delectus98.isticlwjgl.system;

import fr.delectus98.isticlwjgl.graphics.FloatRect;
import fr.delectus98.isticlwjgl.graphics.Vector2f;

import static org.lwjgl.opengl.GL11.glViewport;

/**
 * A viewport defines an area on a RenderTarget where the current display will be drawn, contrary to a Camera that define how to draw fr.delectus98.isticlwjgl.graphics on a current Viewport.
 * A camera needs a viewport and a viewport needs a RenderTarget.
 * @see Camera
 */
public final class Viewport {
    private FloatRect rect;
    private boolean updatable = true;

    ////EXPERIMENTAL (permet a un RenderTarget de savoir si son viewport a changé d'état)
    //TODO Renommer isUpdatable pour mieux convenir au sens particulier de a été mis a jour par un RenderTarget
    public final boolean hasSinceBeenUpdated() {
        /*boolean tmp = updatable;
        updatable = false;
        return tmp;*/
        return updatable;
    }

    /**
     * Create a viewport.
     * @param rect the rectangle where fr.delectus98.isticlwjgl.graphics will be drawn.
     */
    public Viewport(FloatRect rect){
        this.rect = rect;
    }

    /**
     * Top-left corner of the viewport
     * @return the top-left corner of the viewport.
     */
    public final Vector2f getTopLeftCorner() {
        return new Vector2f(rect.l, rect.t);
    }

    /**
     *
     * @return
     */
    public final Vector2f getDimension() {
        return new Vector2f(rect.w, rect.h);
    }

    public final void setDimension(Vector2f dimension) {
        rect.w = dimension.x;
        rect.h = dimension.y;
        updatable = true;
    }

    public final void setTopLeftCorner(Vector2f position) {
        rect.l = position.x;
        rect.t = position.y;
        updatable = true;
    }

    /**
     * Apply a viewport linked to RenderTarget dimension
     * @param target
     */
    public final void apply(RenderTarget target){
        updatable = false;
        glViewport((int)(rect.l), (int)(-rect.t + target.getDimension().y - rect.h), (int)(rect.w), (int)(rect.h));
    }

}
