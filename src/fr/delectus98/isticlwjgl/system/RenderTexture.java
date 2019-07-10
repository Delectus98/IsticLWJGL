package fr.delectus98.isticlwjgl.system;

import fr.delectus98.isticlwjgl.graphics.*;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * RenderTexture specifies a Texture which can be edited by the GPU by drawing stuffs on it.
 */
public final class RenderTexture extends RenderTarget {
    private int fboId;
    private int depthId;

    private Texture texture = null;

    public RenderTexture(int width, int height) throws IOException {
        super();

        //frame buffer
        this.fboId = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, this.fboId);

        //depth buffer
        this.depthId = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, this.depthId);

        //allocate space for the renderbuffer
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);

        //attach depth buffer to fbo
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, this.depthId);

        //create texture to render to
        this.glId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, (int)this.glId);
        // Poor filtering. Needed !
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (java.nio.ByteBuffer)null);

        //attach texture to the fbo
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, (int)this.glId, 0);

        //check completeness
        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
            throw new IOException("An error occured creating the frame buffer.");

        texture = new Texture((int)this.glId, width, height);

        this.initGl();

        glBindFramebuffer(GL_FRAMEBUFFER, 0);

    }

    private void initGl(){
        defaultCamera = new Camera2D(new Vector2f(texture.getWidth(), texture.getHeight()));
        camera = defaultCamera;
        defaultViewport = new Viewport(new FloatRect(0,0, texture.getWidth(), texture.getHeight()));
        viewport = defaultViewport;

        camera.apply();
        viewport.apply(this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public final void clear(ConstColor color) {
        if (!this.isActive()) {
            this.setActive();
            Shader.rebind();
            this.applyView();
        }
        if (this.needViewUpdate()) {
            //Shader.rebind();
            this.applyView();
        }

        glClearColor(color.getR(), color.getG(), color.getB(), color.getA());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public final void clear() {
        clear(Color.Black);
    }

    @Override
    public final void draw(Drawable drawable, ConstShader shader) {
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

        drawable.draw();
    }

    public final void display(){
        if (!this.isActive()) this.setActive();
        if (this.needViewUpdate()) this.applyView();

        glFlush();
        //glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    /**
     * Select the frame buffer of the RenderTexture to allow draws on Texture.
     * Make 'this' as the current RenderTarget.
     */
    protected final void bind(){
        //render to fbo
        glBindFramebuffer(GL_FRAMEBUFFER, this.fboId);

        camera.apply();
        viewport.apply(this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public final Vector2i getDimension() {
        return new Vector2i(texture.getWidth(), texture.getHeight());
    }

    public final Texture getTexture(){
        return texture;
    }

    @Override
    public final void free() {
        if (texture != null)
            texture.free();
        texture = null;
    }

    @Override
    @Deprecated
    public final Image capture() {
        final int bpp = 4;

        if (!this.isActive()) this.setActive();
        else if (this.needViewUpdate()) this.applyView();

        return texture.toImage();
        //this.bind();

        /*int width = texture.getWidth();
        int height = texture.getHeight();


        glReadBuffer(GL_FRONT);
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * bpp);

        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        byte[] array = new byte[width*height*bpp];
        buffer.get(array);

        //invert y
        for (int i=0;i < width ; ++i) {
            for (int j=0; j < height/2 ; ++j) {
                byte[] rgba = new byte[]{
                        array[i * bpp + j * width * bpp + 0],
                        array[i * bpp + j * width * bpp + 1],
                        array[i * bpp + j * width * bpp + 2],
                        array[i * bpp + j * width * bpp + 3]
                };

                array[i * bpp + j * width * bpp + 0] = array[i * bpp + (height - j - 1) * width * bpp + 0];
                array[i * bpp + j * width * bpp + 1] = array[i * bpp + (height - j - 1) * width * bpp + 1];
                array[i * bpp + j * width * bpp + 2] = array[i * bpp + (height - j - 1) * width * bpp + 2];
                array[i * bpp + j * width * bpp + 3] = array[i * bpp + (height - j - 1) * width * bpp + 3];

                array[i * bpp + (height - j - 1) * width * bpp + 0] = rgba[0];
                array[i * bpp + (height - j - 1) * width * bpp + 1] = rgba[1];
                array[i * bpp + (height - j - 1) * width * bpp + 2] = rgba[2];
                array[i * bpp + (height - j - 1) * width * bpp + 3] = rgba[3];
            }
        }*/

        //return new Image(array, width, height);
    }
}
