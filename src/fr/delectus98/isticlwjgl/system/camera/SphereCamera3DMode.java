package fr.delectus98.isticlwjgl.system.camera;

/*
import fr.delectus98.isticlwjgl.graphics.Vector3f;
import fr.delectus98.isticlwjgl.system.Camera3D;
import fr.delectus98.isticlwjgl.system.Keyboard;
import fr.delectus98.isticlwjgl.system.Time;

public class SphereCamera3DMode implements Camera3DMode {
    private float radius;
   // private Vector3f _position = new Vector3f();
    private Vector3f _angles = new Vector3f();

    public SphereCamera3DMode(float radius){
        this.radius = radius;
    }

    @Override
    public void apply(Camera3D camera) {}

    public void apply(Camera3D camera, Time elapsed) {
        final int pxPerSeconds = 200;
        final double radPerSeconds = Math.PI;

        double seconds = elapsed.asSeconds();

        // movement handling
        Vector3f pos = camera.getCenter();
        Vector3f motion = new Vector3f();
        if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            motion.add(camera.getOrientation().fact((float)( pxPerSeconds*seconds)));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            motion.add(camera.getOrientation().fact((float)(-pxPerSeconds*seconds)));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            motion.add(Vector3f.product(camera.getUpVector(), camera.getOrientation()).mul((float)(-pxPerSeconds*seconds)).unit());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            motion.add(Vector3f.product(camera.getUpVector(), camera.getOrientation()).mul((float)( pxPerSeconds*seconds)).unit());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            motion.add(camera.getUpVector().mul((float)(pxPerSeconds*seconds)));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            motion.add(camera.getUpVector().neg().mul((float)(pxPerSeconds*seconds)));
        }
        if (motion.compareTo(new Vector3f(0,0,0)) != 0) {
            motion.normalize().fact((float) (pxPerSeconds * seconds));
            pos.add(motion);
        }

        // angle handling
        if (Keyboard(Keyboard.KEY_UP)) {
            _angles.z += radPerSeconds*seconds;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            _angles.z -= radPerSeconds*seconds;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            _angles.y -= radPerSeconds*seconds;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            _angles.y += radPerSeconds*seconds;
        }
        if (_angles.z <= -3.14 / 2)
            _angles.z = -3.14f / 2;
        if (_angles.z >= 3.14 / 2)
            _angles.z = 3.14f / 2;

        Vector3f direction = radianToDirection(_angles);
        camera.look(direction);
        camera.setPosition(pos.sum(direction.mul(radius)));
    }
}
*/