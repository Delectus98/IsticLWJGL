package fr.delectus98.isticlwjgl.graphics.shaders;

import fr.delectus98.isticlwjgl.graphics.*;
import fr.delectus98.isticlwjgl.system.CameraOrtho;
import fr.delectus98.isticlwjgl.system.RenderTarget;
import fr.delectus98.isticlwjgl.system.Time;
import fr.delectus98.isticlwjgl.system.Viewport;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.ARBFramebufferObject.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30C.glBindFramebuffer;
import static org.lwjgl.opengl.GL30C.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class DepthBufferObject extends RenderTarget
{
    private int frameBufferId;
    private int depthTexture;
    private Vector2i dimension;
    private Texture texture;

    public DepthBufferObject(Vector2i dimension) throws IOException
    {
        this.dimension = dimension.clone();

        // The framebuffer, which regroups 0, 1, or more textures, and 0 or 1 depth buffer.
        frameBufferId = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);

        // Depth texture. Slower than a depth buffer, but you can sample it later in your shader
        depthTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, depthTexture);
        glTexImage2D(GL_TEXTURE_2D, 0,GL_DEPTH_COMPONENT16, dimension.x, dimension.y, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTexture, 0);

        glDrawBuffer(GL_NONE); // No color buffer is drawn to.

        texture = new Texture(depthTexture, dimension.x, dimension.y);

        // Always check that our framebuffer is ok
        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
            throw new IOException("Unable to create Depth buffer object.");

        this.glId = frameBufferId;

        this.camera = new CameraOrtho(new Vector2f(dimension), -1, 1000);
        this.viewport = new Viewport(new FloatRect(0,0, dimension.x, dimension.y));
        this.defaultCamera = camera;
        this.defaultViewport = viewport;

        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void resize(Vector2i dimension) throws IOException
    {
        this.free();

        this.dimension = dimension.clone();

        // The framebuffer, which regroups 0, 1, or more textures, and 0 or 1 depth buffer.
        frameBufferId = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);

        // Depth texture. Slower than a depth buffer, but you can sample it later in your shader
        depthTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, depthTexture);
        glTexImage2D(GL_TEXTURE_2D, 0,GL_DEPTH_COMPONENT16, dimension.x, dimension.y, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTexture, 0);

        glDrawBuffer(GL_NONE); // No color buffer is drawn to.

        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        // Always check that our framebuffer is ok
        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
            throw new IOException("Unable to create Depth buffer object.");

        this.glId = frameBufferId;

        this.camera = new CameraOrtho(new Vector2f(dimension), 0, 1000);
        this.viewport = new Viewport(new FloatRect(0,0, dimension.x, dimension.y));
        this.defaultCamera = camera;
        this.defaultViewport = viewport;
    }

    @Override
    protected void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);
    }

    @Override
    public void clear(ConstColor color) {
        this.setActive();
        glClearColor(color.getR(), color.getG(),color.getB(), color.getA());
    }

    @Override
    public void clear() {
        this.setActive();
        glClearColor(0,0,0, 1);
    }

    @Override
    public void draw(Drawable d, ConstShader shader) {
        if (!this.isActive()) {
            this.setActive();
            if (!shader.isBound())
                shader.bind();
            else Shader.rebind();
            this.applyView();
        }
        if (!shader.isBound() || this.needViewUpdate()) {
            if (!shader.isBound())
                shader.bind();
            this.applyView();
        }

        d.draw();
    }

    public final void display() {
        if (!this.isActive()) this.setActive();
        if (this.needViewUpdate()) this.applyView();

        glFlush();
    }

    @Override
    public Vector2i getDimension() {
        return dimension.clone();
    }

    @Override
    public Image capture() {
        return null;
    }

    public ConstTexture getTexture()
    {
        return texture;
    }

    @Override
    public void free() {
        if (frameBufferId == 0) return ;

        glDeleteFramebuffers(frameBufferId);
        if (this.isActive()) {
            glBindFramebuffer(GL_FRAMEBUFFER, 0);
        }
        frameBufferId = 0;
    }


}
