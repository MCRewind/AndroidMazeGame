package com.gnarly.engine.utils;

import java.io.File;
import java.util.ArrayList;

import com.gnarly.engine.components.Shader;
import com.gnarly.engine.components.Texture;

public class Library {

	Texture[] textures;
	Shader[] shaders;
	
	public Library(String texStart, String shaderStart) {
		loadTextures(texStart);
		loadShaders(shaderStart);
	}
	
	private void loadTextures(String texStart) {
		String[] paths = loadStructure(texStart);
		textures = new Texture[paths.length];
		for (int i = 0; i < paths.length; i++)
			textures[i] = new Texture(paths[i]);
	}
	
	private void loadShaders(String shaderStart) {
		String[] paths = loadStructure(shaderStart);
		shaders = new Shader[paths.length / 2];
		for (int i = 0; i < paths.length; i += 2)
			shaders[i] = new Shader(paths[i + 1], paths[i]);
	}
	
	private String[] loadStructure(String structureStart) {
		ArrayList<String> check = new ArrayList<>();
		ArrayList<String> files = new ArrayList<>();
		check.add(structureStart);
		while(check.size() > 0) {
			File file = new File(check.get(0));
			String path = file.getPath();
			String[] paths = file.list();
			if(paths != null) {
				for (int i = 0; i < paths.length; i++)
					check.add(path + "/" + paths[i]);
			}
			else
				files.add(path);
			check.remove(0);
		}
		String[] ret = new String[files.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = files.get(i);
		return ret;
	}
	
	public Texture getTexture(String name) {
		for (int i = 0; i < textures.length; i++)
			if(textures[i].getName().equals(name))
				return textures[i];
		throw new RuntimeException("Could not find texture: " + name + "!");
	}
	
	public Shader getShader(String name) {
		for (int i = 0; i < shaders.length; i++)
			if(shaders[i].getName().equals(name))
				return shaders[i];
		throw new RuntimeException("Could not find shader: " + name + "!");
	}
}
