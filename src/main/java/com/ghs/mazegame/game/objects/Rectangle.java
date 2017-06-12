package com.ghs.mazegame.game.objects;

import android.renderscript.Matrix4f;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;

public class Rectangle {

    private float x, y, width, height, r, g, b, a;
    private Camera camera;
    private Shader shader;
    private VAO vao;

    public Rectangle(Camera camera, float x, float y, float depth, float width, float height, float r, float g, float b, float a) {
        this.camera = camera;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.shader = new Shader(R.raw.colorvs, R.raw.colorfs);
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

    public void render() {
        shader.enable();
        Matrix4f model = new Matrix4f();
        model.loadTranslate(x, y, 0);
        shader.setUniformMat4f("model", model);
        shader.setUniformMat4f("projection", camera.getProjection());
        shader.setUniform4f("color", r, g, b, a);
        vao.render();
        shader.disable();
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

    public void setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
    }
}
