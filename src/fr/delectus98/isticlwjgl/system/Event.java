package fr.delectus98.isticlwjgl.system;

/**
 * Interface used (by GLFWWindow) to describe a callback event and used to let the user known the description of the callback event outside callbacks.
 * Each parameter of Event corresponds to callbacks parameters. Each event must used it own values corresponding to its event type.
 * For fr.delectus98.isticlwjgl.example, when the event's type is 'TEXTENTERED', user must use the corresponding attribute 'textEntered'.
 * @see GLFWWindow#pollEvents()
 * @see GLFWWindow#waitEvent()
 */
public final class Event {
    public final Type type;

    // window event values
    // TODO instead of using multiple variable we must use C Union-like type to decrease memory use
    public int textEntered;
    public int keyPressed;
    public int keyReleased;
    public int keyRepeated;
    public int buttonPressed;
    public int buttonReleased;
    public float scrollX;
    public float scrollY;
    public String drop[];
    public int joystick;
    public int resizeX;
    public int resizeY;
    public int posX;
    public int posY;

    // TODO instead of using multiple constructors, GLFWWindow may use attributes assignment

    // Constructors are only called by GLFWWindow and must not be called otherwise

    public Event(Type type) {
        this.type = type;

        switch (type) {
            case MOUSEENTER: break;
            case MOUSELEAVE: break;
        }
    }

    public Event(Type type, boolean[] values) {
        this(type);
    }

    public Event(Type type, int[] values){
        this(type);

        switch (type) {
            case TEXTENTERED: textEntered = values[0]; break;

            case BUTTONPRESSED: buttonPressed = values[0]; break;
            case BUTTONRELEASED: buttonReleased = values[0]; break;

            case KEYREPEAT: keyRepeated = values[0]; break;
            case KEYPRESSED: keyPressed = values[0]; break;
            case KEYRELEASED: keyReleased = values[0]; break;

            case RESIZE: resizeX = values[0]; resizeY = values[1]; break;
            case MOVE: posX = values[0]; posY = values[1]; break;

            case JOYSTICK_CONNECTION: joystick = values[0]; break;
            case JOYSTICK_DISCONNECTION: joystick = values[0]; break;
        }
    }

    public Event(Type type, float[] values){
        this(type);

        switch (type) {
            case MOUSESCROLL: scrollX = values[0]; scrollY = values[1]; break;
        }
    }

    public Event(Type type, String[] values) {
        this(type);

        switch (type) {
            case MOUSEDROP: drop = values; break;
        }
    }

    public enum Type {
        CLOSE,          // Window close button has been released             | no attributes
        RESIZE,         // Window has been resized                           | resizeX, resizeY
        FOCUS,          // Window has been selected as current window        | no attributes
        UNFOCUS,        // Window is not current window anymore              | no attributes
        MOVE,           // Window has been moved                             | posX, posY

        KEYREPEAT,      // Keyboard key is pressed when Window was current window                               | keyRepeated
        KEYPRESSED,     // Keyboard key has been pressed for the first time when Window was current window      | keyPressed
        KEYRELEASED,    // Keyboard key has been released when Window was current window                        | keyReleased
        TEXTENTERED,    // Text has been entered when Window was current window                                 | textEntered

        BUTTONPRESSED,  // Mouse button has been pressed for the first time when Window was current window      | buttonPressed
        BUTTONRELEASED, // Mouse button has been released when Window was current window                        | buttonReleased

        MOUSEENTER,     // Mouse cursor just enter Window area                          | no attributes
        MOUSELEAVE,     // Mouse cursor just leave Window area                          | no attributes

        MOUSESCROLL,    // Mouse has been scrolling when Window was current window      | scrollX, scrollY

        MOUSEDROP,      // User dropped items into window deposit                       | drop

        JOYSTICK_CONNECTION,          // Joystick event                       | no attributes
        JOYSTICK_DISCONNECTION        // Joystick event                       | no attributes
    }
}
