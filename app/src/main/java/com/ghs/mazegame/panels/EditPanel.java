package com.gnarly.game.panels;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Library;
import com.gnarly.engine.utils.MapManager;
import com.gnarly.game.objects.Item;
import com.gnarly.game.objects.LoaderButton;
import com.gnarly.game.objects.Selected;
import com.gnarly.game.objects.SelectorButton;
import com.gnarly.game.objects.Tile;
import com.gnarly.game.objects.UIButton;
import com.gnarly.game.objects.UIImage;

public class EditPanel extends GenericPanel {

	private final float SCALE = 64;
	private int width = 32, height = 32;
	private Camera camera;
	private Library library;
	private Tile[][] map;
	private Tile[][] backMap;
	private UIImage plate;
	private UIImage brickSelect;
	private UIImage circleBrickSelect;
	private UIImage startPadSelect;
	private UIImage endPadSelect;
	private UIImage pauseMenu;
	private UIImage stoneLockSelect;
	private UIImage goldLockSelect;
	private UIImage stoneKeySelect;
	private UIImage goldKeySelect;
	private SelectorButton button, button1;
	private LoaderButton homeButton;
	private UIButton button2, button3, button4, button5, button6, button7, arrowButton, pauseButton;
	private Window window;
	MapManager mapManager = new MapManager();
	Selected selected = new Selected();
	
	boolean isStart = false;
	boolean isEnd = false;
	
	boolean paused = false;
	
