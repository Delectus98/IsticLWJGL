package fr.delectus98.isticlwjgl.system;


import fr.delectus98.isticlwjgl.graphics.*;
import com.sun.istack.internal.NotNull;

/**
 * RenderTarget is an abstract class specifying that it drawn OpenGL fr.delectus98.isticlwjgl.graphics.
 */
public abstract class RenderTarget extends GlObject {
    //Multiple RenderTargets
    private static ThreadLocal<RenderTarget> currentTarget = new ThreadLocal<RenderTarget>();

    //load default shader only once
    private static boolean first = true;

    //default view parameters
    protected Camera defaultCamera;
    protected Camera camera;
    protected Viewport defaultViewport;
    protected Viewport viewport;


    protected RenderTarget(){
        super();
    }

    /**
     * Change current RenderTarget to 'this' and change view to 'this' view
     */
    public final void setActive(){
        if (currentTarget.get() != this) {
            currentTarget.set(this);
            this.bind(); // on souhaite modifier ce RenderTarget seulement
        }
    }

    /**
     * Make 'this' as the current RenderTarget.
     */
    protected abstract void bind() ;

    /**
     * Updates current RenderTarget View using Camera and Viewport
     */
    protected final void applyView() {
        camera.apply();
        viewport.apply(this);
    }

    /**
     * Checks if currentTarget is 'this'.
     * @return true i
     */
    public final boolean isActive(){
        return currentTarget.get() == this;
    }

    protected final boolean needViewUpdate() {
        return viewport.hasSinceBeenUpdated() || camera.hasSinceBeenUpdated();
    }

    /**
     * Change last camera to camera.
     * Setting a camera obliges the User to update camera settings when the GLFWWindow is resizing.
     * @param cam specified camera
     */
    public final void setCamera(Camera cam){
        this.camera = cam;
        if (isActive()) {
            // changer de camera pendant un affichage ne va pas remettre a jour la vue du RenderTarget
            // changer les paramètres de la camera utilisée pendant un affichage va remettre a jour la camera du RenderTarget
            this.camera.apply();
        }
    }

    /**
     * Change last viewport to viewport.
     * Setting a viewport obliges the User to update viewport settings when the GLFWWindow is resizing.
     * @param viewport specified viewport
     */
    public final void setViewport(Viewport viewport){
        this.viewport = viewport;
        if (isActive()) {
            // changer de viewport pendant un affichage va remettre a jour la vue du RenderTarget
            // changer les paramètres du viewport utilisée pendant un affichage va remettre a jour le viewport du RenderTarget
            this.viewport.apply(this);
        }
    }

    public Viewport getViewport() {
        return viewport;
    }

    public Camera getCamera() {
        return camera;
    }

    /**
     * Clear the RenderTarget with a fill color.
     * RenderTarget is activated before calling 'clear' method.
     * @param color clear color
     */
    public abstract void clear(ConstColor color);

    /**
     * Clear the RenderTarget with black color.
     * RenderTarget is activated before calling 'clear' method.
     */
    public abstract void clear();

    /**
     * RenderTarget is activated before calling 'draw' method of the drawable.
     * @param d drawable
     */
    public final void draw(Drawable d) {
        draw(d, Shader.getDefaultShader());
    }

    /**
     * RenderTarget is activated before calling 'draw' method of the drawable and use shader program (with IsticLWJGL specificity).
     * @param d drawable
     * @param shader shader program
     */
    public abstract void draw(Drawable d, ConstShader shader);

    /**
     * Gives the pixel dimension of the RenderTarget.
     * @return pixel dimension
     */
    public abstract Vector2i getDimension();

    /**
     * Screenshot the RenderTarget and gives an image of it.
     * @return screenshot image
     */
    public abstract Image capture() ;

}
