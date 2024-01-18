#version 330

in vec4 FragColor;

out vec4 Color;

void main()
{
    Color = FragColor;
    //Color = vec4(1,1,1,1);
}