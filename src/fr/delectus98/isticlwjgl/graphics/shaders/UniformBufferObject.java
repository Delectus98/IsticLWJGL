package fr.delectus98.isticlwjgl.graphics.shaders;

import fr.delectus98.isticlwjgl.system.GlObject;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15C.glGenBuffers;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;

public class UniformBufferObject extends GlObject {
    public UniformBufferObject(float[] data){
        super.glId = glGenBuffers();
        glBindBuffer(GL_UNIFORM_BUFFER, (int)super.glId);
        glBufferData(GL_UNIFORM_BUFFER, data.length, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_UNIFORM_BUFFER, 0);
    }

    public void update(float[] data){
        if (super.glId == 0) return ;

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        glBindBuffer(GL_UNIFORM_BUFFER, (int)super.glId);
        glBufferSubData(GL_UNIFORM_BUFFER, 0, buffer);
        glBindBuffer(GL_UNIFORM_BUFFER, 0);
    }

    public void free() {
        if (super.glId == 0) return ;

        glDeleteBuffers((int)super.glId);
        super.glId = 0;
    }
}
