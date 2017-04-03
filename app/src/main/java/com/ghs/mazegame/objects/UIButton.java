package com.gnarly.game.objects;

import com.gnarly.engine.components.Shader;
import com.gnarly.engine.components.Texture;
import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Library;

public class UIButton extends UIImage {

	private Window window;
	
	private Camera camera;
	
	private Texture up, down, hover;
	
	private int type;
	private boolean affector;
	private boolean pressed;
	
	public UIButton(Camera camera, Texture texture, Shader shader, Window window, Texture up, Texture down, Texture hover, float x, float y, float width, float height, int type, boolean affector) {
		super(camera, texture, shader, x, y, width, height);
		this.window = window;
		this.camera = camera;
		this.up = up;
		this.down = down;
		this.hover = hover;
		this.type = type;
		this.affector = affector;
	}

	public void update() {
		if ((window.getMouseCoords().x > this.getX() && window.getMouseCoords().x < getX()+getWidth()) && (window.getMouseCoords().y > this.getY() && window.getMouseCoords().y < getY()+getHeight())) {
			onHover();
			if (window.isMousePressed(0)) {
				pressed = true;
				onPress();
			} else {
				if (pressed) {
					pressed = false;
					onRelease();
				}
			}
		} else {
			onLeave();
		}
	}
	
	public boolean getAffector() {
		return affector;
	}
	
	public void onPress() {
		setTexture(down);
	}
	
	public void onRelease() {
		setTexture(up);
		affector = !affector;
	}
	
	public void onHover() {
		setTexture(hover);
	}
	
	public void onLeave() {
		setTexture(up);
	}
	
	public void setTextures(Texture up, Texture down, Texture hover) {
		this.up = up;
		this.down = down;
		this.hover = hover;
	}
	
}
