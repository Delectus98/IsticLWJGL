package fr.delectus98.isticlwjgl.system;


import static org.lwjgl.glfw.GLFW.*;

public final class Keyboard {
    /**Context values*/
    private GLFWWindow window;

    /**
     * Generates a keyboard associated to a window.
     * Default layout is QWERTY.
     * @param window the keyboard will be associated to this window
     */
    public Keyboard(GLFWWindow window) {
        this.window = window;
    }

    /**
     * Generates a keyboard associated to a window using a specific layout.
     * If failed to set layout, the default layout become QWERTY.
     * @param window the keyboard will be associated to this window
     */
    public Keyboard(GLFWWindow window, int layout) {
        this.window = window;
    }

    /**
     * Checks if the keyboard key is pressed or not
     * @param key tested keyboard key
     * @return true if the keyboard key is pressed else false
     */
    public boolean isKeyPressed(int key) {
        if (window.getGlId() == 0) return false;

        int state = glfwGetKey(window.getGlId(), key);
        return (state == GLFW_PRESS);
    }

}