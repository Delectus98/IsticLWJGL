#version 330

in vec4 FragColor;
in vec2 FragTexCoord;

uniform sampler2D texture0;

out vec4 Color;

void main()
{
    Color = texture2D(texture0, FragTexCoord) * FragColor;
}