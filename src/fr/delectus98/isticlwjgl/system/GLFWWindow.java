package fr.delectus98.isticlwjgl.system;

import fr.delectus98.isticlwjgl.graphics.*;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;


import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetWindowFrameSize;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.system.MemoryUtil.memPointerBuffer;

/**
 * Interface to manage a Graphics Window.
 */
public class GLFWWindow extends RenderTarget {
    // window settings
    private int width = 1;
    private int height = 1;
    private String title;
    private WindowStyle style;
    private CallbackMode mode;
    private GLFWWindow share;
    private boolean running = false;
    private Clock internalClk = new Clock();
    private Time frameTimeLimit = Time.zero();

    // window event values
    private int textEntered;
    private boolean textEvent = false;
    private int keyPressed;
    private int keyReleased;
    private int keyRepeated;
    private boolean keyPressedEvent = false;
    private boolean keyReleasedEvent = false;
    private boolean keyRepeatEvent = false;
    private int buttonPressed;
    private int buttonReleased;
    private boolean buttonPressedEvent = false;
    private boolean buttonReleasedEvent = false;
    private boolean mouseCollision = false;
    private boolean mouseCollisionEvent = false;
    private boolean scrollEvent = false;
    private float scrollX;
    private float scrollY;
    private boolean dropEvent = false;
    private String drop[];
    private boolean joystickEvent = false;
    private int joystick;
    private boolean joystickConnection;
    private boolean resizeEvent = false;
    private int resizex;
    private int resizey;
    private boolean moveEvent = false;
    private int posx;
    private int posy;
    private boolean focusEvent = false;
    private boolean focus;

    // auto share
    private static GLFWWindow lastCreated = null;

    /**
     * Enable callbacks according to 'modes' parameter
     * @param modes chosen modes
     */
    private void initCallbacks(CallbackMode modes) {
        //Callbacks Inputs
        if (modes.enable(CallbackMode.KEY))
            glfwSetKeyCallback(this.glId, (window, key, scancode, action, mods) -> {
            switch (action) {
                case GLFW_PRESS: keyPressed = key; keyPressedEvent = true; break;
                case GLFW_RELEASE: keyReleased = key; keyReleasedEvent = true; break;
                case GLFW_REPEAT: keyRepeated = key; keyRepeatEvent = true; break;
                default: break;
            }
        });
        if (modes.enable(CallbackMode.CHAR))
            glfwSetCharCallback(this.glId, (window, unicode) -> {
            textEntered = unicode;
            textEvent = true;
        });
        if (modes.enable(CallbackMode.CURSORENTER))
            glfwSetCursorEnterCallback(this.glId, (window, state) -> {
            mouseCollisionEvent = true;
            mouseCollision = state;
        });
        if (modes.enable(CallbackMode.BUTTON))
            glfwSetMouseButtonCallback(this.glId, (window, button, action, mods) -> {
            switch (action) {
                case GLFW_PRESS: buttonPressed = button; buttonPressedEvent = true; break;
                case GLFW_RELEASE: buttonReleased = button; buttonReleasedEvent = true; break;
                default: break;
            }
        });
        if (modes.enable(CallbackMode.SCROLL))
            glfwSetScrollCallback(this.glId, (window, offsetx, offsety) -> {
            scrollEvent = true;
            scrollX = (float)offsetx;
            scrollY = (float)offsety;
        });
        if (modes.enable(CallbackMode.MOUSEDROP))
            glfwSetDropCallback(this.glId, (window,count, names) -> {
            dropEvent = true;
            drop = new String[count];

            PointerBuffer nameBuffer = memPointerBuffer(names, count);
            for (int i = 0; i < count; i++) {
                drop[i] = org.lwjgl.system.MemoryUtil.memASCII(nameBuffer.get(i));
            }
        });
        if (modes.enable(CallbackMode.JOYSTICK))
            glfwSetJoystickCallback((joystick, event) -> {
            joystickEvent = true;
            this.joystick = joystick;

            if (event == GLFW_CONNECTED) {
                // The joystick was connected
                joystickConnection = true;
            } else if (event == GLFW_DISCONNECTED) {
                // The joystick was disconnected
                joystickConnection = false;
            }
        });
        if (modes.enable(CallbackMode.FOCUS))
            glfwSetWindowFocusCallback(this.glId, (window, focus)->{
                focusEvent = true;
                this.focus = focus;
        });

        // Callback window
        if (modes.enable(CallbackMode.RESIZE))
            glfwSetFramebufferSizeCallback(this.glId, (window, w, h) -> {
            resizeEvent = true;
            resizex = w;
            resizey = h;
            this.width = resizex;
            this.height = resizey;

            this.updateDefaultView();
        });
        if (modes.enable(CallbackMode.MOVE))
            glfwSetWindowPosCallback(this.glId, (window, xpos, ypos) -> {
            moveEvent = true;
            posx = xpos;
            posy = ypos;
        });


    }

