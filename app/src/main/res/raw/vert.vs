attribute vec4 vertices;

uniform mat4 projection;

void main() {
    gl_Position = projection * vertices;
}