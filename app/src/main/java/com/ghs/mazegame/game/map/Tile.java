package com.ghs.mazegame.game.map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Matrix4f;

import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.Main;

import static com.ghs.mazegame.game.Main.SCALE;
import static com.ghs.mazegame.game.Main.defaultShader;

public class Tile {

    private float x, y, width, height;
    private boolean solid;
    private Camera camera;
    private Texture texture;
    private Shader shader;
    private VAO vao;

    private Bitmap bitmap;

    public Tile(Camera camera, int identifier, boolean solid, float depth) {
        this.camera = camera;
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
        Resources resources = Main.resources;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            bitmap = BitmapFactory.decodeResource(resources, identifier, options);

            texture = new Texture(bitmap);
        } catch(Exception e) {
            e.printStackTrace();
        }
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

        bitmap = Bitmap.createBitmap(SCALE * 2, SCALE * 2, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                if(i == 0 || j == 0)
                    bitmap.setPixel(i, j, 0xFFDDDDDD);
                else
                    bitmap.setPixel(i, j, 0x00000000);
            }
        }
        texture = new Texture(bitmap);
        bitmap = Bitmap.createBitmap(SCALE, SCALE, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < bitmap.getWidth(); i++)
            for (int j = 0; j < bitmap.getHeight(); j++)
                bitmap.setPixel(i, j, 0XFF000000);
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

    public Bitmap getBitmap() {
        return bitmap;
    }
}
