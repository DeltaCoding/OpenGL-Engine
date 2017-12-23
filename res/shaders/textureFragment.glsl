#version 330
in vec3 vertexColor;
in vec2 texCoords;

out vec4 fragColor;

uniform sampler2D tex1;
uniform sampler2D tex2;

void main() {
	fragColor = mix(texture(tex1, texCoords), texture(tex2, texCoords), texture(tex2, texCoords).a * 0.5);
}