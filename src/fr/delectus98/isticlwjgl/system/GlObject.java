package fr.delectus98.isticlwjgl.system;

/**
 * GlObject uses internally OpenGL resources and own an id associated to those resources.
 */
public abstract class GlObject {
    protected long glId = 0;

    protected GlObject(){}

    protected GlObject(int glId){
        this.glId = glId;
    }

    /**
     * Frees OpenGL resources and set id to 0.
     */
    public abstract void free();

    public long getGlId() {
        return glId;
    }
}
