#version 330 core
layout (location = 0) in vec3 pos;
layout (location = 1) in vec3 color;

out vec3 vertexColor;

uniform float horizontalOffset;

void main() {
	gl_Position = vec4(pos.x + horizontalOffset, pos.y, pos.z, 1.0);
	vertexColor = color;
}