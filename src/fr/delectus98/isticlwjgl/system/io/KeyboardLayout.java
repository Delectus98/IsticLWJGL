package fr.delectus98.isticlwjgl.system.io;


public interface KeyboardLayout {
    /**
     * @return key id according to the layout of the local keyboard translated to how GLFW handle keyboard layout
     */
    int getKeyID();

    /**
     * @return the key name
     */
    String getKeyName();

    /**
     * @return the key name
     */
    String toString();

}
