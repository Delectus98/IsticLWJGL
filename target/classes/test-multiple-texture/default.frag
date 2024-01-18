
uniform sampler2D texture;
uniform sampler2D texture1;

void main(void)
{
    gl_FragColor = gl_Color * texture2D(texture, gl_TexCoord[0].st) * (vec4(1,1,1,2) - texture2D(texture1, gl_TexCoord[0].st));
}


