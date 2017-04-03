package com.gnarly.engine.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.gnarly.engine.display.Camera;
import com.gnarly.game.objects.Item;
import com.gnarly.game.objects.Tile;

public class MapManager {
	
	boolean isStart, isEnd;
	
	public void saveMap(Tile[][] map, String fileName) {
		int[][] intMap = new int[map.length][map[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				switch (map[i][j].getTexture().getName()) {
				case "Stone Floor.png" :
					intMap[i][j] = 0;
					break;
				case "Brick Wall.png" :
					intMap[i][j] = 1;
					break;
				case "Circular Wall.png" :
					intMap[i][j] = 2;
					break;
				case "Spawn Pad.png" :
					intMap[i][j] = 3;
					break;
				case "End Pad.png" :
					intMap[i][j] = 4;
					break;
				case "Stone Lock.png" :
					intMap[i][j] = 5;
					break;
					
				case "Gold Lock.png" :
					intMap[i][j] = 6;
					break;
				case "Stone Key.png" :
					intMap[i][j] = 7;
					break;
					
				case "Gold Key.png" :
					intMap[i][j] = 8;
					break;
				}
			}
		}
		
		try {
			//Write anarray to a binary file
		    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName + ".dat"));
		    oos.writeObject(intMap);
		    oos.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Tile[][] loadMap(String fileName, Camera camera, Library library, int SCALE) {
		int[][] ia = null;
		isStart = false;
		isEnd = false;
		
		try {
			//Read arrays from a binary file
		    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName + ".dat"));
		    ia = (int[][]) (ois.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Tile[][] map = new Tile[ia.length][ia[0].length];
		
		for (int k = 0; k < ia.length; k++) {
			for (int k2 = 0; k2 < ia[0].length; k2++) {
				switch (ia[k][k2]) {
					case 0 :
						map[k][k2] = new Tile(camera, library.getTexture("Stone Floor.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, false, false);
						break;
					case 1 :
						map[k][k2] = new Tile(camera, library.getTexture("Brick Wall.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, true, false);
						break;
					case 2 :
						map[k][k2] = new Tile(camera, library.getTexture("Circular Wall.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, true, false);
						break;
					case 3 :
						isStart = true;
						map[k][k2] = new Tile(camera, library.getTexture("Spawn Pad.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, false, false);
						break;
					case 4 :
						isEnd = true;
						map[k][k2] = new Tile(camera, library.getTexture("End Pad.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, false, false);
						break;
					case 5 :
						map[k][k2] = new Tile(camera, library.getTexture("Stone Lock.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, true, false);
						break;
					case 6 :
						map[k][k2] = new Tile(camera, library.getTexture("Gold Lock.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, true, false);
						break;
					case 7 :
						map[k][k2] = new Item(camera, library.getTexture("Stone Key.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, false);
						break;
					case 8 :
						map[k][k2] = new Item(camera, library.getTexture("Gold Key.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, false);
						break;
				}
			}
		}
		return map;
	}
	
	public boolean isStart() {
		return isStart;
	}
	
	public boolean isEnd() {
		return isEnd;
	}
	
}
