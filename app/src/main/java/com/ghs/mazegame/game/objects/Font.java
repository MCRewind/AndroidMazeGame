package com.ghs.mazegame.game.objects;

import android.renderscript.Matrix4f;
import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;

import java.util.Scanner;

import static com.ghs.mazegame.game.Main.resources;

public class Font {

    private Camera camera;
    private static Shader shader;
    private Texture texture;
    private float[] widths;
    private VAO[] vaos;
    private int numRows, numColumns, height, cellWidth, cellHeight;
    private float scale, r, g, b, a;


    public Font(Camera camera, int fontId, int infoId, float height, float depth, float r, float g, float b, float a) {
        this.camera = camera;
        if(shader == null)
            shader = new Shader(R.raw.defaultvs, R.raw.textfs);
        texture = new Texture(fontId);
        Scanner info = new Scanner(resources.openRawResource(infoId));
        numColumns = Integer.parseInt(info.nextLine());
        numRows = Integer.parseInt(info.nextLine());
        cellWidth = Integer.parseInt(info.nextLine());
        cellHeight = Integer.parseInt(info.nextLine());
        this.height = cellHeight;
        int numChars = numColumns * numRows;
        widths = new float[numChars];
        vaos = new VAO[numChars];
        float[] vertices = new float[] {
            0.0f, 0.0f,       depth, //TOP LEFT
            0.0f, cellHeight, depth, //BOTTOM LEFT
            0.0f, cellHeight, depth, //BOTTOM RIGHT
            0.0f, 0.0f,       depth  //TOP RIGHT
        };
        int[] indices = new int[] {
            0, 1, 3,
            1, 2, 3
        };
        float[] texCoords = new float[8];
        float tx, ty, dx, dy = 1f / (float) numRows;
        for (int i = 0; i < numChars; i++) {
            widths[i] = Integer.parseInt(info.nextLine());
            vertices[6] = widths[i];
            vertices[9] = widths[i];
            tx = (float) (i % numColumns) / (float) numColumns;
            ty = (float) (i / numColumns) / (float) numRows;
            dx = (1f / (float) numColumns) * ((float) widths[i] / (float) cellWidth);
            texCoords[0] = tx;
            texCoords[1] = ty;
            texCoords[2] = tx;
            texCoords[3] = ty + dy;
            texCoords[4] = tx + dx;
            texCoords[5] = ty + dy;
            texCoords[6] = tx + dx;
            texCoords[7] = ty;
            vaos[i] = new VAO(vertices, indices, texCoords);
        }
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        scale = height / (float) this.height;
    }

    public void drawString(String string, float x, float y) {
        texture.bind();
        for (char c : string.toCharArray()) {
            shader.setUniform4f("color", r, g, b, a);
            Matrix4f model = new Matrix4f();
            model.loadTranslate(x, y, 0);
            model.scale(scale, scale, 0);
            shader.setUniformMat4f("model", model);
            shader.setUniformMat4f("projection", camera.getProjection());
            shader.enable();
            vaos[c].render();
            shader.disable();
            x += widths[c] * scale;
        }
        texture.unbind();
    }

    public void setHeight(float height) {
        scale = height / (float) this.height;
    }

    public void setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float getLength(String string) {
        float width = 0;
        for (char c : string.toCharArray())
            width += widths[c];
        return width * scale;
    }

    public float getHeight() {
        return height * scale;
    }
}
