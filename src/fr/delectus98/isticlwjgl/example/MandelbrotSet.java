package fr.delectus98.isticlwjgl.example;


import fr.delectus98.isticlwjgl.graphics.*;
import fr.delectus98.isticlwjgl.opengl.Version;
import fr.delectus98.isticlwjgl.system.*;
import fr.delectus98.isticlwjgl.system.io.AZERTYLayout;
import org.lwjgl.opengl.GL20;

import java.io.IOException;

public class MandelbrotSet {
    public static void main(String[] args) throws IOException {
        //GLFWWindow window = new GLFWWindow(VideoMode.getDesktopMode(), "Mandelbrot Set",  WindowStyle.DEFAULT.remove(WindowStyle.RESIZABLE));
        GLFWWindow window = new GLFWWindow(new VideoMode(500,500), "Mandelbrot Set",  WindowStyle.DEFAULT.remove(WindowStyle.RESIZABLE));
        window.setFrameRateLimit(60);

        Shader mandelbrot = new Shader("res/mandelbrot/mandelbrot.vert", "res/mandelbrot/mandelbrot.frag");

        int complexId = mandelbrot.getUniformLocation("c");
        int iterId = mandelbrot.getUniformLocation("iter");
        int offsetXId = mandelbrot.getUniformLocation("offsetX");
        int offsetYId = mandelbrot.getUniformLocation("offsetY");
        int scaleXId = mandelbrot.getUniformLocation("scaleX");
        int scaleYId = mandelbrot.getUniformLocation("scaleY");

        mandelbrot.bind();
        //mandelbrot.setUniform(complexId, 0.34845f, 0.4344545f);
        //mandelbrot.setUniform(complexId, 0.34845f, 0.4344545f);
        //mandelbrot.setUniform(complexId, -0.84845f, -0.4344545f);
        //mandelbrot.setUniform(complexId, 0.04845f, -0.7344545f);
        mandelbrot.setUniform(complexId, 0.f, 1.0044545f);
        int iter = 1000;
        mandelbrot.setUniform(iterId, iter);
        float offsetX = 0, offsetY = 0;
        mandelbrot.setUniform(offsetXId, offsetX);
        mandelbrot.setUniform(offsetYId, offsetY);
        float scaleX = 2, scaleY = 2;
        mandelbrot.setUniform(scaleYId, scaleX);
        mandelbrot.setUniform(scaleYId, scaleY);

        ConstTexture t = Texture.DefaultTexture();
        Shape shape = new Sprite(t);
        shape.setScale((float)window.getDimension().x / t.getWidth(), (float)window.getDimension().y / t.getHeight());

        Keyboard k = new Keyboard(window);

        while (window.isOpen())
        {
            Event event;
            while ((event = window.pollEvents()) != null)
            {
                if (event.type == Event.Type.CLOSE)
                {
                    window.close();
                }
            }

            float speed = 0.01f;

            if (k.isKeyPressed(AZERTYLayout.UP_ARROW.getKeyID()))
            {
                offsetY -= scaleY * speed;
            }
            if (k.isKeyPressed(AZERTYLayout.DOWN_ARROW.getKeyID()))
            {
                offsetY += scaleY * speed;
            }
            if (k.isKeyPressed(AZERTYLayout.RIGHT_ARROW.getKeyID()))
            {
                offsetX += scaleX * speed;
            }
            if (k.isKeyPressed(AZERTYLayout.LEFT_ARROW.getKeyID()))
            {
                offsetX -= scaleX * speed;
            }
            if (k.isKeyPressed(AZERTYLayout.PAD_PLUS.getKeyID()))
            {
                scaleX -= scaleX * speed;
                scaleY -= scaleY * speed;
            }
            if (k.isKeyPressed(AZERTYLayout.PAD_MINUS.getKeyID()))
            {
                scaleX += scaleX * speed;
                scaleY += scaleY * speed;
            }
            if (k.isKeyPressed(AZERTYLayout.PAGE_DOWN.getKeyID()))
            {
                iter += 1;
                System.out.println(iter);
            }
            if (k.isKeyPressed(AZERTYLayout.PAGE_UP.getKeyID()))
            {
                iter -= 1;
                iter = Math.max(iter, 1);
            }


            mandelbrot.setUniform(offsetXId, offsetX);
            mandelbrot.setUniform(offsetYId, offsetY);
            mandelbrot.setUniform(scaleXId, scaleX);
            mandelbrot.setUniform(scaleYId, scaleY);
            mandelbrot.setUniform(iterId, iter);

            window.clear();

            window.draw(shape, mandelbrot);

            window.display();
        }
    }
}
