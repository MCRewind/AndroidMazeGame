package com.ghs.mazegame.game.objects;

import android.renderscript.Matrix4f;

import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.interfaces.GameObject;

import static com.ghs.mazegame.engine.display.Surface.touchX;
import static com.ghs.mazegame.engine.display.Surface.touchY;
import static com.ghs.mazegame.game.Main.defaultShader;

public class ToggleButton implements GameObject {

    public static int
        STATE_UNPRESSED = 0,
        STATE_PRESSED = 2;

    private float x = 0, y = 0, width = 0, height = 0;
    private int state, texture;
    private boolean reset;
    private Camera camera;
    private VAO vao;
    private Texture[] textures;
    private Shader shader;

    public ToggleButton(Camera camera, Texture unpressed, Texture pressed, float x, float y, float width, float height) {
        textures = new Texture[2];
        textures[0] = unpressed;
        textures[1] = pressed;
        shader = defaultShader;
        this.camera = camera;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        reset = true;
        float[] vertices = new float[] {
            0.0f,  0.0f,   0.1f, //TOP LEFT
            0.0f,  height, 0.1f, //BOTTOM LEFT
            width, height, 0.1f, //BOTTOM RIGHT
            width, 0.0f,   0.1f  //TOP RIGHT
        };
        int[] indices = new int[] {
            0, 1, 3,
            1, 2, 3
        };
        float[] texCoords = new float[] {
            0, 0,
            0, 1,
            1, 1,
            1, 0
        };
        vao = new VAO(vertices, indices, texCoords);
    }

    public void update() {
        float x = touchX;
        float y = touchY;
        if (contains(x, y) && reset) {
            reset = false;
            if (state > STATE_UNPRESSED + 1)
                state = STATE_UNPRESSED;
            else
                state = STATE_PRESSED;
        }
        else if(state % 2 == 0)
            ++state;
        if(x < 0)
            reset = true;
        texture = state / 2;
    }

    public void render() {
        textures[texture].bind();
        shader.enable();
        Matrix4f model = new Matrix4f();
        model.loadTranslate(x, y, 0);
        shader.setUniformMat4f("model", model);
        shader.setUniformMat4f("projection", camera.getUntransformedProjection());
        vao.render();
        shader.disable();
        textures[texture].unbind();
    }

    public int getState() {
        return state;
    }

    public boolean contains(float x, float y) {
        if ((x >= this.x && x < this.x + this.width) && (y >= this.y && y < this.y + this.height)) {
            return true;
        } else {
            return false;
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setTexture(int state, Texture texture) {
        textures[state] = texture;
    }

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setState(int state) {
        this.state = state;
    }
}
