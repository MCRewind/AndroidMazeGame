package com.ghs.mazegame.game.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Matrix4f;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.interfaces.GameObject;

import java.io.File;

import static com.ghs.mazegame.engine.display.Surface.touchX;
import static com.ghs.mazegame.engine.display.Surface.touchY;
import static com.ghs.mazegame.game.Main.SCALE;
import static com.ghs.mazegame.game.Main.cameraHeight;
import static com.ghs.mazegame.game.Main.cameraWidth;
import static com.ghs.mazegame.game.Main.context;
import static com.ghs.mazegame.game.Main.defaultShader;

public class Thumbnail implements GameObject {

    public static int
        STATE_RELEASED = 0,
        STATE_UNPRESSED = 1,
        STATE_PRESSED = 2,
        STATE_HELD = 3;

    private String map;

    private float x = 0, y = 0, width = 0, height = 0;
    private int state;
    private Camera camera;
    private VAO vao;
    private Texture texture;
    private Rectangle over;
    private Shader shader;

    public Thumbnail(Camera camera, String map, float x, float y, float depth) {
        shader = defaultShader;
        state = STATE_UNPRESSED;
        this.camera = camera;
        this.map = map;
        this.x = x;
        this.y = y;
        this.width = (cameraWidth - SCALE) / 3f;
        this.height = width * 2 / 3f;
        over = new Rectangle(camera, x, y, width, height, 0, 0, 0, 0.5f);
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
        File file = new File(context.getFilesDir(), map + ".png");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        texture = new Texture(BitmapFactory.decodeFile(file.getPath(), options));
    }

    public Thumbnail(Camera camera, float x, float y, float depth) {
        shader = defaultShader;
        state = STATE_UNPRESSED;
        this.camera = camera;
        this.x = x;
        this.y = y;
        this.width = (cameraWidth - SCALE) / 3f;
        this.height = width * 2 / 3f;
        over = new Rectangle(camera, x, y, width, height, 0, 0, 0, 0.5f);
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
        texture = new Texture(R.drawable.new_map_button);
    }

    public void update() {
        float tx = touchX + camera.getX();
        float ty = touchY + camera.getY();
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
        if(state >= STATE_PRESSED)
            over.render();
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

    public String getMap() {
        return map;
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

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