    /**
     * Enable styles according to 'styles' parameter
     * @param styles chosen styles
     */
    private void initHints(WindowStyle styles) {
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        //glfwWindowHint(GLFW_DOUBLEBUFFER, GL_FALSE);

        glfwWindowHint(GLFW_VISIBLE, ((styles.bits & WindowStyle.VISIBLE.bits) == WindowStyle.VISIBLE.bits) ? GLFW_TRUE : GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, ((styles.bits & WindowStyle.RESIZABLE.bits) == WindowStyle.RESIZABLE.bits) ? GLFW_TRUE : GLFW_FALSE); // the window will be resizable
        glfwWindowHint(GLFW_DECORATED, ((styles.bits & WindowStyle.TITLEBAR.bits) == WindowStyle.TITLEBAR.bits) ? GLFW_TRUE : GLFW_FALSE); // the window will have title bar
        glfwWindowHint(GLFW_FLOATING, ((styles.bits & WindowStyle.TOPMOST.bits) == WindowStyle.TOPMOST.bits) ? GLFW_TRUE : GLFW_FALSE);
        //glfwWindowHint(GLFW_MAXIMIZED, ((styles.bits & WindowStyle.MAXIMIZED.bits) == WindowStyle.MAXIMIZED.bits) ? GLFW_TRUE : GLFW_FALSE);
    }

