#version 330
layout (location = 0) in vec3 inPos;
layout (location = 1) in vec3 inColor;
layout (location = 2) in vec2 inTexCoords;

out vec3 vertexColor;
out vec2 texCoords;

void main() {
	gl_Position = vec4(inPos, 1.0);
	vertexColor = inColor;
	texCoords = inTexCoords;
}