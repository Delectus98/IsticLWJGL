#version 330

layout (location = 0) out vec4 DepthFragment;

void main()
{
    //DepthFragment = gl_FragCoord.z;
    DepthFragment = vec4(gl_FragCoord.z, gl_FragCoord.z, gl_FragCoord.z, 1);
}