#version 330

in vec4 FragColor;
in vec2 FragTexCoord;
in vec4 ShadowCoord;

layout (binding = 0) uniform sampler2D texture0;
layout (binding = 1) uniform sampler2D shadowMap;

out vec4 Color;

void main()
{
    float visibility = 1.0;
    float depth = texture(shadowMap, ShadowCoord.xy).r;
    if (depth < gl_FragCoord.z){
        visibility = 0.5;
    }

    vec4 textureColor = texture2D(texture0, FragTexCoord.xy);

    Color = textureColor * FragColor * visibility;
    Color.a = FragColor.a * textureColor.a;


    //Color = texture(shadowMap, FragTexCoord.xy);
    //Color = vec4(gl_FragCoord.z,gl_FragCoord.z,gl_FragCoord.z,1);
}