	public EditPanel(Camera camera, Window window, Library library) {
		this.camera = camera;
		this.library = library;
		this.window = window;
		plate = new UIImage(camera, library.getTexture("Plate.png"), library.getShader("default"), 350, 10, SCALE / 16 * 316, SCALE / 16 * 40);
		brickSelect = new UIImage(camera, library.getTexture("Brick Wall.png"), library.getShader("default"), 418, 58, SCALE / 16 * 16, SCALE / 16 * 16);
		circleBrickSelect = new UIImage(camera, library.getTexture("Circular Wall.png"), library.getShader("default"), 558, 58, SCALE / 16 * 16, SCALE / 16 * 16);
		startPadSelect = new UIImage(camera, library.getTexture("Spawn Pad.png"), library.getShader("default"), 708, 58, SCALE / 16 * 16, SCALE / 16 * 16);
		endPadSelect = new UIImage(camera, library.getTexture("End Pad.png"), library.getShader("default"), 858, 58, SCALE / 16 * 16, SCALE / 16 * 16);
		stoneLockSelect = new UIImage(camera, library.getTexture("Stone Lock.png"), library.getShader("default"), 1008, 58, SCALE / 16 * 16, SCALE / 16 * 16);
		goldLockSelect = new UIImage(camera, library.getTexture("Gold Lock.png"), library.getShader("default"), 1158, 58, SCALE / 16 * 16, SCALE / 16 * 16);
		stoneKeySelect = new UIImage(camera, library.getTexture("Stone Key.png"), library.getShader("default"), 1308, 358, SCALE / 16 * 16, SCALE / 16 * 16);
		goldKeySelect = new UIImage(camera, library.getTexture("Gold Lock.png"), library.getShader("default"), 1458, 58, SCALE / 16 * 16, SCALE / 16 * 16);
		pauseMenu = new UIImage(camera, library.getTexture("PauseMenu.png"), library.getShader("default"), window.getWidth()/2-(SCALE / 8 * 144)/2, window.getHeight()/2-(SCALE / 8 * 108)/2, SCALE / 8 * 144, SCALE / 8 * 108);
		button = new SelectorButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 385, 25, SCALE / 16 * 32, SCALE / 16 * 32, 0, selected);
		button1 = new SelectorButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 525, 25, SCALE / 16 * 32, SCALE / 16 * 32, 1, selected);
		button2 = new SelectorButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 675, 25, SCALE / 16 * 32, SCALE / 16 * 32, 3, selected);
		button3 = new SelectorButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 825, 25, SCALE / 16 * 32, SCALE / 16 * 32, 4, selected);
		button4 = new SelectorButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 975, 25, SCALE / 16 * 32, SCALE / 16 * 32, 5, selected);
		button5 = new SelectorButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 1125, 25, SCALE 	/ 16 * 32, SCALE / 16 * 32, 6, selected);
		button6 = new SelectorButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 1275, 25, SCALE / 16 * 32, SCALE / 16 * 32, 7, selected);
		button7 = new SelectorButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 1425, 25, SCALE / 16 * 32, SCALE / 16 * 32, 8, selected);
		arrowButton = new UIButton(camera, library.getTexture("ArrowButtonUp.png"), library.getShader("default"), window, library.getTexture("ArrowButtonUp.png"), library.getTexture("ArrowButtonDown.png"), library.getTexture("ArrowButtonUpHover.png"), 1563, 120, SCALE / 32 * 16, SCALE / 32 * 16, 0, false);
		pauseButton = new UIButton(camera, library.getTexture("PauseButtonUp.png"), library.getShader("default"), window, library.getTexture("PauseButtonUp.png"), library.getTexture("PauseButtonDown.png"), library.getTexture("PauseButtonUpHover.png"), 25, 25, SCALE / 16 * 32, SCALE / 16 * 32, 1, paused);
		homeButton = new LoaderButton(camera, library.getTexture("HomeButtonUp.png"), library.getShader("default"), window, library.getTexture("HomeButtonUp.png"), library.getTexture("HomeButtonDown.png"), library.getTexture("HomeButtonUpHover.png"), (window.getWidth()/2)-3*(SCALE / 16 * 32), window.getHeight()/2, SCALE / 8 * 32, SCALE / 8 * 32, 0);
	}
	
	//Updates all the elements of the panel nad the camera
	public void update() {
		//Updates Button
		button.update();
		button1.update();
		button2.update();
		button3.update();
		button4.update();
		button5.update();
		button6.update();
		button7.update();
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
		
		setTile();
		
		//Handles inputs and moves player with wasd
		float speed = 7, xChange = 0, yChange = 0;
		if(window.isKeyPressed(GLFW_KEY_W))
			yChange -= speed;
		if(window.isKeyPressed(GLFW_KEY_A))
			xChange -= speed;
		if(window.isKeyPressed(GLFW_KEY_S))
			yChange += speed;
		if(window.isKeyPressed(GLFW_KEY_D))
			xChange += speed;
		camera.translate(xChange, yChange, 0);
		
		if (window.isKeyPressed(GLFW_KEY_J))
			mapManager.saveMap(map, "map");
		if (window.isKeyPressed(GLFW_KEY_K))
			this.map = mapManager.loadMap("map", camera, library, (int) SCALE);
		
	//	arrowButton.update();
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
		//Renders the map
		for (int i = Math.max(minX, 0); i <= maxX && i < map.length; i++) {
			for (int j = Math.max(minY, 0); j <= maxY && j < map[0].length; j++) {
				map[i][j].render();
			}
		}
		
		for (int k = Math.max(minX, 0); k <= maxX && k < backMap.length; k++) {
			for (int j2 = Math.max(minY, 0); j2 <= maxY && j2 < backMap[0].length; j2++) {
				backMap[k][j2].render();
			}
		}
		
		//Renders the HUD
		pauseButton.render();
		if(pauseButton.getAffector()) {
			homeButton.render();
			pauseMenu.render();
		}
		brickSelect.render();
		circleBrickSelect.render();
		startPadSelect.render();
		endPadSelect.render();
		stoneLockSelect.render();
		goldLockSelect.render();
		//stoneKeySelect.render();
		goldLockSelect.render();
		button.render();
		button1.render();
		button2.render();
		button3.render();
		button4.render();
		button5.render();
		button6.render();
		button7.render();
		arrowButton.render();
		plate.render();
	}

	public void setTile() {
		int x = (int)((window.getMouseCoords().x + camera.getX()) / SCALE);
		int y = (int)((window.getMouseCoords().y + camera.getY()) / SCALE);
		
		if(!(plate.within((int) window.getMouseCoords().x, (int) window.getMouseCoords().y)) && !(pauseButton.within((int) window.getMouseCoords().x, (int) window.getMouseCoords().y))) {
			if (window.isMousePressed(0)) {			
				switch (selected.getId()) {
				case 0:
					map[x][y] = new Tile(camera, library.getTexture("Brick Wall.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, true, false);
					break;
				case 1:
					map[x][y] = new Tile(camera, library.getTexture("Circular Wall.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, true, false);
					break;
				case 2:
					map[x][y] = new Tile(camera, library.getTexture("Stone Floor.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, false, false);
					break;
				case 3:
					if (isStart == false) {
						map[x][y] = new Tile(camera, library.getTexture("Spawn Pad.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, false, false);
						isStart = true;
					}
					break;
				case 4:
					if (isEnd == false) {
						map[x][y] = new Tile(camera, library.getTexture("End Pad.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, false, false);
						isEnd = true;
					}
					break;
				case 5:
					map[x][y] = new Tile(camera, library.getTexture("Stone Lock.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, true, false);
					break;
				case 6:
					map[x][y] = new Tile(camera, library.getTexture("Gold Lock.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, false, false);
					break;
				case 7:
					map[x][y] = new Item(camera, library.getTexture("Stone Key.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, false);
					break;
				case 8:
					map[x][y] = new Item(camera, library.getTexture("Gold Key.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, false);
					break;
				}			
			} else if (window.isMousePressed(1)) {
				if (map[x][y].getTexture() == library.getTexture("Spawn Pad.png")) {
					isStart = false;
				} else if (map[x][y].getTexture() == library.getTexture("End Pad.png")) {
					isEnd = false;
				}
				map[x][y] = new Tile(camera, library.getTexture("Stone Floor.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, false, false);
			}
		}
	}
	
	//Loads a map based on an array of ints
	//0 = Stone Floor
	//1 = Brick Wall
	//2 = Circular Wall
	public void setMap() {
		this.map = mapManager.loadMap("map", camera, library, (int) SCALE);
		this.isStart = mapManager.isStart();
		this.isEnd = mapManager.isEnd();
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j].getTexture().getName().equals("Stone Floor.png")) {
					map[i][j] = new Tile(camera, library.getTexture("INVIS.png"), library.getShader("default"), i * SCALE, j * SCALE, 1, 1, false, false);
				}
			}
		}
		
		this.backMap = mapManager.loadMap("backMap", camera, library, (int) SCALE);
	}
}
