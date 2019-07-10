package fr.delectus98.isticlwjgl.example;

import fr.delectus98.isticlwjgl.system.*;

import fr.delectus98.isticlwjgl.graphics.*;
import fr.delectus98.isticlwjgl.graphics.Color;
import fr.delectus98.isticlwjgl.system.io.AutoKeyboardLayout;

import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;

//TODO trouver un moyen de ne transmettre la MVP qu'une seule fois (uniform blocks, uniform buffer object)
public class RenderWindow {
    public static void main(String[] args) {

        GLFWWindow window = new GLFWWindow(
                VideoMode.getDesktopMode(),
                "fr/delectus98/isticlwjgl/opengl",
                WindowStyle.DEFAULT
                //WindowStyle.FULLSCREEN
        );
        //window.setFrameRateLimit(120);

        //RenderTexture renderTexture;
        //RenderTexture renderTexture2;

        Texture texture;
        Texture texture2;
        Texture texture3;
        Texture transp;
        FontFamily font;

        try {
            //renderTexture = new RenderTexture(200,200);
            //renderTexture2 = new RenderTexture(200,200);

            texture = new Texture("dalle.png");
            texture.setWrapMode(Texture.REPEAT);
            texture2 = new Texture("Phases.bmp");
            texture3 = new Texture("ben10.png");
            transp = new Texture("character.png");

            font = new FontFamily("default.ttf", 30);
            //font = new FontFamily("asmelina.ttf", 24);
            //font = new FontFamily("arial.ttf", 24);
            //font = new FontFamily("mono.ttf", 24);
        } catch (IOException e) {
            window.close();
            e.printStackTrace();
            return ;
        }

        Text text = new Text(font, "Phrase Italique!", Text.ITALIC);
        Text text2 = new Text(font, "Phrase Normale!", Text.REGULAR);
        Text text3 = new Text(font, "Phrase Grasse!", Text.BOLD);
        Text fpsText = new Text(font, "0", Text.BOLD);
        fpsText.setFillColor(Color.Blue);

        text.setFillColor(Color.Black);
        text2.setFillColor(new Color(1, 0.59f, 0.2f));
        text3.setFillColor(new Color(0.59f, 1f, 0.5f));
        //text2.setScale(0.2f,0.5f);
        RectangleShape textShape = new RectangleShape(text2.getBounds().l, text2.getBounds().t, text2.getBounds().w, text2.getBounds().h);
        textShape.setFillColor(Color.Red);

        Sprite transptransp = new Sprite(transp);
        transptransp.setFillColor(new Color(1,1,1,0.5f));


        RectangleShape shape = new RectangleShape(10,10, 10,10);
        shape.setOrigin(5,5);
        shape.setFillColor(Color.Red);

        RectangleShape background = new RectangleShape(200,200);
        background.setFillColor(Color.Blue);

        RectangleShape fullBackground = new RectangleShape(500,500);
        fullBackground.move(-300,-300);
        fullBackground.setFillColor(Color.Red);


        Sprite sprite = new Sprite(texture);
        sprite.move(50, 50);

        /*Sprite screen = new Sprite(renderTexture.getTexture());
        screen.setTextureRect(0,0,400,400);
        screen.move(50,50);
        //screen.setScale(0.5f,-0.5f);

        Sprite screen2 = new Sprite(renderTexture2.getTexture());
        screen2.setTextureRect(0,0,200,200);
        screen.move(30,300);*/

        Keyboard keyboard = new Keyboard(window);

        Camera2D camera = new Camera2D(window);
        camera.setZfar(10.f);
        camera.setZnear(-10.f);
        window.setCamera(camera);

        //renderTexture.setCamera(screenCamera);
        //renderTexture2.setCamera(screenCamera2);


        Mouse mouse = new Mouse(window);

        ArrayList<Shape> array = new ArrayList<>();
        final int arraySize = 1000;
        for (int i = 0; i < arraySize ; ++i) {
            Sprite tmp;
            if (i%3 == 0)
                tmp = new Sprite(texture);
            else if (i%3 == 1)
                tmp = new Sprite(texture2);
            else tmp = new Sprite(texture3);
            //RectangleShape tmp = new RectangleShape(1000,100);
            tmp.move((i%(int)Math.sqrt(arraySize))*200.f,(i/(int)Math.sqrt(arraySize))*200.f);
            tmp.setFillColor(new Color(1.f - i / (float)arraySize, i / (float)arraySize, i / (float)arraySize));
            tmp.setScale(0.5f, 0.5f);
            array.add(tmp);
        }

        Clock clk = new Clock(Clock.Mode.NANOSECONDS_ACCURACY);

        //Viewport viewport = new Viewport(new FloatRect(0,0, 2000,1024));
        //window.setViewport(viewport);

        Viewport viewport2 = new Viewport(new FloatRect(400,50, 900,400));


        Time elapsedSinceBeginning = Time.seconds(0);
        while (window.isOpen()) {
            Time elapsed = clk.restart();
            elapsedSinceBeginning.add(elapsed);
            //System.out.println(1.0/elapsed.asSeconds());
            fpsText.setString("fps:"+(int)(1.0/elapsed.asSeconds()));
            //out.println("abs:" + mouse.getAbsolutePosition().x + ":" + mouse.getAbsolutePosition().y);
            //out.println("rel:" + mouse.getRelativePosition().x + ":" + mouse.getRelativePosition().y);

            Event event;
            while ((event = window.pollEvents()) != null) {
                if (event.type == Event.Type.CLOSE) {

                    window.close();
                    System.exit(0);
                }
            }


            Vector2f mousePos = mouse.getRelativePositionUsingCamera2D(window.getViewport(), camera);
            if (shape.getBounds().contains(mousePos.x, mousePos.y)) {
                shape.setFillColor(Color.Yellow);
            } else {
                shape.setFillColor(Color.White);
            }

            shape.move((float)elapsed.asSeconds()*100, (float)elapsed.asSeconds()*100);
            fullBackground.move((float)elapsed.asSeconds()*100, (float)elapsed.asSeconds()*100);
            transptransp.move((float)elapsed.asSeconds()*100, (float)elapsed.asSeconds()*100);




            camera.setDimension(window.getViewport().getDimension());
            camera.setCenter(shape.getPosition());
            if (keyboard.isKeyPressed(GLFW_KEY_P)) {
                /*try {
                    renderTexture.capture().saveAs("target.jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            } else if (keyboard.isKeyPressed(AutoKeyboardLayout.Q.getKeyID())) {
                window.close();
                return ;
            }

            //test viewport 1
            //window.setViewport(viewport);
            window.clear(Color.Green);
            //texturedShader.bind();
            //window.getCamera().setUniformMVP(0);
            for (int i = 0; i < array.size() ; ++i) {
                window.draw(array.get(i));
            }
            window.draw(fullBackground);

            window.draw(shape);


            /*window.draw(screen);
            window.draw(screen2);*/
            fpsText.setPosition(shape.getPosition().x-200, shape.getPosition().y - 50);

            fpsText.draw();

            /*screen.draw();
            text.setPosition(shape.getPosition().x, shape.getPosition().y);
            text2.setPosition(shape.getPosition().x, shape.getPosition().y + 100);
            text3.setPosition(shape.getPosition().x, shape.getPosition().y + 200);

            textShape.setPosition(shape.getPosition().x, shape.getPosition().y + 100);
            textShape.draw();
            text.draw();
            text2.draw();
            text3.draw();*/
            //screen2.draw();

            //fpsText.draw();


            //test viewport 2
            /*camera.setCenter(shape.getPosition());
            viewport.setTopLeftCorner(new Vector2f(30,30));
            camera.setDimension(viewport2.getDimension());
            window.setViewport(viewport2);
            window.draw(screen);
            window.draw(screen2);
            window.draw(shape);*/
            window.draw(transptransp);

            window.display();
        }

        for (int i = 0; i < array.size() ; ++i) {
            //array.get(i).free();
        }
    }
}

