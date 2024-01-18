package fr.delectus98.isticlwjgl.system;

import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;


public final class GLFWContext {
    private static long mainThreadId = Thread.currentThread().getId();
    private static int contextCount = 0;

    /**
     * When first context is created we must initialize GLFW.
     * Only one window is enable.
     */
    public synchronized static void createContext(){
        // Checks if context is created within the Main thread
        if (Thread.currentThread().getId() != mainThreadId) {
            throw new RuntimeException("GLFW context is not initialized by the Main thread");
        }

        if (contextCount++ == 0) {
            // Setup an error callback. The default implementation
            // will print the error message in System.err.
            // GLFWErrorCallback.createPrint(System.err).set();

            // Initialize GLFW. Most GLFW functions will not work before doing this.
            if (!glfwInit()) {
                throw new RuntimeException("Unable to initialize GLFW.");
            }
        }
    }

    /**
     * When last context is delete we must terminate GLFW
     */
    public synchronized static void deleteContext(){
        if (Thread.currentThread().getId() != mainThreadId) {
            throw new RuntimeException("GLFW context is not removed by the Main thread");
        }

        contextCount--;
        if (contextCount == 0) {
            System.out.println("glfwTerminate() called");
            glfwTerminate();
        }
    }

    /**
     * We can't initialize GLFWContext
     */
    private GLFWContext(){}

}
