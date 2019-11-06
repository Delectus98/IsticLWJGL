package fr.delectus98.isticlwjgl.opengl;


import fr.delectus98.isticlwjgl.graphics.*;
import fr.delectus98.isticlwjgl.system.Camera;
import fr.delectus98.isticlwjgl.system.Camera2D;
import fr.delectus98.isticlwjgl.system.Camera3D;
import fr.delectus98.isticlwjgl.system.CameraOrtho;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class CompatibilityChecking {
    /*protected CompatibilityChecking(Version v){
        register(this.getClass(), v);
    }

    public final boolean isAvailable() {
        return v.isAvailable();
    }*/
    private static Map<Object, Version> requirements;
    static {
        requirements = new HashMap<>();
        register(Shader.class, Version.GL11);
        register(Texture.class, Version.GL11);
        register(Shape.class, Version.GL11);
        register(RectangleShape.class, Version.GL11);
        register(Sprite.class, Version.GL11);
        register(Texture.class, Version.GL11);
        register(VertexArrayObject.class, Version.GL33);
        register(VertexDisplayList.class, Version.GL11);

        register(Camera.class, Version.GL11);
        register(Camera3D.class, Version.GL11);
        register(Camera2D.class, Version.GL11);
        register(CameraOrtho.class, Version.GL11);

        try {
            register(Camera2D.class.getDeclaredMethod("apply"), Version.GL15);
        } catch (NoSuchMethodException e) {
            System.exit(-1);
        }
    }

    private static void register(Method m, Version v) {
        requirements.put(m, v);
    }
    private static void register(Class c, Version v) {
        requirements.put(c, v);
    }
    public static boolean isAvailable(Class c) {
        return requirements.getOrDefault(c, Version.GL11).isAvailable();
    }
    public static boolean isAvailable(Method m) {
        return requirements.getOrDefault(m, Version.GL11).isAvailable();
    }

}
