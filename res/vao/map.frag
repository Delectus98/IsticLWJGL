#version 330

in vec4 FragColor;
in vec2 FragTexCoord;
in vec3 Normal;
in vec3 Tangeant;
in vec3 BiTangeant;


//http://fabiensanglard.net/bumpMapping/index.php

layout (binding = 0) uniform sampler2D texture0;
layout (binding = 1) uniform sampler2D texture1;

out vec4 Color;

void main()
{
    //vec3 normalColor = (texture(texture1, FragTexCoord) * 2.0 - 1.0).rgb;
    //Color.rgb = normalColor;
    //Color.rgb = vec3(1,1,1);
    Color.rgb = normalize(Normal);
    Color.a = 1.0;
}