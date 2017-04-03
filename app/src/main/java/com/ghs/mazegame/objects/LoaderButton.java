package com.gnarly.game.objects;

import com.gnarly.engine.components.Shader;
import com.gnarly.engine.components.Texture;
import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.game.Main;

public class LoaderButton extends UIButton {
	
	// This class is bad
	
	int panelId;
	
	public LoaderButton(Camera camera, Texture texture, Shader shader, Window window, Texture up, Texture down, Texture hover, float x, float y, float width, float height, int panelId) {
		super(camera, texture, shader, window, up, down, hover, x, y, width, height, 1, true);
		this.panelId = panelId;
	}
	
	public void onPress() {
		super.onPress();
	}
	
	public void onRelease() {
		super.onRelease();	
		switch (panelId) {
		case 0:
			Main.menuInit();
			break;
		case 1:
			Main.playInit();
			break;
		case 2:
			Main.editInit();
			break;
		}
	}
	
	public void onHover() {
		super.onHover();
	}
	
	public void onLeave() {
		super.onLeave();
	}

}
