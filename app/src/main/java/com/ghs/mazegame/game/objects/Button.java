package com.ghs.mazegame.game.objects;

import android.renderscript.Matrix4f;
import android.util.Log;

import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.interfaces.GameObject;

import static com.ghs.mazegame.engine.display.Surface.touchX;
import static com.ghs.mazegame.engine.display.Surface.touchY;
import static com.ghs.mazegame.game.Renderer.defaultShader;

public class Button implements GameObject {

    public static int
        STATE_RELEASED = 0,
        STATE_UNPRESSED = 1,
        STATE_PRESSED = 2,
        STATE_HELD = 3;

    private float x = 0, y = 0, width = 0, height = 0;
    private int state, texture;
    private Camera camera;
    private VAO vao;
    private Texture[] textures;
    private Shader shader;

    public Button(Camera camera, Texture unpressed, Texture pressed, float x, float y, float width, float height) {
        textures = new Texture[2];
        textures[0] = unpressed;
        textures[1] = pressed;
        shader = defaultShader;
        this.camera = camera;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        float tx = touchX;
        float ty = touchY;
        if ((tx >= x && tx < x + width) && (ty >= y && ty < y + height)) {
            if(state == STATE_PRESSED)
                state = STATE_HELD;
            else if(state < STATE_PRESSED)
                state = STATE_PRESSED;
        } else {
            if(state == STATE_RELEASED)
                state = STATE_UNPRESSED;
            else if(state > STATE_UNPRESSED)
                state = STATE_RELEASED;

        }
        if(state >= STATE_PRESSED)
            texture = 1;
        else
            texture = 0;
    }

    public void render() {
        Matrix4f proj = camera.getUnatransformedProjection();
        proj.translate(x, y, 0);
        shader.setUniformMat4f("projection", proj);
        textures[texture].bind();
        shader.enable();
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
}
