#version 330

layout (location = 0) in vec3 VertexPosition;
layout (location = 1) in vec4 VertexColor;
layout (location = 2) in vec2 VertexTexCoords;
layout (location = 3) in vec3 VertexNormal;
layout (location = 4) in vec3 VertexTangeant;

uniform mat4 mvp;
uniform vec3 lightPos;

out vec4 FragColor;
out vec2 FragTexCoord;
out vec3 FragLightPos;

out vec3 lightVec;
out vec3 eyeVec;


void main(void)
{
    FragTexCoord = VertexTexCoords;
    FragColor = VertexColor;
    FragLightPos = lightPos;

    gl_Position = mvp * vec4(VertexPosition, 1.0);


    mat4 normalMatrix = transpose(inverse(mvp));

    vec3 n = normalize((normalMatrix * vec4(VertexNormal, 1.0)).xyz);
    vec3 t = normalize((normalMatrix * vec4(VertexTangeant, 1.0)).xyz);
    vec3 b = cross(n, t);

    vec3 vVertex = (mvp * vec4(VertexPosition, 1.0)).xyz;
    vec3 tmpVec = lightPos - vVertex;

    lightVec.x = dot(tmpVec, t);
    lightVec.y = dot(tmpVec, b);
    lightVec.z = dot(tmpVec, n);

    tmpVec = -vVertex;
    eyeVec.x = dot(tmpVec, t);
    eyeVec.y = dot(tmpVec, b);
    eyeVec.z = dot(tmpVec, n);
}
