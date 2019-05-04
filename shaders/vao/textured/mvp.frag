/* Very simple fragment shader. It basically passes the
 * (interpolated) vertex color on to the individual pixels.
 */ 
#version 400

in vec4 Color;
in vec2 TexCoord;

uniform sampler2D texture;

out vec4 FragColor;

void main()
{
    vec4 pixel = texture2D(texture, TexCoord);
	// assign vertex color to pixel color
    FragColor = Color * pixel;
}