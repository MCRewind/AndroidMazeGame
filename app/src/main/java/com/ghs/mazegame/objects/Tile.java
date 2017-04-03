package com.gnarly.game.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.gnarly.engine.components.Shader;
import com.gnarly.engine.components.Texture;
import com.gnarly.engine.components.VAO;
import com.gnarly.engine.display.Camera;
import com.gnarly.engine.utils.Hitbox;

public class Tile {

	private float x, y;
	private boolean solid;
	private Hitbox hitbox;
	private Camera camera;
	private VAO vao;
	private Texture texture;
	private Shader shader;
	
	public Tile(Camera camera, Texture texture, Shader shader, float x, float y, float width, float height, boolean solid, boolean transparency) {
		this.texture = texture;
		this.shader = shader;
		this.camera = camera;
		this.x = x;
		this.y = y;
		this.solid = solid;
		hitbox = new Hitbox(x, y, width, height);
		float z = transparency ? 0.1f : 0;
		float[] vertices = new float[] {
			0.0f,  0.0f,   z, //TOP LEFT
			0.0f,  height, z, //BOTTOM LEFT
			width, height, z, //BOTTOM RIGHT
			width, 0.0f,   z  //TOP RIGHT
		};
		int[] indices = new int[] {
			0, 1, 3,
			1, 2, 3
		};
		int[] texCoords = new int[] {
			0, 0,
			0, 1,
			1, 1,
			1, 0
		};
 		vao = new VAO(vertices, indices, texCoords);
 	}
	
	public void render() {
		Matrix4f proj = new Matrix4f();
		camera.getProjection().translate(new Vector3f(x, y, 0), proj);
		shader.setUniformMat4f("projection", proj);
		texture.bind();
		shader.enable();
		vao.render();
		shader.disable();
		texture.unbind();
	}
	
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public Texture getTexture() {
		return texture;
	}
}
