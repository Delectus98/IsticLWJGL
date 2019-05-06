package Graphics;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

import System.GlObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * Interface that handle VBO structure and CPU to GPU stream
 */
public class VertexBuffer extends GlObject implements Drawable {
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
    public enum Usage{
        STREAM(GL_STREAM_DRAW),          // always
        DYNAMIC(GL_DYNAMIC_DRAW),        // often
        STATIC(GL_STATIC_DRAW);          // sometimes

        protected int usage;
        Usage(int glUsage){
            this.usage = glUsage;
        }
    }

    private Mode drawMode;
    private Usage usage;
    private int count = 0;
    private int[] vboArrayId = null;
    private int[] vboArraySampleSize = null;

    /**
     * Creates a Vertex Buffer Object with a specific Usage.
     * @param usage specified usage.
     */
    public VertexBuffer(Usage usage) {
        this.usage = usage;
    }

    /**
     * Creates a Vertex Buffer Object with a specific Usage.
     * @param verticesCount vertices count.
     * @param vaoCount Number of VAO. (positions, colors, texture coords)
     * @param vaoSampleSize For each VAO each vertices requires how many floats per sample. (0 < vaoSampleSize.length <= vaoCount)
     * @param mode draw mode.
     * @param usage usage specified usage.
     */
    public VertexBuffer(int verticesCount, int vaoCount, int[] vaoSampleSize, Mode mode, Usage usage) {
        this.usage = usage;
        this.create(verticesCount, vaoCount, vaoSampleSize, mode);
    }


    /**
     *
     * @param verticesCount number of vertices
     * @param vaoCount number of vao. Par exemple: [count=2](positions = 3, colors = 4) ou [count=3](positions = 3, colors = 4, texCoords = 2)
     * @param vaoSampleSize each vao has it's own number of float values per sample
     * @param mode primitive type
     */
    public void create(int verticesCount, int vaoCount, int[] vaoSampleSize, Mode mode) {
        this.free();

        //each draw mode needs a specified amount of vertices data
        this.drawMode = mode;
        if (verticesCount <= 0 || (verticesCount % mode.modulus != 0)) {
            throw new RuntimeException("Selected mode requires :'" + mode.modulus + "' vertices per geometric object.");
        }


        this.count = verticesCount;
        this.vboArrayId = new int[vaoCount];
        this.vboArraySampleSize = new int[vaoCount];



        // create VBOs
        for (int i=0 ; i < vaoCount ; ++i) {
            // create vertex buffer object (VBO) for vao[i] kind (positions, colors, texCoods, ...)
            this.vboArrayId[i] = GL15.glGenBuffers();
            this.vboArraySampleSize[i] = vaoSampleSize[i];
            glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vboArrayId[i]);
            glBufferData(GL15.GL_ARRAY_BUFFER, vaoSampleSize[i]*verticesCount, usage.usage);
        }
        // unbind VBO
        glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);



        // create vertex array object (VAO)
        super.glId = GL30.glGenVertexArrays(); //TODO context can't share VAO
        glBindVertexArray((int)super.glId);

        for (int i=0 ; i < vaoCount ; ++i) {
            glEnableVertexAttribArray(i);

            // assign vertex VBO to slot 'i' of VAO
            glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vboArrayId[i]);
            glVertexAttribPointer(i, vaoSampleSize[i], GL11.GL_FLOAT, false, 0, 0);
        }
        // unbind VBO
        glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

    }

    /**
     * Updates the GPU memory for a specific VAO.
     * @param vao specified vao
     * @param data New memory.
     */
    public void update(int vao, float[] data) {
        if (super.getGlId() == 0) return;

        if (vao < 0 || vao >= vboArrayId.length || data.length % vboArraySampleSize[vao] != 0) throw new RuntimeException("");

        glBindVertexArray((int)super.glId);
        // assign vertex VBO to slot 0 of VAO
        glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vboArrayId[vao]);
        glBufferData(GL15.GL_ARRAY_BUFFER, data, usage.usage);
        glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Frees the VertexBuffer from GPU memory.
     */
    @Override
    public void free() {
        if (super.getGlId() == 0) return;

        for (int i=0 ; i < vboArrayId.length ; ++i) {
            glDeleteBuffers(vboArrayId[i]);
        }
        this.vboArrayId = null;

        glDeleteVertexArrays((int)super.getGlId());
        //glDeleteBuffers((int)super.getGlId());
        super.glId = 0;
    }

    /**
     * Displays VBO.
     */
    @Override
    public void draw() {
        if (this.glId == 0) return ;

        //shader.bind();

        // bind vertex and color data
        glBindVertexArray((int)super.glId);

        // draw VAO
        GL11.glDrawArrays(drawMode.mode, 0, count);

        glBindVertexArray(0);

        //Shader.unbind();
    }
}
