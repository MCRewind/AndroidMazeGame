package com.ghs.mazegame.game.objects;

import android.graphics.Bitmap;
import android.renderscript.Matrix4f;

import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;

import static com.ghs.mazegame.game.Renderer.defaultShader;

public class Backplate {

    private float x, y, width, height;
    private Camera camera;
    private Texture texture;
    private Shader shader;
    private VAO vao;

    public Backplate(Camera camera, float x, float y, int width, int height, int borderThickness) {
        this.camera = camera;
        shader = defaultShader;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

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

        texture = makePlate(width, height, borderThickness, false);
    }

    public void render() {
        Matrix4f proj = camera.getUnatransformedProjection();
        proj.translate(x, y, 0);
        shader.setUniformMat4f("projection", proj);
        texture.bind();
        shader.enable();
        vao.render();
        shader.disable();
        texture.unbind();
    }

    public boolean contains(float x, float y) {
        if ((x >= this.x && x < this.x + this.width) && (y >= this.y && y < this.y + this.height)) {
            return true;
        } else {
            return false;
        }
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public static Texture makePlate(int width, int height, int borderThickness, boolean invert) {
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((i < borderThickness && i < height - j - 1) || (j < borderThickness && j < width - i - 1)) {
                    if (invert)
                        bitmap.setPixel(i, j, 0xFF303030);
                    else
                        bitmap.setPixel(i, j, 0xFF999999);
                }
                else if((i >= width - borderThickness && i > width - j - 1) || (j >= height - borderThickness && j > height - i - 1)) {
                    if (invert)
                        bitmap.setPixel(i, j, 0xFF999999);
                    else
                        bitmap.setPixel(i, j, 0xFF303030);
                }
                else
                    bitmap.setPixel(i, j, 0xFF686868);
            }
        }
        return new Texture(bitmap);
    }
}
