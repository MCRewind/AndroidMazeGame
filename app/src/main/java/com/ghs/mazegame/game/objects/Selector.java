package com.ghs.mazegame.game.objects;

import android.renderscript.Matrix4f;
import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.game.interfaces.GameObject;

import static com.ghs.mazegame.engine.display.Surface.touchX;
import static com.ghs.mazegame.engine.display.Surface.touchY;
import static com.ghs.mazegame.game.Main.defaultShader;

public class Selector implements GameObject {

    public static int
            STATE_UNPRESSED = 0,
            STATE_PRESSED = 1;

    private float x = 0, y = 0, xOffset = 0, yOffset = 0, width = 0, height = 0;
    private boolean independent;
    private int selected = 0, state;
    private float scale = 3, prevScale = 4f;
    private Image[] selections, previews;
    private int[] directions;
    private Vector3f dir;
    private Camera camera;
    private VAO vao;
    private int[] textures;
    private Shader shader;

    public Selector(Camera camera, float x, float y, float depth, float width, float height, int[] directions, int[] textures, boolean independent) {
        this.textures = textures;
        selections = new Image[4];
        previews = new Image[4];
        shader = new Shader(R.raw.defaultvs, R.raw.dpadfs);
        selections[0] = new Image(camera, new Texture(R.drawable.s_left),  shader, x,                     y + height / scale,     width / scale, height / scale, 0.2f);
        selections[1] = new Image(camera, new Texture(R.drawable.s_right), shader, x + 2 * width / scale, y + height / scale,     width / scale, height / scale, 0.2f);
        selections[2] = new Image(camera, new Texture(R.drawable.s_up),    shader, x + width / scale,     y,                      width / scale, height / scale, 0.2f);
        selections[3] = new Image(camera, new Texture(R.drawable.s_down ), shader, x + width / scale,     y + 2 * height / scale, width / scale, height / scale, 0.2f);
        previews[0]   = new Image(camera, new Texture(textures[0]),        x,                         y + height / prevScale,     width / prevScale, height / prevScale, 0.1f);
        previews[1]   = new Image(camera, new Texture(textures[1]),        x + 2 * width / prevScale, y + height / prevScale,     width / prevScale, height / prevScale, 0.1f);
        previews[2]   = new Image(camera, new Texture(textures[2]),        x + width / prevScale,     y,                          width / prevScale, height / prevScale, 0.1f);
        previews[3]   = new Image(camera, new Texture(textures[3]),        x + width / prevScale,     y + 2 * height / prevScale, width / prevScale, height / prevScale, 0.1f);
        state = STATE_UNPRESSED;
        dir = new Vector3f();
        this.camera = camera;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.directions = directions;
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
        selected = -1;
        for (int i = 0; i < selections.length; i++)
            if (selections[i].contains(touchX, touchY))
                selected = i;
        if (selected != -1) {
            float vecX = touchX - (x + width / 2);
            float vecY = touchY - (y + height / 2);
            float length = (float) Math.sqrt(vecX * vecX + vecY * vecY);
            dir.x = vecX / length;
            dir.y = vecY / length;
            if(length <= width / 6.0f) {
                dir.x *= 0.5f;
                dir.y *= 0.5f;
            }
        }
        else {
            dir.x = 0;
            dir.y = 0;
        }
    }

    public void render() {
        for (int i = 0; i < previews.length; i++) {
            previews[i].render();
        }
        for (int i = 0; i < selections.length; i++) {
            shader.enable();
            if (selected == i)
                shader.setUniform4f("color", 0.3f, 0.5f, 0.7f, 0.6f);
            else
                shader.setUniform4f("color", 0.4f, 0.4f, 0.4f, 0.3f);
            selections[i].render();
        }
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

    public void setTexture(int state, int texture) {
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
