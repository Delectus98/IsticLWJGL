package fr.delectus98.isticlwjgl.example;

import fr.delectus98.isticlwjgl.graphics.*;

import java.io.IOException;

import fr.delectus98.isticlwjgl.system.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;


public class TestGLFWWindow {
    public static void main(String[] args) throws IOException {

        GLFWWindow window = new GLFWWindow(VideoMode.getDesktopMode(), "fr/delectus98/isticlwjgl/opengl", WindowStyle.DEFAULT/*.remove(WindowStyle.VSYNC)*/);

        Texture texture;
        try {
            texture = new Texture("Phases.bmp");
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }


        window.setActive();

        RectangleShape shapeA1 = new RectangleShape(10,10, 10,10);
        shapeA1.setOrigin(5.f, 5.f);
        shapeA1.setFillColor(Color.Red);

        RectangleShape shapeA2 = new RectangleShape(10,100, 50,50);
        shapeA2.setFillColor(Color.Yellow);
        Sprite spriteA1 = new Sprite(texture);
        //spriteA1.setScale(-1.f, -1.f);
        spriteA1.setPosition(0,1);
        //spriteA1.setOrigin(spriteA1.getBounds().w / 2.f, spriteA1.getBounds().h / 2.f);
        //spriteA1.rotate(3.14f / 2.f);
        //spriteA1.setRotation(90.f/180.f*(float)Math.PI);

        RectangleShape shapeB1 = new RectangleShape(10,10, 10,10);
        shapeB1.setFillColor(Color.Red);
        RectangleShape shapeB2 = new RectangleShape(10,100, 50,50);
        shapeB2.setFillColor(Color.Yellow);
        Sprite spriteB1 = new Sprite(texture);
        spriteB1.setOrigin(spriteB1.getBounds().w / 2.f, spriteB1.getBounds().h / 2.f);
        //sprite.setOrigin(sprite.getBounds().w/2.f, sprite.getBounds().h/2.f);
        spriteB1.setRotation(10.f/180.f*(float)Math.PI);



        Keyboard keyboard = new Keyboard(window);

        //Camera is not default window camera so it need to be updated when window is resized
        Camera2D tracker = new Camera2D(window);
        //window.setCamera(tracker);

        Mouse mouse = new Mouse(window);

        while (window.isOpen()) {
            if (keyboard.isKeyPressed(GLFW_KEY_LEFT)) {
                shapeA1.move(-1f, 0);
            }
            if (keyboard.isKeyPressed(GLFW_KEY_RIGHT)) {
                shapeA1.move(1f, 0);
            }
            if (keyboard.isKeyPressed(GLFW_KEY_UP)) {
                shapeA1.move(0, -1f);
            }
            if (keyboard.isKeyPressed(GLFW_KEY_DOWN)) {
                shapeA1.move(0, 1f);
            }

            tracker.setCenter(shapeA1.getPosition());

            shapeA1.rotate(0.01f);

            Event event;




            if (spriteA1.getBounds().contains(mouse.getRelativePosition().x, mouse.getRelativePosition().y)) {
                spriteA1.setFillColor(Color.White);
            } else {
                spriteA1.setFillColor(Color.Blue);
            }



            window.clear(Color.Blue);
            //window.draw(shapeA1);
            //window.draw(shapeA2);
            window.draw(spriteA1);
            window.display();

            /*window2.clear();
            untexturedShader.bind();
            window2.getCamera().setUniformMVP(0);
            window2.draw(shapeB2);
            texturedShader.bind();
            window2.getCamera().setUniformMVP(0);
            window2.draw(spriteB1);
            window2.display();*/



            while ((event = window.pollEvents()) != null) {
                if (event.type == Event.Type.CLOSE) {
                    window.close();
                    System.exit(0);
                }
                if (event.type == Event.Type.KEYRELEASED && event.keyReleased == GLFW_KEY_P) {
                    System.out.println("Screenshot! 'capture.png'");
                    try {
                        window.capture().saveAs("capture.png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //Camera is not default window camera so it needs to be updated when window is resized
                if (event.type == Event.Type.RESIZE) {
                    tracker.setDimension(new Vector2f(event.resizeX, event.resizeY));
                }
                if (event.type == Event.Type.MOUSEDROP) {
                    System.out.println("Mouse drop! 'window one'");
                }
                if (event.type == Event.Type.KEYPRESSED) {
                    System.out.println("KEYPRESSED! 'window one'");
                }
            }
        }
    }

}
