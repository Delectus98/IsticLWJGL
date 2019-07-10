package fr.delectus98.isticlwjgl.system.io;

import java.awt.im.InputContext;
import java.util.*;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;

public enum AutoKeyboardLayout implements KeyboardLayout {

    //never change
    ESCAPE,
    CAPS_LOCK,
    LEFT_SHIFT,
    RIGHT_SHIFT,
    TAB,
    LEFT_CONTROL,
    RIGHT_CONTROL,
    LEFT_ALT,
    RIGHT_ALT,
    BACKSPACE,
    SPACE,
    RETURN,
    MENU,
    LEFT_SYSTEM_KEY,
    RIGHT_SYSTEM_KEY,

    PRINT,
    PAUSE,
    SCROLL_LOCK,

    PAGE_UP,
    PAGE_DOWN,
    INSERT,
    DELETE,
    END,
    HOME, //<-

    LEFT_ARROW,
    RIGHT_ARROW,
    UP_ARROW,
    DOWN_ARROW,

    F1,
    F2,
    F3,
    F4,
    F5,
    F6,
    F7,
    F8,
    F9,
    F10,
    F11,
    F12,
    F13,
    F14,
    F15,
    F16,
    F17,
    F18,
    F19,
    F20,
    F21,
    F22,
    F23,
    F24,
    F25,

    NUM_LOCK,
    PAD_1,
    PAD_2,
    PAD_3,
    PAD_4,
    PAD_5,
    PAD_6,
    PAD_7,
    PAD_8,
    PAD_9,
    PAD_0,
    PAD_SLASH,
    PAD_ASTERISK,
    PAD_PLUS,
    PAD_MINUS,
    PAD_RETURN,
    PAD_DELETE,

    NUM_1,
    NUM_2,
    NUM_3,
    NUM_4,
    NUM_5,
    NUM_6,
    NUM_7,
    NUM_8,
    NUM_9,
    NUM_0,
    EQUAL,

    //sometimes change
    A,
    Z,
    E,
    R,
    T,
    Y,
    U,
    I,
    O,
    P,
    Q,
    S,
    D,
    F,
    G,
    H,
    J,
    K,
    L,
    M,
    W,
    X,
    C,
    V,
    B,
    N;

    private static Locale locale = null;
    private static Map<String, KeyboardLayout> keys = new HashMap<>();

    private static KeyboardLayout[] getCurrentLayout(){
        if (locale.equals(Locale.FRANCE) || locale.equals(Locale.FRENCH)) {
            return AZERTYLayout.values();
        } else {
            return QWERTYLayout.values();
        }
    }

    /**
     * Creates a default Keyboard Layout using OS default layout only once
     */
    private static void settle(){
        if (locale == null) {
            locale = InputContext.getInstance().getLocale();

            Arrays.stream(AutoKeyboardLayout.values())
                    .forEach(key ->
                            Arrays.stream(getCurrentLayout())
                                    .filter(key2 -> key2.getKeyName().equals(key.getKeyName()))
                                    .forEach(key2 -> keys.put(key2.getKeyName(), key2)
                                    )
                    );
        }
    }

    @Override
    public int getKeyID() {
        settle();
        KeyboardLayout layout = keys.get(this.getKeyName());
        return layout == null ? GLFW_KEY_UNKNOWN : layout.getKeyID();
    }

    @Override
    public String getKeyName() {
        return this.name().replaceAll("_", " ");
    }

    @Override
    public String toString() {
        return getKeyName();
    }
}
