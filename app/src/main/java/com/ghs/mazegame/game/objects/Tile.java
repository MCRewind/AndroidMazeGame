package com.ghs.mazegame.game.objects;

import android.renderscript.Matrix4f;

import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.utils.Hitbox;

public class Tile {

    private float x, y, width, height;
    private boolean solid;
    private Camera camera;
    private Texture texture;
    private Shader shader;
    private VAO vao;
    private Hitbox hitbox;

    public Tile(Camera camera, Texture texture, Shader shader, float x, float y, float width, float height, boolean solid) {
        this.camera = camera;
        this.texture = texture;
        this.shader = shader;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        hitbox = new Hitbox(x, y, width, height);
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

    public Hitbox getHitbox() {
        return hitbox;
    }

    public boolean isSolid() {
        return solid;
    }
}
