#version 330

layout (location = 0) in vec3 VertexPosition;
layout (location = 1) in vec4 VertexColor;
layout (location = 2) in vec2 VertexTexCoords;
layout (location = 3) in vec3 VertexNormal;

uniform mat4 mvp;
uniform mat4 depthMVP; // depthBiasMVP = bias * depthMVP

out vec4 FragColor;
out vec2 FragTexCoord;
out vec4 ShadowCoord;

void main()
{
    FragColor = VertexColor;
    FragTexCoord = VertexTexCoords;
    gl_Position =  mvp * vec4(VertexPosition,1);

    mat4 depthBiasMVP =
    mat4
    (
        0.5, 0.0, 0.0, 0.0,
        0.0, 0.5, 0.0, 0.0,
        0.0, 0.0, 0.5, 0.0,
        0.0, 0.0, 0.0, 0.5
    ) * depthMVP;

    // Same, but with the light's view matrix
    ShadowCoord = depthBiasMVP * gl_Position;//vec4(VertexPosition,1);
}