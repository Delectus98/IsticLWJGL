package fr.delectus98.isticlwjgl.system.camera;

import fr.delectus98.isticlwjgl.system.io.AZERTYLayout;
import fr.delectus98.isticlwjgl.system.Keyboard;
import fr.delectus98.isticlwjgl.graphics.Vector3f;
import fr.delectus98.isticlwjgl.system.Camera3D;

import fr.delectus98.isticlwjgl.system.Time;

@Deprecated //because it's not portable
public class FPSCamera3DMode implements Camera3DMode {
    private Vector3f _angles = new Vector3f(0,0,0);
    private Keyboard keyboard;

    public FPSCamera3DMode(Keyboard keyboard){
        this.keyboard = keyboard;
    }

    public FPSCamera3DMode(Keyboard keyboard, Vector3f angles) {
        this.keyboard = keyboard;
        this._angles = new Vector3f(angles);
    }

    @Override
    public void apply(Camera3D camera, Time elapsed) {
        final int pxPerSeconds = 50;
        final double radPerSeconds = Math.PI/10;

        double seconds = elapsed.asSeconds();

        // movement handling
        Vector3f pos = camera.getCenter();
        Vector3f motion = new Vector3f();
        if (keyboard.isKeyPressed(AZERTYLayout.Z.getKeyID())) {
            motion.add(camera.getOrientation().fact((float)( pxPerSeconds*seconds)));
        }
        if (keyboard.isKeyPressed(AZERTYLayout.S.getKeyID())) {
            motion.add(camera.getOrientation().fact((float)(-pxPerSeconds*seconds)));
        }
        if (keyboard.isKeyPressed(AZERTYLayout.Q.getKeyID())) {
            motion.add(Vector3f.product(camera.getUpVector(), camera.getOrientation()).mul((float)(-pxPerSeconds*seconds)));
        }
        if (keyboard.isKeyPressed(AZERTYLayout.D.getKeyID())) {
            motion.add(Vector3f.product(camera.getUpVector(), camera.getOrientation()).mul((float)( pxPerSeconds*seconds)));
        }
        if (keyboard.isKeyPressed(AZERTYLayout.SPACE.getKeyID())) {
            motion.add(camera.getUpVector().mul((float)(pxPerSeconds*seconds)));
        }
        if (keyboard.isKeyPressed(AZERTYLayout.LEFT_SHIFT.getKeyID())) {
            motion.add(camera.getUpVector().neg().mul((float)(pxPerSeconds*seconds)));
        }
        if (motion.compareTo(new Vector3f(0,0,0)) != 0) {
            motion.normalize().fact((float) (pxPerSeconds * seconds));
            pos.add(motion);
            camera.setPosition(pos);
        }

        // angle handling
        if (keyboard.isKeyPressed(AZERTYLayout.UP_ARROW.getKeyID())) {
            _angles.z -= radPerSeconds*seconds;
        }
        if (keyboard.isKeyPressed(AZERTYLayout.DOWN_ARROW.getKeyID())) {
            _angles.z += radPerSeconds*seconds;
        }
        if (keyboard.isKeyPressed(AZERTYLayout.LEFT_ARROW.getKeyID())) {
            _angles.y += radPerSeconds*seconds;
        }
        if (keyboard.isKeyPressed(AZERTYLayout.RIGHT_ARROW.getKeyID())) {
            _angles.y -= radPerSeconds*seconds;
        }
        if (_angles.z < -3.14 / 2)
            _angles.z = -3.14f / 2;
        if (_angles.z > 3.14 / 2)
            _angles.z = 3.14f / 2;

        camera.look(radianToDirection(_angles));
    }

    public static Vector3f radianToDirection(Vector3f v) {
        return new Vector3f((float)(Math.cos(v.z)*Math.cos(v.y)), (float)(Math.sin(v.z)), (float)(Math.cos(v.z)*Math.sin(v.y)));
    }

}
