package fr.delectus98.isticlwjgl.graphics;

/**
 * Shape can be drawn by GPU and can be transformed.
 * Only the window activated during the Shape creation can draw the shape. (Because VBO can't be shared)
 * To share to a specific window you must activate it before.
 */
public abstract class Shape extends Transformable implements Drawable
{
    protected Color color = new Color(255, 255, 255, 255);
    protected float width = 0, height = 0;

    //protected VertexArrayObject buffer;
    protected VertexDisplayList displayList = null;

    /**
     * Applies new color to the graphic component
     * @param color fill color
     */
    public void setFillColor(ConstColor color)
    {
        this.color = new Color(color.getR(), color.getG(), color.getB(), color.getA());
        updateColor();
    }

    public ConstColor getFillColor()
    {
        return color;
    }

    protected abstract void updateColor();


    /**
     * Global bounds of the shape
     * @return bounds
     */
    public abstract FloatRect getBounds();

}
