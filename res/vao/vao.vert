#version 330

layout (location = 0) in vec3 VertexPosition;
layout (location = 1) in vec4 VertexColor;
layout (location = 2) in vec2 VertexTexCoords;

uniform mat4 mvp;

out vec4 FragColor;
out vec2 FragTexCoord;

void main()
{
    FragTexCoord = VertexTexCoords;
    FragColor = VertexColor;

    gl_Position = mvp * vec4(VertexPosition, 1.0);
}