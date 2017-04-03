package com.gnarly.game.panels;

import org.joml.Vector3f;

import com.gnarly.engine.components.Animation;
import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Hitbox;
import com.gnarly.engine.utils.Library;
import com.gnarly.engine.utils.MapManager;
import com.gnarly.game.objects.LoaderButton;
import com.gnarly.game.objects.Player;
import com.gnarly.game.objects.Tile;
import com.gnarly.game.objects.UIButton;
import com.gnarly.game.objects.UIImage;
import com.sun.java.swing.plaf.windows.resources.windows;

public class PlayPanel extends GenericPanel {

	private final float SCALE = 64;
	private int width = 32, height = 32;
	private Camera camera;
	private Library library;
	private Tile[][] map;
	private Tile[][] backMap;
	private Player player;
	private Window window;
	private UIImage hud, pauseMenu;
	private UIButton pauseButton;
	private LoaderButton homeButton;
	private MapManager mapManager = new MapManager();
	
	private boolean paused = false;
	
	public PlayPanel(Camera camera, Window window, Library library) {
		this.camera = camera;
		this.library = library;
		this.window = window;
		pauseMenu = new UIImage(camera, library.getTexture("PauseMenu.png"), library.getShader("default"), window.getWidth()/2-(SCALE / 8 * 144)/2, window.getHeight()/2-(SCALE / 8 * 108)/2, SCALE / 8 * 144, SCALE / 8 * 108);
		player = new Player(window, camera, library.getTexture("Claytob.png"), library.getShader("default"), SCALE * 2, SCALE, SCALE, SCALE, width * SCALE, height * SCALE);
		pauseButton = new UIButton(camera, library.getTexture("PauseButtonUp.png"), library.getShader("default"), window, library.getTexture("PauseButtonUp.png"), library.getTexture("PauseButtonDown.png"), library.getTexture("PauseButtonUpHover.png"), 25, 25, SCALE / 16 * 32, SCALE / 16 * 32, 1, paused);
		homeButton = new LoaderButton(camera, library.getTexture("HomeButtonUp.png"), library.getShader("default"), window, library.getTexture("HomeButtonUp.png"), library.getTexture("HomeButtonDown.png"), library.getTexture("HomeButtonUpHover.png"), (window.getWidth()/2)-3*(SCALE / 16 * 32), window.getHeight()/2, SCALE / 8 * 32, SCALE / 8 * 32, 0);
	}
	
	//Updates all the elements of the panel nad the camera
	public void update() {
		if(pauseButton.getAffector()) {
			pauseButton.setTextures(library.getTexture("ResumeButtonUp.png"), library.getTexture("ResumeButtonDown.png"), library.getTexture("ResumeButtonUpHover.png"));
			pauseButton.setPosition((window.getWidth()/2)+SCALE / 16 * 32, window.getHeight()/2);
			pauseButton.setWidth(SCALE / 8 * 32);
			pauseButton.setHeight(SCALE / 8 * 32);
			pauseButton.makeVAO();
			homeButton.update();
		} else {
			pauseButton.setTextures(library.getTexture("PauseButtonUp.png"), library.getTexture("PauseButtonDown.png"), library.getTexture("PauseButtonUpHover.png"));
			pauseButton.setPosition(25, 25);
			pauseButton.setWidth(SCALE / 16 * 32);
			pauseButton.setHeight(SCALE / 16 * 32);
			pauseButton.makeVAO();
		}
		pauseButton.update();
		//Updates player
		player.update();
		//Checks collision
		checkPlayerCollision();
		//Sets the camera position putting the player in the center
		camera.setPosition(player.getX() + (player.getWidth() - camera.getWidth()) / 2, player.getY() + (player.getHeight() - camera.getHeight()) / 2, 0);
		//Checks that the camera is notgoing past the edge of the map and adjusting the camera position if neccessary
		if(camera.getX() < 0)
			camera.setPosition(0, camera.getY(), 0);
		else if(camera.getX() > width * SCALE - camera.getWidth())
			camera.setPosition(width * SCALE - camera.getWidth(), camera.getY(), 0);
		if(camera.getY() < 0)
			camera.setPosition(camera.getX(), 0, 0);
		else if(camera.getY() > height * SCALE - camera.getHeight())
			camera.setPosition(camera.getX(), height * SCALE - camera.getHeight(), 0);
	}
	
	//Render all the panel components
	public void render() {
		//Determines the area of the map that needs to be rendered
		int minX = (int) (camera.getX() / SCALE);
		int maxX = (int) ((camera.getX() + camera.getWidth()) / SCALE);
		int minY = (int) (camera.getY() / SCALE);
		int maxY = (int) ((camera.getY() + camera.getHeight()) / SCALE);
		//Renders teh map
		for (int i = Math.max(minX, 0); i <= maxX && i < map.length; i++) {
			for (int j = Math.max(minY, 0); j <= maxY && j < map[0].length; j++) {
				map[i][j].render();
			}
		}
		//Renders the player and the HUD
		pauseButton.render();
		if(pauseButton.getAffector()) {
			homeButton.render();
			pauseMenu.render();
		}
		
		player.render();
	}
	
	//Checks player hitbox against the nearest block and adjusts position accordingly
	//If the collision is successful it checks again so that you dont get pushed into another block
	private void checkPlayerCollision() {
		boolean check = false;
		Hitbox playerHitbox = player.getHitbox();
		//Determines which tiles the player is over
		int minX = (int) (player.getX() / SCALE);
		int maxX = (int) ((player.getX() + player.getWidth()) / SCALE);
		int minY = (int) (player.getY() / SCALE);
		int maxY = (int) ((player.getY() + player.getHeight()) / SCALE);
		Hitbox closest = null;
		Vector3f length1 = null;
		//Determines the closest tile of the ones the player is on
		for (int i = Math.max(minX, 0); i <= maxX && i < map.length; i++) {
			for (int j = Math.max(minY, 0); j <= maxY && j < map[0].length; j++) {
				if(map[i][j].isSolid()) {
					if(closest == null) {
						closest = map[i][j].getHitbox();
						length1 = closest.getCenter().sub(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0, new Vector3f());
					}
					else {
						Vector3f length2 = map[i][j].getHitbox().getCenter().sub(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0, new Vector3f());
						if(length1.lengthSquared() > length2.lengthSquared()) {
							closest = map[i][j].getHitbox();
							length1 = length2;
						}
					}	
				}
			}
		}
		//Adjusts the player position if necessary and runs another check if necessary
		if(closest != null) {
			check = playerHitbox.checkCollision(closest);
			if(check) {
				player.translate(playerHitbox.collisionAdjust(closest));
				checkPlayerCollision();
			}
		}
	}
	
	//Loads a map based on an array of ints
	//0 = Stone Floor
	//1 = Brick Wall
	public void setMap() {
		this.map = mapManager.loadMap("map", camera, library, (int) SCALE);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if(map[i][j].getTexture().getName().equals("Spawn Pad.png")) {
					player.setPosition(map[i][j].getHitbox().getX(), map[i][j].getHitbox().getY());
				}
			}
		}
		
		this.backMap = mapManager.loadMap("backMap", camera, library, (int) SCALE);
	}
}
