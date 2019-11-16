#version 330

in vec4 FragColor;
in vec2 FragTexCoord;
in vec3 FragLightPos;

in vec3 lightVec;
in vec3 eyeVec;

layout (binding = 0) uniform sampler2D colorMap;
layout (binding = 1) uniform sampler2D normalMap;
uniform float invRadius;
uniform float lightAmbiant;
uniform float lightDiffuse;
uniform float lightSpecular;
uniform vec4 materialAmbiant;
uniform vec4 materialDiffuse;
uniform vec4 materialSpecular;
uniform float materialShininess;


layout(location = 0) out vec4 Color;

void main (void)
{
    float distSqr = dot(lightVec, lightVec);
    float att = clamp(1.0 - invRadius * sqrt(distSqr), 0.0, 1.0);
    vec3 lVec = lightVec * inversesqrt(distSqr);

    vec3 vVec = normalize(eyeVec);

    vec4 base = texture2D(colorMap, FragTexCoord);

    vec3 bump = normalize(texture2D(normalMap, FragTexCoord).xyz * 2.0 - 1.0);

    vec4 vAmbient = lightAmbiant * materialAmbiant;

    //float diffuse = max( dot(lVec, bump), 0.0 );
    float diffuse = max( dot(lVec, bump), -dot(lVec, bump) ); //better results

    vec4 vDiffuse = lightDiffuse * materialDiffuse * diffuse;

    float specular = pow(clamp(dot(reflect(-lVec, bump), vVec), 0.0, 1.0), materialShininess);

    vec4 vSpecular = lightSpecular * materialSpecular * specular;

    Color = (vAmbient*base + vDiffuse*base + vSpecular) * att;
    Color.a = FragColor.a;
}