package fr.delectus98.isticlwjgl.graphics;


import fr.delectus98.isticlwjgl.opengl.GLM;


public class Sprite extends Shape {
    private ConstTexture texture = null;
    private float tx=0,ty=0,tw=0,th=0;

    public Sprite(){
        displayList = new VertexDisplayList(4, VertexDisplayList.Mode.QUADS);
        update();
        updateColor();
        updateCoords();
    }

    public Sprite(ConstTexture texture){
        this();
        this.setTexture(texture, true);
    }

    public void setTexture(ConstTexture texture) {
        this.setTexture(texture, true);
    }

    public void setTexture(ConstTexture texture, boolean resize){
        this.texture = texture;
        if (texture != null && resize) {
            width = texture.getWidth();
            height = texture.getHeight();

            tx = ty = 0;
            tw = 1.f;
            th = 1.f;

            updateCoords();
        }

        update();
    }

    /**
     *
     * @param x texture chosen x-pos [0 to texture.getWidth()-1]
     * @param y texture chosen y-pos [0 to texture.getHeight()-1]
     * @param w texture chosen width
     * @param h texture chosen height
     */
    public void setTextureRect(float x, float y, float w, float h){
        this.width = w;
        this.height = h;

        tx = x / texture.getWidth();
        ty = y / texture.getHeight();
        tw = w / texture.getWidth();
        th = h / texture.getHeight();

        update();
        updateCoords();
    }

    protected void update() {
        Vector2f center = new Vector2f(x, y);
        Vector2f tl = GLM.rotate(center, new Vector2f(x - ox, y - oy), cos, sin);
        Vector2f tr = GLM.rotate(center, new Vector2f(x - ox + sx * width, y - oy), cos, sin);
        Vector2f bl = GLM.rotate(center, new Vector2f(x - ox, y - oy + sy * height), cos, sin);
        Vector2f br = GLM.rotate(center, new Vector2f(x - ox + sx * width, y - oy + sy * height), cos, sin);

        displayList.setVertexPosition(0, tl);
        displayList.setVertexPosition(1, tr);
        displayList.setVertexPosition(2, br);
        displayList.setVertexPosition(3, bl);
    }

    @Override
    protected void updateColor(){
        displayList.setVertexColor(0, color);
        displayList.setVertexColor(1, color);
        displayList.setVertexColor(2, color);
        displayList.setVertexColor(3, color);
    }


    private void updateCoords(){
        displayList.setVertexTexCoord(0, new Vector2f(tx, ty));
        displayList.setVertexTexCoord(1, new Vector2f(tx + tw, ty));
        displayList.setVertexTexCoord(2, new Vector2f(tx + tw, ty + th));
        displayList.setVertexTexCoord(3, new Vector2f(tx, ty + th));
    }

    public void draw() {
        if (displayList == null) return ;

        if (texture != null) {
            texture.bind();
            displayList.draw();
        }
    }

    public ConstTexture getTexture(){
        return texture;
    }

    public FloatRect getTextureRect() {
        return new FloatRect(tx * texture.getWidth(),ty * texture.getHeight(),tw * texture.getWidth(),th * texture.getHeight());
    }

    @Override
    public FloatRect getBounds() {
        return new FloatRect(x - ox, y - oy, width * sx, height * sy);
    }

}
