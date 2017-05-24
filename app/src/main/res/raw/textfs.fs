precision mediump float;

varying vec2 texCoords;

uniform sampler2D texture;
uniform vec4 color;

void main() {
    gl_FragColor = color * texture2D(texture, texCoords);
}