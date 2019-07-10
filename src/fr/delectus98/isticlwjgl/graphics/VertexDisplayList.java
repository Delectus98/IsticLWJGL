package fr.delectus98.isticlwjgl.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

/**
 * Old OpenGL means of drawing interface.
 */
public class VertexDisplayList implements Drawable {
    public enum Mode {
        TRIANGLES(GL_TRIANGLES, 3),
        TRIANGLES_FAN(GL_TRIANGLE_FAN, 3),
        TRIANGLES_STRIP(GL_TRIANGLE_STRIP, 3),
        QUADS(GL_QUADS, 4),
        POINTS(GL_POINTS, 1),
        LINES(GL_LINES, 2),
        LINES_STRIP(GL_LINE_STRIP, 2);

        protected int mode;
        protected int modulus;
        Mode(int glMode, int modulus){
            this.mode = glMode;
            this.modulus = modulus;
        }
    }

    private Mode drawMode;
    private int count = 0;
    public Vector3f[] positions;
    public Color[] colors;
    public Vector2f[] coords;

    public VertexDisplayList(int count, Mode mode) {
        this.resize(count, mode);
    }

    public int getVerticesCount() {
        return count;
    }

    public void setVertexPosition(int i, Vector2f position) {
        this.positions[i] = new Vector3f(position.x, position.y, 0);
    }
    public void setVertexPosition(int i, Vector3f position) {
        this.positions[i] = position.clone();
    }
    public void setVertexColor(int i, ConstColor color) {
        this.colors[i] = new Color(color.getR(), color.getG(), color.getB(), color.getA());
    }
    public void setVertexTexCoord(int i, Vector2f coords) {
        this.coords[i] = coords.clone();
    }

    public void resize(int count, Mode mode) {
        this.drawMode = mode;
        if (count <= 0 || count % mode.modulus != 0) {
            throw new RuntimeException("Selected mode requires :'" + mode.modulus + "' vertices per geometric object.");
        }
        this.count = count;
        positions = new Vector3f[count];
        colors = new Color[count];
        coords = new Vector2f[count];
    }

    public void draw() {
        glBegin(drawMode.mode);

        for (int i = 0 ; i < count ; ++i) {
            glColor4f(colors[i].r, colors[i].g, colors[i].b, colors[i].a);
            glTexCoord2f(coords[i].x, coords[i].y);
            glVertex3f(positions[i].x, positions[i].y, positions[i].z);
        }

        glEnd();
    }
}
