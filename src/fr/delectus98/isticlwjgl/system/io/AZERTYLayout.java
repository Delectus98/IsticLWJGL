package fr.delectus98.isticlwjgl.system.io;

import static org.lwjgl.glfw.GLFW.*;

/**http://claude.segeral.pagesperso-orange.fr/qwerazer/*/
public enum AZERTYLayout implements KeyboardLayout {
    //never change
    ESCAPE(GLFW_KEY_ESCAPE),
    CAPS_LOCK(GLFW_KEY_CAPS_LOCK),
    LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT),
    RIGHT_SHIFT(GLFW_KEY_RIGHT_SHIFT),
    TAB(GLFW_KEY_TAB),
    LEFT_CONTROL(GLFW_KEY_LEFT_CONTROL),
    RIGHT_CONTROL(GLFW_KEY_RIGHT_CONTROL),
    LEFT_ALT(GLFW_KEY_LEFT_ALT),
    RIGHT_ALT(GLFW_KEY_RIGHT_ALT),
    BACKSPACE(GLFW_KEY_BACKSPACE),
    SPACE(GLFW_KEY_SPACE),
    RETURN(GLFW_KEY_ENTER),
    MENU(GLFW_KEY_MENU),
    LEFT_SYSTEM_KEY(GLFW_KEY_LEFT_SUPER),
    RIGHT_SYSTEM_KEY(GLFW_KEY_RIGHT_SUPER),

    PRINT(GLFW_KEY_PRINT_SCREEN),
    PAUSE(GLFW_KEY_PAUSE),
    SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK),

    PAGE_UP(GLFW_KEY_PAGE_UP),
    PAGE_DOWN(GLFW_KEY_PAGE_DOWN),
    INSERT(GLFW_KEY_INSERT),
    DELETE(GLFW_KEY_DELETE),
    END(GLFW_KEY_END),
    HOME(GLFW_KEY_HOME), //<-

    LEFT_ARROW(GLFW_KEY_LEFT),
    RIGHT_ARROW(GLFW_KEY_RIGHT),
    UP_ARROW(GLFW_KEY_UP),
    DOWN_ARROW(GLFW_KEY_DOWN),

    F1(GLFW_KEY_F1),
    F2(GLFW_KEY_F2),
    F3(GLFW_KEY_F3),
    F4(GLFW_KEY_F4),
    F5(GLFW_KEY_F5),
    F6(GLFW_KEY_F6),
    F7(GLFW_KEY_F7),
    F8(GLFW_KEY_F8),
    F9(GLFW_KEY_F9),
    F10(GLFW_KEY_F10),
    F11(GLFW_KEY_F11),
    F12(GLFW_KEY_F12),
    F13(GLFW_KEY_F13),
    F14(GLFW_KEY_F14),
    F15(GLFW_KEY_F15),
    F16(GLFW_KEY_F16),
    F17(GLFW_KEY_F17),
    F18(GLFW_KEY_F18),
    F19(GLFW_KEY_F19),
    F20(GLFW_KEY_F20),
    F21(GLFW_KEY_F21),
    F22(GLFW_KEY_F22),
    F23(GLFW_KEY_F23),
    F24(GLFW_KEY_F24),
    F25(GLFW_KEY_F25),

    NUM_LOCK(GLFW_KEY_NUM_LOCK),
    PAD_1(GLFW_KEY_KP_1),
    PAD_2(GLFW_KEY_KP_2),
    PAD_3(GLFW_KEY_KP_3),
    PAD_4(GLFW_KEY_KP_4),
    PAD_5(GLFW_KEY_KP_5),
    PAD_6(GLFW_KEY_KP_6),
    PAD_7(GLFW_KEY_KP_7),
    PAD_8(GLFW_KEY_KP_8),
    PAD_9(GLFW_KEY_KP_9),
    PAD_0(GLFW_KEY_KP_0),
    PAD_SLASH(GLFW_KEY_KP_DIVIDE),
    PAD_ASTERISK(GLFW_KEY_KP_MULTIPLY),
    PAD_PLUS(GLFW_KEY_KP_ADD),
    PAD_MINUS(GLFW_KEY_KP_SUBTRACT),
    PAD_RETURN(GLFW_KEY_KP_ENTER),
    PAD_DELETE(GLFW_KEY_KP_DECIMAL),

    NUM_1(GLFW_KEY_1),
    NUM_2(GLFW_KEY_2),
    NUM_3(GLFW_KEY_3),
    NUM_4(GLFW_KEY_4),
    NUM_5(GLFW_KEY_5),
    NUM_6(GLFW_KEY_6),
    NUM_7(GLFW_KEY_7),
    NUM_8(GLFW_KEY_8),
    NUM_9(GLFW_KEY_9),
    NUM_0(GLFW_KEY_0),

    //sometimes change
    A(GLFW_KEY_Q),
    Z(GLFW_KEY_W),
    E(GLFW_KEY_E),
    R(GLFW_KEY_R),
    T(GLFW_KEY_T),
    Y(GLFW_KEY_Y),
    U(GLFW_KEY_U),
    I(GLFW_KEY_I),
    O(GLFW_KEY_O),
    P(GLFW_KEY_P),
    Q(GLFW_KEY_A),
    S(GLFW_KEY_S),
    D(GLFW_KEY_D),
    F(GLFW_KEY_F),
    G(GLFW_KEY_G),
    H(GLFW_KEY_H),
    J(GLFW_KEY_J),
    K(GLFW_KEY_K),
    L(GLFW_KEY_L),
    M(GLFW_KEY_SEMICOLON),
    W(GLFW_KEY_Z),
    X(GLFW_KEY_X),
    C(GLFW_KEY_C),
    V(GLFW_KEY_V),
    B(GLFW_KEY_B),
    N(GLFW_KEY_N)

    ;


    AZERTYLayout(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name().replaceAll("_", " ");
    }

    private int id;
    @Override
    public int getKeyID() {
        return id;
    }

    @Override
    public String getKeyName() {
        return toString();
    }

    public static void main(String[] args) {
        KeyboardLayout[] a = AZERTYLayout.values();
        System.out.println("AZERTY count:" + a.length);
        for (KeyboardLayout l : a) {
            System.out.println(l.toString());
        }
    }
}
