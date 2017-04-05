package com.ghs.mazegame.engine.components;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import com.ghs.mazegame.R;

import javax.imageio.ImageIO;

import GL

import org.lwjgl.BufferUtils;

public class Texture {

	private String name;
	private int id, width, height;
	
	public Texture(Resources resources, int identifier) {
		try {
			//File file = new File(fileName);
			name = file.getName();
			Bitmap bi = BitmapFactory.decodeResource(resources, identifier);
			width = bi.getWidth();
			height = bi.getHeight();
			
			int[] pixelsRaw = new int[width * height];
			pixelsRaw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			//ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			ByteBuffer pixels = bi.getPixels(0, 0, width, height, null, 0, width);
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int pixel = pixelsRaw[i * width + j];
					pixels.put((byte)((pixel >> 16) & 0xFF)); //RED
					pixels.put((byte)((pixel >>  8) & 0xFF)); //GREEN
					pixels.put((byte)((pixel      ) & 0xFF)); //BLUE
					pixels.put((byte)((pixel >> 24) & 0xFF)); //ALPHA
				}
			}
			pixels.flip();
			
			id = glGenTextures();
			
			bind();

			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

			GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pixels);
		
			unbind();
		} catch(IOException e) {
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
