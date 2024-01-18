#version 330

layout (location = 0) in vec3 VertexPosition;
layout (location = 1) in vec4 VertexColor;
layout (location = 2) in vec2 VertexTexCoords;
layout (location = 3) in vec3 VertexNormal;
layout (location = 4) in vec3 VertexTangeant;

uniform mat4 mvp;
uniform vec3 lightPos;
uniform vec3 eyePos;

out vec4 FragColor;
out vec2 FragTexCoord;
out vec3 Normal;
out vec3 Tangeant;
out vec3 BiTangeant;


void main()
{
    Normal = VertexNormal;

    FragTexCoord = VertexTexCoords;
    FragColor = VertexColor;

    gl_Position = mvp * vec4(VertexPosition, 1.0);
}