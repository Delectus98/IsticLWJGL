package fr.delectus98.isticlwjgl.system.camera;

import fr.delectus98.isticlwjgl.system.Camera3D;
import fr.delectus98.isticlwjgl.system.Time;

public interface Camera3DMode {
    void apply(Camera3D camera3D, Time elapsed);
}
