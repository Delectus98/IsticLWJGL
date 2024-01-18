#version 330

layout (location = 0) in vec3 VertexPosition;
layout (location = 1) in vec4 VertexColor;

uniform mat4 mvp;

out vec4 FragColor;

void main()
{
    FragColor = VertexColor;

    gl_Position = mvp * vec4(VertexPosition, 1.0);
}