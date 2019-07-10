package fr.delectus98.isticlwjgl.system;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * WindowStyle provides interface to allow or not some GLFWWindow styles.
 * Issue 1: No title bar and the window become not resizable.
 */
public class WindowStyle {
    /// Default list of styles
    /** VISIBLE : The displayed window will appear directly after creation */
    public static final WindowStyle VISIBLE = new WindowStyle(1 << 0);
    /** TOPMOST : The displayed window will always be displayed above second plan windows*/
    public static final WindowStyle TOPMOST = new WindowStyle(1 << 1);
    /** TITLEBAR : The displayed window will appear with a title bar, close button, reduce button, and maximize button */
    public static final WindowStyle TITLEBAR = new WindowStyle(1 << 2);
    /** RESIZABLE : The displayed window will be resizable, if not the maximize button from title bar will be removed */
    public static final WindowStyle RESIZABLE = new WindowStyle(1 << 3);
    /** MAXIMIZED : The displayed window will be maximized when created */
    public static final WindowStyle MAXIMIZED = new WindowStyle(1 << 4);
    /** MAXIMIZED : The displayed window will enable vsync */
    public static final WindowStyle VSYNC = new WindowStyle(1 << 6);

    /** DEFAULT : VISIBLE + RESIZABLE + TITLEBAR */
    public static final WindowStyle DEFAULT = new WindowStyle(VISIBLE.bits | RESIZABLE.bits | TITLEBAR.bits);
    /** FULLSCREEN : the displayed window will be in fullscreen mode */
    public static final WindowStyle FULLSCREEN = new WindowStyle(1 << 5 | VISIBLE.bits);
    /** NONE : the displayed window will appear as pop-up window */
    public static final WindowStyle NONE = new WindowStyle(VISIBLE.bits);


    public final int bits;

    /**
     * Constructor is private to provide only existing styles that are functional
     * @param styles chosen styles
     */
    @Immutable
    private WindowStyle(int styles){
        bits = styles;
    }

    /**
     * Adds styles to current
     * @param styles added styles
     * @return new style
     */
    public WindowStyle add(WindowStyle styles) {
        return new WindowStyle(styles.bits | this.bits);
    }

    /**
     * Removes styles to current
     * @param styles removed styles
     * @return new style
     */
    public WindowStyle remove(WindowStyle styles) {
        return new WindowStyle(this.bits ^ (this.bits & styles.bits));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof WindowStyle) {
            WindowStyle style = (WindowStyle)o;
            return style.bits == bits;
        }

        return false;
    }

    @Override
    public WindowStyle clone(){
        return new WindowStyle(this.bits);
    }
}
