package com.ghs.mazegame.game.map;

import android.graphics.Bitmap;
import android.renderscript.Matrix4f;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.utils.Hitbox;
import com.ghs.mazegame.game.Renderer;

import static com.ghs.mazegame.game.Renderer.SCALE;
import static com.ghs.mazegame.game.Renderer.defaultShader;

public class Tile {

    private float x, y, width, height;
    private boolean solid;
    private Camera camera;
    private Texture texture;
    private Shader shader;
    private VAO vao;

    public Tile(Camera camera, Texture texture, boolean solid, float depth) {
        this.camera = camera;
        this.texture = texture;
        shader = defaultShader;
        this.x = 0;
        this.y = 0;
        this.width = SCALE;
        this.height = SCALE;
        this.solid = solid;
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

    public Tile(Camera camera) {
        this.camera = camera;
        shader = defaultShader;
        this.x = 0;
        this.y = 0;
        this.width = SCALE;
        this.height = SCALE;
        float[] vertices = new float[] {
            0.0f,  0.0f,   0.9f, //TOP LEFT
            0.0f,  height, 0.9f, //BOTTOM LEFT
            width, height, 0.9f, //BOTTOM RIGHT
            width, 0.0f,   0.9f  //TOP RIGHT
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

        final Bitmap bitmap = Bitmap.createBitmap(SCALE * 2, SCALE * 2, Bitmap.Config.ARGB_8888);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(i == 0 || j == 0)
                    bitmap.setPixel(i, j, 0xFFDDDDDD);
                else
                    bitmap.setPixel(i, j, 0x00000000);
            }
        }
        texture = new Texture(bitmap);
    }

    public void render() {
        Matrix4f proj = camera.getProjection();
        proj.translate(x, y, 0);
        shader.setUniformMat4f("projection", proj);
        texture.bind();
        shader.enable();
        vao.render();
        shader.disable();
        texture.unbind();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
