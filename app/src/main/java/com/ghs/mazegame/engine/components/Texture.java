package com.ghs.mazegame.engine.components;




import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.ghs.mazegame.R;







public class Texture {

	private String name;
	private int id, width, height;
	
	public Texture(Resources resources, int identifier) {
		try {
			final int[] textureHandle = new int[1];

			GLES20.glGenTextures(1, textureHandle, 0);

			//id = glGenTextures();

			if (textureHandle[0] != 0)
			{
				final BitmapFactory.Options options = new BitmapFactory.Options();
				options.inScaled = false;   // No pre-scaling

				// Read in the resource
				final Bitmap bitmap = BitmapFactory.decodeResource(resources, identifier, options);

				// Bind to the texture in OpenGL
				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

				// Set filtering
				GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
				GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

				// Load the bitmap into the bound texture.
				GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

				// Recycle the bitmap, since its data has been loaded into OpenGL.
				bitmap.recycle();
			}

			if (textureHandle[0] == 0)
			{
				throw new RuntimeException("Error loading texture.");
			}

			/*
			bind();

			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

			GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pixels);
		
			unbind();
			*/
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
