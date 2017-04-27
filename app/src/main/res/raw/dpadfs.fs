precision mediump float;

varying vec2 texCoords;

uniform sampler2D sampler;
uniform vec4 opacity;

vec4 texture;

void main() {
    texture = texture2D(sampler, texCoords);
    if(texture.a == 0.0)
        gl_FragColor = vec4(0.0);
    else
        gl_FragColor = (opacity + texture) * vec4(0.5);

}