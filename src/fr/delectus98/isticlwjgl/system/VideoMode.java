package fr.delectus98.isticlwjgl.system;


import java.awt.*;

/**
 * Represents a 2D dimension that can be applied to a window. Must be positives values.
 */
public class VideoMode {
    public final int width;
    public final int height;

    //1280×800, 1440×900, 1680×1050, 1920×1200, 2560×1600, 1024×576, 1152×648, 1280×720, 1366×768, 1600×900, 1920×1080, 2560×1440, 3840×2160, 7680×4320
    private static final VideoMode[] defaultModes = new VideoMode[]{
            new VideoMode(1280, 800),
            new VideoMode(1440, 900),
            new VideoMode(1680, 1050),
            new VideoMode(1920, 1200),
            new VideoMode(2560, 1600),
            new VideoMode(1024, 576),
            new VideoMode(1152, 648),
            new VideoMode(1280, 720),
            new VideoMode(1366, 768),
            new VideoMode(1600, 900),
            new VideoMode(1920, 1080),
            new VideoMode(2560, 1440),
            new VideoMode(3840, 2160),
            new VideoMode(7680, 4320)
    };

    private static VideoMode desktopMode;
    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        desktopMode = new VideoMode((int)width, (int)height);
    }

    /**
     * Create a dimension useful for windows
     * @param w width
     * @param h height
     * @throws RuntimeException thrown when dimensions used are negatives
     */
    public VideoMode(int w, int h) throws RuntimeException {
        if (w <= 0 || h <= 0) {
            throw new RuntimeException("Screen resolution must have positive values");
        }

        width = w;
        height = h;
    }

    /**
     * Returns current display screen dimension in pixel.
     * @return current display screen dimension in pixel.
     */
    public static VideoMode getDesktopMode() {
        return desktopMode;
    }

    /**
     * Returns list of default video modes among common display resolution
     * @return list of default video modes among common display resolution
     */
    public static VideoMode[] getDefaultModes() {
        return defaultModes;
    }
}