    /**
     * Default initialization of window's MVP and Viewport and enabled gl states.
     * Init Camera (default Camera2D).
     * Init Viewport (default Window dimension).
     */
    protected void initGl() {
        defaultCamera = new Camera2D(new Vector2f(width, height));
        camera = defaultCamera;

        defaultViewport = new Viewport(new FloatRect(0,0, width, height));
        viewport = defaultViewport;

        camera.apply();
        viewport.apply(this);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Updates current context Default Camera (default Camera2D) and Default Viewport.
     * If defaultCamera is not camera, camera must be updated outside.
     * If defaultViewport is not viewport, viewport must be updated outside.
     */
    protected void updateDefaultView(){
        ((Camera2D)defaultCamera).setDimension(new Vector2f(width, height));
        ((Camera2D)defaultCamera).setCenter(new Vector2f(width / 2.f,height / 2.f));
        defaultViewport.setDimension(new Vector2f(width, height));
    }

    /**
     * Make 'this' as the current RenderTarget.
     */
    @Override
    protected final void bind(){
        glfwMakeContextCurrent(glId);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        camera.apply();
        viewport.apply(this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }


    /**
     * Create a window with a title bar, title.
     * By default it enable all callbacks methods.
     * @param videoMode window's dimension
     * @param title  window's title
     */
    public GLFWWindow(VideoMode videoMode, String title) {
        this(videoMode, title, WindowStyle.DEFAULT);
    }

    /**
     * Create a window with a specific style.
     * By default it enable all callbacks methods.
     * @param videoMode window's dimension
     * @param title window's title
     * @param style window's style
     */
    public GLFWWindow(VideoMode videoMode, String title, WindowStyle style) {
        this(videoMode, title, style, CallbackMode.DEFAULT);
    }

    /**
     * Create a window with a specific style and specific callback modes.
     * @param videoMode window's dimension
     * @param title window's title
     * @param style window's style
     * @param modes window's callback modes
     */
    public GLFWWindow(VideoMode videoMode, String title, WindowStyle style, CallbackMode modes) {
        this(videoMode, title, style, modes, lastCreated); // auto share
    }


    /**
     * Create a window with a specific style and specific callback modes.
     * @param videoMode window's dimension
     * @param title window's title
     * @param style window's style
     * @param modes window's callback modes
     * @param share target where 'this' will share Texture, Shaders, VBO ...
     */
    protected GLFWWindow(VideoMode videoMode, String title, WindowStyle style, CallbackMode modes, GLFWWindow share) {
        super();

        // initialized glfw if glfw is not initialized
        GLFWContext.createContext();

        this.width = Math.min(videoMode.width, VideoMode.getDesktopMode().width);
        //int OVERSIZE_OFFSET = (((WindowStyle.TITLEBAR.bits & style.bits) == WindowStyle.TITLEBAR.bits) ? (OVERSIZED_WINDOW_TITLE_BAR_DELTA) : (0));
        this.height = Math.min(videoMode.height, VideoMode.getDesktopMode().height/* - OVERSIZE_OFFSET*/);
        this.title = title;
        this.style = style.clone();
        this.mode = modes.clone();

        //////////////////////// Set up window //////////////////////////////
        // Configure our window
        this.initHints(style);

        // Create the window
        this.glId = glfwCreateWindow(this.width, this.height, title, ((WindowStyle.FULLSCREEN.bits & style.bits) == WindowStyle.FULLSCREEN.bits) ? glfwGetPrimaryMonitor() : 0, (share != null) ? share.getGlId() : 0);
        if (this.glId == 0)
            throw new RuntimeException("Failed to create the GLFW window");

        // Make the OpenGL context current
        glfwMakeContextCurrent(this.glId);

        // create a current thread context
        if (lastCreated == null) {
            GL.createCapabilities();
        }


        ////////////////////// Set up event handle //////////////////////////
        // Setup a all callbacks.
        this.initCallbacks(modes);

        /////////////////////  Set up params ////////////////////
        // Enable v-sync
        glfwSwapInterval(((style.bits & WindowStyle.VSYNC.bits) == WindowStyle.VSYNC.bits) ? 1 : 0);

        // Make the window visible
        glfwShowWindow(this.glId);

        // Right now the window is opened
        this.running = true;

        // Creates trivial texture (white 1*1 2D texture) for prime created window
        // Creates default shader for prime created window
        if (share == null) {
            Texture.createTrivial();
            Shader.createDefault();
        }

        // Creates default GL states and camera and viewport
        this.initGl();

        // for auto sharing
        lastCreated = this;

        //first clear
        clear();

        // Checks window oversize
        int[] left = new int[1];
        int[] top = new int[1];
        int[] right = new int[1];
        int[] bottom = new int[1];
        glfwGetWindowFrameSize(this.glId, left, top, right, bottom);
        if (top[0] + bottom[0] + this.height >= VideoMode.getDesktopMode().height) {//only height
            this.setDimension(new VideoMode(this.width, this.height - top[0] - bottom[0]));
        }

        // Get the resolution of the primary monitor
        VideoMode desktopMode = VideoMode.getDesktopMode();
        // Center our window
        posx = (desktopMode.width - this.width) / 2;
        posy = (desktopMode.height - this.height) / 2;
        if (posx < 0) posx = 0;
        if (posy < top[0]) posy = top[0];
        if (posx > desktopMode.width) posx = top[0];
        if (posy > desktopMode.height) posy = top[0];
        glfwSetWindowPos(this.getGlId(), posx, posy);

        // maximized or not
        if ((style.bits & WindowStyle.MAXIMIZED.bits) == WindowStyle.MAXIMIZED.bits)
            glfwMaximizeWindow(this.getGlId());
    }

    /**
     * Close and destroy the window.
     * @see GLFWWindow#isOpen return now false
     */
    public final void close() {
        if (!running) return;

        this.free();
    }

    /**
     * Checks if the window is running
     * @return true if the window is running
     * @see GLFWWindow#close
     */
    public final boolean isOpen() {
        return running;
    }

    ///EVENTS
    /**
     * Generates an event associated with its specific case values.
     * @return event caught by the window callbacks methods
     */
    private Event pollEvent() {
        if (glfwWindowShouldClose(this.getGlId())) {
            return new Event(Event.Type.CLOSE);
        }
        if (keyPressedEvent) {
            keyPressedEvent = false;
            return new Event(Event.Type.KEYPRESSED, new int[]{keyPressed});
        }
        if (keyReleasedEvent) {
            keyReleasedEvent = false;
            return new Event(Event.Type.KEYRELEASED, new int[]{keyReleased});
        }
        if (textEvent) {
            textEvent = false;
            return new Event(Event.Type.TEXTENTERED, new int[]{textEntered});
        }
        if (mouseCollisionEvent) {
            mouseCollisionEvent = false;
            return new Event((mouseCollision) ? Event.Type.MOUSEENTER : Event.Type.MOUSELEAVE);
        }
        if (buttonPressedEvent) {
            buttonPressedEvent = false;
            return new Event(Event.Type.BUTTONPRESSED, new int[]{buttonPressed});
        }
        if (buttonReleasedEvent) {
            buttonReleasedEvent = false;
            return new Event(Event.Type.BUTTONRELEASED, new int[]{buttonReleased});
        }
        if (keyRepeatEvent) {
            keyRepeatEvent = false;
            return new Event(Event.Type.KEYREPEAT, new int[]{keyRepeated});
        }
        if (scrollEvent) {
            scrollEvent = false;
            return new Event(Event.Type.MOUSESCROLL, new float[]{scrollX, scrollY});
        }
        if (dropEvent) {
            dropEvent = false;
            return new Event(Event.Type.MOUSEDROP, drop);
        }
        if (joystickEvent) {
            joystickEvent = false;
            return new Event((joystickConnection) ? Event.Type.JOYSTICK_CONNECTION : Event.Type.JOYSTICK_DISCONNECTION, new int[]{joystick});
        }
        if (resizeEvent) {
            resizeEvent = false;
            return new Event(Event.Type.RESIZE, new int[]{resizex, resizey});
        }
        if (moveEvent) {
            moveEvent = false;
            return new Event(Event.Type.MOVE, new int[]{posx, posy});
        }
        if (focusEvent) {
            focusEvent = false;
            return new Event(focus ? (Event.Type.FOCUS) : (Event.Type.UNFOCUS));
        }
        return null;
    }

    /**
     * Poll GLFW events and generate the last event caught associated with its specific case values.
     * @return event caught by the window callbacks methods
     * @see GLFWWindow#waitEvent()
     * @see GLFWWindow#waitEvent(Time)
     */
    public final Event pollEvents() {
        if (!running) return null;

        if (!this.isActive()) this.setActive();

        glfwPollEvents();

        return pollEvent();
    }

    /**
     * Waits GLFW events then generate the event caught associated with its specific case values.
     * @return event caught by the window callbacks methods
     * @see GLFWWindow#waitEvent(Time)
     * @see GLFWWindow#pollEvents()
     */
    public final Event waitEvent() {
        if (!running) return null;

        if (!this.isActive()) this.setActive();

        glfwWaitEvents();

        return pollEvent();
    }

    /**
     * Waits GLFW events until timeout then generate the event caught associated with its specific case values.
     * @param timeout time until the poll will stop
     * @return event caught by the window callbacks methods
     * @see GLFWWindow#waitEvent()
     * @see GLFWWindow#pollEvents()
     */
    public final Event waitEvent(Time timeout) {
        if (!running) return null;

        if (!this.isActive()) this.setActive();

        glfwWaitEventsTimeout(timeout.asSeconds());

        return pollEvents();
    }

    ///DISPLAY
    /**
     * Clear all the window screen with black color.
     */
    public final void clear(){
        clear(Color.Black);
    }

    /**
     * Clear all the window screen with a specific color.
     * @param color specified color
     */
    @Override
    public final void clear(ConstColor color){
        if (!this.isOpen()) return ;

        if (!this.isActive()) {
            this.setActive();
            Shader.rebind();
            this.applyView();
        }
        if (this.needViewUpdate()) {
            //Shader.rebind();
            this.applyView();
        }

        glClearColor(color.getR(),color.getG(),color.getB(),color.getA());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    /**
     * Render frame buffer to screen.
     */
    public final void display() {
        if (!running) return ;

        if (!this.isActive()) this.setActive();
        if (this.needViewUpdate()) this.applyView();

        //glFlush();
        glfwSwapBuffers(this.glId);


        if (frameTimeLimit.asSeconds() != 0.f) {
            Time fps = frameTimeLimit.clone();
            fps.reduce(internalClk.getElapsed());
            try {
                Thread.sleep((long)fps.asMilliseconds());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            internalClk.restart();
        }
    }

    /**
     * Draw the Drawable inside the frame buffer.
     * @param drawable something that can be drawn
     */
    public final void draw(Drawable drawable, ConstShader shader) {
        if (!running) return ;

        if (!this.isActive()) {
            this.setActive();
            if (!shader.isBound())
                shader.bind();
            else Shader.rebind();
            this.applyView();
        }
        if (!shader.isBound() || this.needViewUpdate()) {
            if (!shader.isBound())
                shader.bind();
            this.applyView();
        }

        drawable.draw();
    }

    @Override
    public final void free() {
        if (!running) return;

        running = false;

        glfwFreeCallbacks(this.glId);
        glfwDestroyWindow(this.glId);

        // destroy glfw if glfw is initialized and there is no GLFWContext anymore
        GLFWContext.deleteContext();

        this.glId = 0;
    }


    /**
     * Changes window's position into Desktop.
     * @param position desktop topleft position
     */
    public final void setPosition(Vector2i position){
        if (!running) return;
        glfwSetWindowPos(this.glId, position.x, position.y);
    }

    /**
     * Changes window's dimension
     * @param dimension specified dimension
     */
    public final void setDimension(VideoMode dimension){
        if (!running) return;
        this.width = dimension.width;
        this.height = dimension.height;
        glfwSetWindowSize(this.glId, dimension.width, dimension.height);

        this.updateDefaultView();
    }

    /**
     * Position of the window into Desktop.
     * @return window's position
     */
    public final Vector2i getPosition() {
        return new Vector2i(posx, posy);
    }

    /**
     * Dimension of the window;
     * @return window's dimension
     */
    public final Vector2i getDimension() {
        return new Vector2i(width, height);
    }

    /**
     * Sets approximate FPS limit.
     * Set limit to Zero removes frame rate limit effect.
     * @param frameRate frame per seconds
     */
    public final void setFrameRateLimit(int frameRate) {
        this.frameTimeLimit = Time.milliseconds((long)Math.max(0.f, 1000.f / frameRate));
    }

    public final void hide() {
        if (!running) return;
        glfwHideWindow(this.getGlId());
    }

    public final void show() {
        if (!running) return;
        glfwShowWindow(this.getGlId());
    }

    /**
     * Make a Screenshot of GLFWWindow and gives an image of it.
     * @return screenshot image
     */
    @Override
    public final Image capture() {
        if (!running) return null;
        final int bpp = 4;

        if (!this.isActive()) this.setActive();
        else if (this.needViewUpdate()) this.applyView();

        glReadBuffer(GL_FRONT);
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * bpp);

        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        byte[] array = new byte[width*height*bpp];
        buffer.get(array);

        //invert y
        for (int i=0;i < width ; ++i) {
            for (int j=0; j < height/2 ; ++j) {
                byte[] rgba = new byte[]{
                        array[i * bpp + j * width * bpp    ],
                        array[i * bpp + j * width * bpp + 1],
                        array[i * bpp + j * width * bpp + 2],
                        array[i * bpp + j * width * bpp + 3]
                };

                array[i * bpp + j * width * bpp    ] = array[i * bpp + (height - j - 1) * width * bpp    ];
                array[i * bpp + j * width * bpp + 1] = array[i * bpp + (height - j - 1) * width * bpp + 1];
                array[i * bpp + j * width * bpp + 2] = array[i * bpp + (height - j - 1) * width * bpp + 2];
                array[i * bpp + j * width * bpp + 3] = array[i * bpp + (height - j - 1) * width * bpp + 3];

                array[i * bpp + (height - j - 1) * width * bpp    ] = rgba[0];
                array[i * bpp + (height - j - 1) * width * bpp + 1] = rgba[1];
                array[i * bpp + (height - j - 1) * width * bpp + 2] = rgba[2];
                array[i * bpp + (height - j - 1) * width * bpp + 3] = rgba[3];
            }
        }

        return new Image(array, width, height);
    }



}


