package com.ghs.mazegame.engine.components;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.ghs.mazegame.game.Main;

public class Texture {

    private int id, width, height;

    public Texture(int identifier) {
        Resources resources = Main.resources;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            final Bitmap bitmap = BitmapFactory.decodeResource(resources, identifier, options);

            width = bitmap.getWidth();
            height = bitmap.getHeight();

            setTexture(bitmap);

            bitmap.recycle();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Texture(Bitmap bitmap) {
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        setTexture(bitmap);
        bitmap.recycle();
    }

    private void setTexture(Bitmap bitmap) {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        id = textureHandle[0];

        if (id != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, id);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        }
        else
            throw new RuntimeException("Error loading texture.");
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getID() {
        return id;
    }

    public void bind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, id);
    }

    public void unbind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }
}