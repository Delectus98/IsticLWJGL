package fr.delectus98.isticlwjgl.example;

import fr.delectus98.isticlwjgl.graphics.*;
import fr.delectus98.isticlwjgl.system.*;
import fr.delectus98.isticlwjgl.system.io.AZERTYLayout;

import java.io.IOException;


public class ConwayGame {
    public static void main(String[] args) throws IOException {
        int w = 100, h = 100;
        GLFWWindow window = new GLFWWindow(new VideoMode(w, h), "Mandelbrot Set",  WindowStyle.DEFAULT.remove(WindowStyle.RESIZABLE));
        window.setFrameRateLimit(10);

        Shader conway = new Shader("res/conway/conway.vert", "res/conway/conway.frag");

        Keyboard k = new Keyboard(window);

        RenderTexture target = new RenderTexture(w, h);

        target.clear(Color.White);

        Shape shape = new Sprite(target.getTexture());

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

            target.draw(shape, conway);
            target.display();

            window.clear();

            window.draw(shape);

            window.display();
        }
    }
}