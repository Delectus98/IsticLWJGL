package Graphics;

import OpenGL.GLM;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;


public class RectangleShape extends Shape {
    public RectangleShape(){
        this(0,0);
    }

    public RectangleShape(float w, float h){
        this(0,0, w, h);
    }

    public RectangleShape(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        displayList = new VertexDisplayList(4 , VertexDisplayList.Mode.QUADS);

        update();
        updateColor();
        displayList.setVertexTexCoord(0, new Vector2f(0,0));
        displayList.setVertexTexCoord(1, new Vector2f(0 , 1));
        displayList.setVertexTexCoord(2, new Vector2f(1, 1));
        displayList.setVertexTexCoord(3, new Vector2f(1, 0));
    }

    public void setSize(float x, float y){
        this.width = x;
        this.height = y;
        update();
    }

    @Override
    protected void updateColor(){
        displayList.setVertexColor(0, color);
        displayList.setVertexColor(1, color);
        displayList.setVertexColor(2, color);
        displayList.setVertexColor(3, color);
    }

    @Override
    protected void update(){
        Vector2f center = new Vector2f(x, y);
        Vector2f tl = GLM.rotate(center, new Vector2f(x - ox, y - oy), cos, sin);
        Vector2f tr = GLM.rotate(center, new Vector2f(x - ox + sx * width, y - oy), cos, sin);
        Vector2f bl = GLM.rotate(center, new Vector2f(x - ox, y - oy + sy * height), cos, sin);
        Vector2f br = GLM.rotate(center, new Vector2f(x - ox + sx * width, y - oy + sy * height), cos, sin);

        displayList.setVertexPosition(0, new Vector3f(tl.x, tl.y, z));
        displayList.setVertexPosition(1, new Vector3f(tr.x, tr.y, z));
        displayList.setVertexPosition(2, new Vector3f(br.x, br.y, z));
        displayList.setVertexPosition(3, new Vector3f(bl.x, bl.y, z));
    }

    @Override
    public void draw() {
        Texture.DefaultTexture().bind();
        //glEnable(GL_TEXTURE_2D);
        displayList.draw();
    }

    @Override
    public FloatRect getBounds() {
        return new FloatRect(x - ox, y - oy, width * sx, height * sy);
    }
}
