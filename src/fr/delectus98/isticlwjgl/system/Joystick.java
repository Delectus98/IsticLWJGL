package fr.delectus98.isticlwjgl.system;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Joystick {
    public enum GamePadButton {
        A(0), CROSS(0),
        B(1), CIRCLE(1),
        X(2), SQUARE(2),
        Y(3), TRIANGLE(3),

        LEFT_BUMPER(4),
        RIGHT_BUMPER(5),
        BACK(6),
        START(7),
        LEFT_THUMB(8),
        RIGHT_THUMB(9),

        UP(10),
        RIGHT(11),
        DOWN(12),
        LEFT(13),

        GUIDE(14),
        LAST(15);


        private int id;
        GamePadButton(int id) {
            this.id = id;
        }
    }
    public enum GamePadAxis {
        R2(5),
        L2(4),
        LEFT_AXIS_X(0),
        LEFT_AXIS_Y(1),
        RIGHT_AXIS_X(2),
        RIGHT_AXIS_Y(3);


        private int id;
        GamePadAxis(int id){
            this.id = id;
        }
    }

    private final int id;
    private final String name;

    public Joystick(int id) {
        this.id = id;
        String joystickName = glfwGetJoystickName(this.id);
        name = joystickName != null ? joystickName : "?";
    }

    public float[] getAllAxis() {
        if (!isPlugged()) return null;

        FloatBuffer axis = glfwGetJoystickAxes(id);

        float[] array = new float[axis.capacity()];
        axis.get(array);

        return array;
    }

    public byte[] getAllButtons(){
        if (!isPlugged()) return null;

        ByteBuffer buttons = glfwGetJoystickButtons(id);

        byte[] array = new byte[buttons.capacity()];
        buttons.get(array);

        return array;
    }

    public boolean isGamePadButtonPressed(GamePadButton button) {
        byte[] b = getAllButtons();
        return !(b == null || button.id >= b.length || button.id < 0) && this.isGamePad() && b[button.id] == GLFW_PRESS;
    }

    public boolean isButtonPressed(int button) {
        byte[] b = getAllButtons();
        return !(b == null || button >= b.length || button < 0) && b[button] == GLFW_PRESS;
    }

    public float getGamePadAxisValue(GamePadAxis axis, float threshold) {
        float[] b = getAllAxis();
        return !(b == null || axis.id >= b.length || axis.id < 0) && isGamePad() ? (Math.abs(b[axis.id]) < threshold ? 0 : b[axis.id]) : 0;
    }

    public float getAxisValue(int axis, float threshold) {
        float[] b = getAllAxis();
        return !(b == null || axis >= b.length || axis < 0) ? (Math.abs(b[axis]) < threshold ? 0 : b[axis])  : 0;
    }

    public String getJoystickName(){
        return name;
    }

    public boolean isPlugged() {
        return glfwJoystickPresent(id);
    }

    public boolean isGamePad() {
        return glfwJoystickIsGamepad(id);
    }

    public static boolean isPlugged(int id){
        return glfwJoystickPresent(id);
    }

    public static boolean isGamePad(int id){
        return glfwJoystickPresent(id);
    }

}
