package fr.delectus98.isticlwjgl.system.camera;


import fr.delectus98.isticlwjgl.graphics.Vector3f;
import fr.delectus98.isticlwjgl.system.Camera3D;
import fr.delectus98.isticlwjgl.system.Keyboard;
import fr.delectus98.isticlwjgl.system.Time;
import fr.delectus98.isticlwjgl.system.io.AZERTYLayout;

import static fr.delectus98.isticlwjgl.system.camera.FPSCamera3DMode.radianToDirection;

@Deprecated
public class SphereCamera3DMode implements Camera3DMode {
    private float radius;
   // private Vector3f _position = new Vector3f();
    private Vector3f _angles = new Vector3f();
    private Keyboard keyboard;

    public SphereCamera3DMode(float radius, Keyboard keyboard){
        this.radius = radius;
        this.keyboard = keyboard;
    }

    @Override
    public void apply(Camera3D camera, Time elapsed) {
        final int pxPerSeconds = 200;
        final double radPerSeconds = Math.PI;

        double seconds = elapsed.asSeconds();

        Vector3f direction = radianToDirection(_angles);


        // movement handling
        Vector3f pos = camera.getCenter().sum(direction.mul(radius).neg());
        Vector3f motion = new Vector3f(0,0,0);
        if (keyboard.isKeyPressed(AZERTYLayout.Z.getKeyID())) {
            motion.add(camera.getOrientation().fact((float)( pxPerSeconds*seconds)));
        }
        if (keyboard.isKeyPressed(AZERTYLayout.S.getKeyID())) {
            motion.add(camera.getOrientation().fact((float)(-pxPerSeconds*seconds)));
        }
        if (keyboard.isKeyPressed(AZERTYLayout.Q.getKeyID())) {
            motion.add(Vector3f.product(camera.getUpVector(), camera.getOrientation()).mul((float)(-pxPerSeconds*seconds)).unit());
        }
        if (keyboard.isKeyPressed(AZERTYLayout.D.getKeyID())) {
            motion.add(Vector3f.product(camera.getUpVector(), camera.getOrientation()).mul((float)( pxPerSeconds*seconds)).unit());
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
        }
        System.out.println(
                motion + "" + pos
        );

        // angle handling
        if (keyboard.isKeyPressed(AZERTYLayout.UP_ARROW.getKeyID())) {
            _angles.z += radPerSeconds*seconds;
        }
        if (keyboard.isKeyPressed(AZERTYLayout.DOWN_ARROW.getKeyID())) {
            _angles.z -= radPerSeconds*seconds;
        }
        if (keyboard.isKeyPressed(AZERTYLayout.LEFT_ARROW.getKeyID())) {
            _angles.y -= radPerSeconds*seconds;
        }
        if (keyboard.isKeyPressed(AZERTYLayout.RIGHT_ARROW.getKeyID())) {
            _angles.y += radPerSeconds*seconds;
        }
        if (_angles.z <= -3.14 / 2)
            _angles.z = -3.14f / 2;
        if (_angles.z >= 3.14 / 2)
            _angles.z = 3.14f / 2;

        camera.look(direction);
        camera.setPosition(pos.sum(direction.mul(radius)));
    }
}
