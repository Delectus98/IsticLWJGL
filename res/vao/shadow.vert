#version 330

layout (location = 0) in vec3 VertexPosition;
layout (location = 1) in vec4 VertexColor;
layout (location = 2) in vec2 VertexTexCoords;

uniform mat4 depthMVP;

void main()
{
    gl_Position = depthMVP * vec4(VertexPosition, 1.0);
}