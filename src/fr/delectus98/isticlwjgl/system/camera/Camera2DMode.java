package fr.delectus98.isticlwjgl.system.camera;

import fr.delectus98.isticlwjgl.system.Camera2D;
import fr.delectus98.isticlwjgl.system.Time;

public interface Camera2DMode {
    void apply(Camera2D camera3D, Time elapsed);
}
