attribute vec3 vertices;
attribute vec2 itexCoords;

varying vec2 texCoords;

uniform mat4 model;
uniform mat4 projection;

void main() {
    texCoords = itexCoords;
    gl_Position = projection * model * vec4(vertices, 1);
}