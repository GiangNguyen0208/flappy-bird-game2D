#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;

out DATA {
    vec2 tc;
    vec3 position;
} vs_out;

uniform mat4 pr_matrix;
uniform mat4 vw_matrix;

void main() {
    vs_out.tc = texCoord;
    vs_out.position = position;
    gl_Position = pr_matrix * vw_matrix * vec4(position, 1.0);
}
