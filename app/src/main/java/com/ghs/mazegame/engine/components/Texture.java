package com.ghs.mazegame.engine.components;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.ghs.mazegame.game.Renderer;

public class Texture {

    private String name;
    private int id, width, height;

    public Texture(int identifier) {
        Resources resources = Renderer.resources;
        try {
            final int[] textureHandle = new int[1];

            GLES20.glGenTextures(1, textureHandle, 0);

            id = textureHandle[0];

            if (id != 0) {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;   // No pre-scaling

                // Read in the resource
                final Bitmap bitmap = BitmapFactory.decodeResource(resources, identifier, options);

                width = bitmap.getWidth();
                height = bitmap.getHeight();

                // Bind to the texture in OpenGL
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, id);

                // Set filtering
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

                // Load the bitmap into the bound texture.
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

                // Recycle the bitmap, since its data has been loaded into OpenGL.
                bitmap.recycle();
            }
            if (id == 0)
                throw new RuntimeException("Error loading texture.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
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