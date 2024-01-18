#version 130

uniform sampler2D texture;

void main(void)
{
    vec4 alive = texture2D(texture, gl_TexCoord[0].st);

    vec2 size = textureSize(texture, 0);

    float population =
    texture2D(texture, gl_TexCoord[0].st + vec2(1 / size.x, 0)).r +
    texture2D(texture, gl_TexCoord[0].st + vec2(-1 / size.x, 0)).r +
    texture2D(texture, gl_TexCoord[0].st + vec2(0, 1 / size.x)).r +
    texture2D(texture, gl_TexCoord[0].st + vec2(0, -1 / size.x)).r +
    texture2D(texture, gl_TexCoord[0].st + vec2(1 / size.x, 1 / size.x)).r +
    texture2D(texture, gl_TexCoord[0].st + vec2(1 / size.x, -1 / size.x)).r +
    texture2D(texture, gl_TexCoord[0].st - vec2(-1 / size.x, 1 / size.x)).r +
    texture2D(texture, gl_TexCoord[0].st - vec2(-1 / size.x, -1 / size.x)).r;

    if (alive.r == 1) {
        if (population == 2) {
            alive = vec4(1,1,1,1);
        } else if (population < 2 && population >= 3) {
            alive = vec4(0,0,0,1);
        }
    } else {
        if (population == 3) {
            alive = vec4(1,1,1,1);
        } else {
            alive = vec4(0,0,0,1);
        }
    }

    gl_FragColor = gl_Color * alive;
}