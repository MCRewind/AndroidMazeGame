package com.gnarly.game.panels;

import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Library;
import com.gnarly.game.objects.LoaderButton;
import com.gnarly.game.objects.UIButton;
import com.gnarly.game.objects.UIImage;

public class MainMenu extends GenericPanel {
	
	private final float SCALE = 64;
	private int width = 32, height = 32;
	Camera camera;
	Library library;
	Window window;
	UIImage background;
	UIButton edit;
	UIButton play;
	public MainMenu(Camera camera, Window window, Library library) {
		this.camera = camera;
		this.library = library;
		this.window = window;
		background = new UIImage(camera, library.getTexture("background.png"), library.getShader("default"), 0, 0, camera.getWidth(), camera.getHeight());
		play = new LoaderButton(camera, library.getTexture("PlayButtonUp.png"), library.getShader("default"), window, library.getTexture("PlayButtonUp.png"), library.getTexture("PlayButtonDown.png"), library.getTexture("PlayButtonUpHover.png"), camera.getWidth()/2-(SCALE / 8 * 64)-20, camera.getHeight()/2, SCALE / 8 * 64, SCALE / 8 * 32, 1);
		edit = new LoaderButton(camera, library.getTexture("EditButtonUp.png"), library.getShader("default"), window, library.getTexture("EditButtonUp.png"), library.getTexture("EditButtonDown.png"), library.getTexture("EditButtonUpHover.png"), (camera.getWidth()/2)+20, camera.getHeight()/2, SCALE / 8 * 64, SCALE / 8 * 32, 2);
	}
	
	public void update() {
		edit.update();
		play.update();
	}
	
	public void render() {
		edit.render();
		play.render();
		background.render();
	}
	
}
