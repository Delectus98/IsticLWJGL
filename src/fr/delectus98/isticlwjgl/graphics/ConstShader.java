package fr.delectus98.isticlwjgl.graphics;


public interface ConstShader {
    /**
     * Sets 'this' as the current shader program
     * @see Shader#isBound now returns true
     */
    void bind();

    /**
     * Checks if 'this' is current bound shader
     * @return true if 'this' is current bound shader else false
     */
    boolean isBound();

    /**
     * Gets the uniform specified value location inside the shader program?
     * @param name uniform value name.
     * @return uniform specified value location inside the shader program?
     */
    int getUniformLocation(String name);
}
