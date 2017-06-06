package com.ghs.mazegame.game.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Matrix4f;
import android.util.Log;

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

    public static int maxChars;

    public static final int
        STATE_RELEASED = 0,
        STATE_UNPRESSED = 1,
        STATE_PRESSED = 2,
        STATE_HELD = 3;

    private static Font font;

    private float x = 0, y = 0, xOffset = 0, yOffset = 0, width = 0, height = 0;
    private int state;
    private String map;
    private Camera camera;
    private VAO borderVAO, vao;
    private Texture border, texture;
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
        this.height = (cameraWidth - SCALE) * 2 / 9f;
        over = new Rectangle(camera, x, y, depth - 0.01f, width, height, 0, 0, 0, 0.25f);
        Bitmap borderBitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < borderBitmap.getWidth(); i++)
            for (int j = 0; j < borderBitmap.getHeight(); j++)
                if (i == 0 || j == 0 || i == borderBitmap.getWidth() - 1 || j == borderBitmap.getHeight() - 1)
                    borderBitmap.setPixel(i, j, 0xFFFFFFFF);
        border = new Texture(borderBitmap);
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
        borderVAO = new VAO(vertices, indices, texCoords);
        vertices[4] -= 2;
        vertices[6] -= 2;
        vertices[7] -= 2;
        vertices[9] -= 2;
        vao = new VAO(vertices, indices, texCoords);
        File file = new File(context.getFilesDir(), map + ".png");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        texture = new Texture(BitmapFactory.decodeFile(file.getPath(), options));
        if(font == null)
            font = new Font(camera, R.drawable.bunky_font, R.raw.bunky, 6, depth, 1, 1, 1, 1);
    }

    public void update() {
        float tx = touchX + camera.getX();
        float ty = touchY + camera.getY();
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
    }

    public void render() {
        shader.enable();
        Matrix4f model = new Matrix4f();
        shader.setUniformMat4f("projection", camera.getProjection());
        model.loadTranslate(x + xOffset + 1, y + yOffset + 1, 0);
        shader.setUniformMat4f("model", model);
        texture.bind();
        vao.render();
        texture.unbind();
        model.loadTranslate(x + xOffset, y + yOffset, 0);
        shader.setUniformMat4f("model", model);
        border.bind();
        borderVAO.render();
        border.unbind();
        shader.disable();
        if(state >= STATE_PRESSED)
            over.render();
        float width = font.getLength(map);
        font.drawString(map, x + xOffset + (this.width - width) / 2, y + yOffset + height + 1);
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

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        over.setPosition(this.x + xOffset, this.y + yOffset);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        over.setPosition(this.x + xOffset, this.y + yOffset);
    }

    public void setOffset(float x, float y) {
        this.xOffset = x;
        this.yOffset = y;
        over.setPosition(this.x + xOffset, this.y + yOffset);
    }

    public void setState(int state) {
        this.state = state;
    }
}