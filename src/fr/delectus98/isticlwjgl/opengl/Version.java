package fr.delectus98.isticlwjgl.opengl;

import fr.delectus98.isticlwjgl.graphics.VertexArrayObject;
import org.lwjgl.opengl.GL;

public enum Version {
    GL11,
    GL12,
    GL13,
    GL14,
    GL15,
    GL20,
    GL21,
    GL30,
    GL31,
    GL32,
    GL33,
    GL40,
    GL41,
    GL42,
    GL43,
    GL44,
    GL45,
    GL46;

    /**
     * Before All: Must be called only after a context is created as GLFWWindow.
     * Checks if this version of openGL is supported on the system.
     * @return the availability of the this version.
     */
    public boolean isAvailable() {
        switch (this) {
            case GL11: return GL.getCapabilities().OpenGL11;
            case GL12: return GL.getCapabilities().OpenGL12;
            case GL13: return GL.getCapabilities().OpenGL13;
            case GL14: return GL.getCapabilities().OpenGL14;
            case GL15: return GL.getCapabilities().OpenGL15;
            case GL20: return GL.getCapabilities().OpenGL20;
            case GL21: return GL.getCapabilities().OpenGL21;
            case GL30: return GL.getCapabilities().OpenGL30;
            case GL31: return GL.getCapabilities().OpenGL31;
            case GL32: return GL.getCapabilities().OpenGL32;
            case GL33: return GL.getCapabilities().OpenGL33;
            case GL40: return GL.getCapabilities().OpenGL40;
            case GL41: return GL.getCapabilities().OpenGL41;
            case GL42: return GL.getCapabilities().OpenGL42;
            case GL43: return GL.getCapabilities().OpenGL43;
            case GL44: return GL.getCapabilities().OpenGL44;
            case GL45: return GL.getCapabilities().OpenGL45;
            case GL46: return GL.getCapabilities().OpenGL46;
            default: return false;
        }
    }

    public static boolean isAvailable(Object o) {
        if (VertexArrayObject.class.isInstance(o)) {
            return GL33.isAvailable();
        } else {
            return true;
        }
    }
}