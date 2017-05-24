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
import static com.ghs.mazegame.game.Main.defaultShader;

public class Button implements GameObject {

    public static int
        STATE_RELEASED = 0,
        STATE_UNPRESSED = 1,
        STATE_PRESSED = 2,
        STATE_HELD = 3;

    private float x = 0, y = 0, xOffset = 0, yOffset = 0, width = 0, height = 0;
    private boolean independent;
    private int state, texture;
    private Camera camera;
    private VAO vao;
    private Texture[] textures;
    private Shader shader;

    public Button(Camera camera, Texture unpressed, Texture pressed, float x, float y, float depth, float width, float height, boolean independent) {
        textures = new Texture[2];
        textures[0] = unpressed;
        textures[1] = pressed;
        shader = defaultShader;
        state = STATE_UNPRESSED;
        this.camera = camera;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.independent = independent;
        float[] vertices = new float[] {
            0.0f,  0.0f,   depth, //TOP LEFT
            0.0f,  height, depth, //BOTTOM LEFT
            width, height, depth, //BOTTOM RIGHT
            width, 0.0f,   depth  //TOP RIGHT
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
        if(!independent) {
            tx += camera.getX();
            ty += camera.getY();
        }
        if ((tx >= x + xOffset && tx < x + xOffset + width) && (ty >= y + yOffset && ty < y + yOffset + height)) {
            if(state == STATE_PRESSED)
                state = STATE_HELD;
            else if(state < STATE_PRESSED)
                state = STATE_PRESSED;
        }
        else if(touchX == -1 && touchY == -1 && state > STATE_UNPRESSED)
            state = STATE_RELEASED;
        else
            state = STATE_UNPRESSED;
        if(state >= STATE_PRESSED)
            texture = 1;
        else
            texture = 0;
    }

    public void render() {
        textures[texture].bind();
        shader.enable();
        Matrix4f model = new Matrix4f();
        model.loadTranslate(x + xOffset, y + yOffset, 0);
        shader.setUniformMat4f("model", model);
        shader.setUniformMat4f("projection", independent ? camera.getUntransformedProjection() : camera.getProjection());
        vao.render();
        shader.disable();
        textures[texture].unbind();
    }

    public int getState() {
        return state;
    }

    public boolean contains(float x, float y) {
        if ((x >= this.x + xOffset && x < this.x + xOffset + this.width) && (y >= this.y + yOffset && y < this.y + yOffset + this.height)) {
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

    public void setOffset(float x, float y) {
        this.xOffset = x;
        this.yOffset = y;
    }
}
