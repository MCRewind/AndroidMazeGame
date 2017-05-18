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

    public Rectangle(Camera camera, float x, float y, float width, float height, float r, float g, float b, float a) {
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
                0.0f,  0.0f,   0.5f, //TOP LEFT
                0.0f,  height, 0.5f, //BOTTOM LEFT
                width, height, 0.5f, //BOTTOM RIGHT
                width, 0.0f,   0.5f  //TOP RIGHT
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
        Matrix4f proj = camera.getProjection();
        proj.translate(x, y, 0);
        shader.setUniformMat4f("projection", proj);
        shader.setUniform4f("color", r, g, b, a);
        shader.enable();
        vao.render();
        shader.disable();
    }

    public void setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
}