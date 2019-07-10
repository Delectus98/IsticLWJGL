package fr.delectus98.isticlwjgl.example;


import fr.delectus98.isticlwjgl.graphics.Shader;
import fr.delectus98.isticlwjgl.system.*;
import fr.delectus98.isticlwjgl.graphics.Color;
import fr.delectus98.isticlwjgl.graphics.RectangleShape;
import org.lwjgl.opengl.GL;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;


/**https://stackoverflow.com/questions/17779340/glfw-3-0-resource-loading-with-opengl*/
@Deprecated
public class ThreadGLFWWindow {
    public static void thread(GLFWWindow window, Shader s) {
        glfwMakeContextCurrent(window.getGlId());
        GL.createCapabilities();

        window.setActive();

        try {
            s.loadFromMemory("void main(){}", "void main(){}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args){
        //TODO Throw inside GLFWContext.createContext an exception when used outside Main Thread
        GLFWContext.createContext();

        Shader s = new Shader();

        GLFWWindow threaded = new GLFWWindow(new VideoMode(500, 500), "Threaded Window", WindowStyle.DEFAULT);
        Thread thread = new Thread(() -> { ThreadGLFWWindow.thread(threaded, s); });
        thread.start();


        /*try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        GLFWWindow window = new GLFWWindow(new VideoMode(500, 500), "Main Thread Window", WindowStyle.DEFAULT, CallbackMode.DEFAULT);
        RectangleShape shape = new RectangleShape(10, 10, 10, 10);
        shape.setFillColor(Color.Red);

        Mouse mouse = new Mouse(window);

        while (window.isOpen()) {
            //display
            if (mouse.isButtonPressed(Mouse.Button.Left)) {
                window.clear(Color.Black);
            }

            shape.move(1.f, 1.f);

            window.draw(shape);

            window.display();

            //poll events
            Event event;
            while ((event = window.pollEvents()) != null) {
                if (event.type == Event.Type.CLOSE) {
                    window.close();
                    threaded.close();
                    return ;
                }
                if (event.type == Event.Type.MOUSELEAVE) {
                    System.out.println("Mouse leave the window");
                }
                if (event.type == Event.Type.MOUSESCROLL) {
                    System.out.println("Mouse is scrolling");
                    shape.move(0, event.scrollY);
                }
                if (event.type == Event.Type.MOUSEENTER) {
                    System.out.println("Mouse enter inside the window");
                }
                if (event.type == Event.Type.MOUSEDROP) {
                    System.out.println("Mouse is dropping files");
                }
                if (event.type == Event.Type.JOYSTICK_CONNECTION) {
                    System.out.println("Joystick event");
                }
                if (event.type == Event.Type.JOYSTICK_DISCONNECTION) {
                    System.out.println("Joystick event");
                }
                if (event.type == Event.Type.BUTTONRELEASED) {
                    System.out.println("Button released detected");
                }
                if (event.type == Event.Type.BUTTONPRESSED) {
                    System.out.println("Button pressed detected");
                }
                if (event.type == Event.Type.RESIZE) {
                    System.out.println("Window resized");
                    //
                }
                if (event.type == Event.Type.MOVE) {
                    System.out.println("Window moved");
                }
                if (event.type == Event.Type.FOCUS) {
                    System.out.println("Window gained focus");
                }
                if (event.type == Event.Type.UNFOCUS) {
                    System.out.println("Window lost focus");
                }
            }



            threaded.clear(Color.Red);
            threaded.display();

            //poll events
            while ((event = threaded.pollEvents()) != null) {
                if (event.type == Event.Type.CLOSE) {
                    threaded.close();
                    return ;
                }
                if (event.type == Event.Type.MOUSELEAVE) {
                    System.out.println("Mouse leave the window 2");
                }
                if (event.type == Event.Type.MOUSESCROLL) {
                    System.out.println("Mouse is scrolling 2");
                    shape.move(0, event.scrollY);
                }
                if (event.type == Event.Type.MOUSEENTER) {
                    System.out.println("Mouse enter inside the window 2");
                }
                if (event.type == Event.Type.MOUSEDROP) {
                    System.out.println("Mouse is dropping files 2");
                }
                if (event.type == Event.Type.JOYSTICK_CONNECTION) {
                    System.out.println("Joystick event 2");
                }
                if (event.type == Event.Type.JOYSTICK_DISCONNECTION) {
                    System.out.println("Joystick event 2");
                }
                if (event.type == Event.Type.BUTTONRELEASED) {
                    System.out.println("Button released detected 2");
                }
                if (event.type == Event.Type.BUTTONPRESSED) {
                    System.out.println("Button pressed detected 2");
                }
                if (event.type == Event.Type.RESIZE) {
                    System.out.println("Window resized 2");
                    //
                }
                if (event.type == Event.Type.MOVE) {
                    System.out.println("Window moved 2");
                }
                if (event.type == Event.Type.FOCUS) {
                    System.out.println("Window gained focus 2");
                }
                if (event.type == Event.Type.UNFOCUS) {
                    System.out.println("Window lost focus 2");
                }
            }
        }


        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GLFWContext.deleteContext();
    }
}
