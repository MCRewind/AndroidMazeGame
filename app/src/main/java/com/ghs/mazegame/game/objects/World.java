package com.ghs.mazegame.game.objects;


import android.content.res.Resources;

import com.ghs.mazegame.R;
import com.ghs.mazegame.game.Renderer;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;

public class World {

    private final float SCALE = Renderer.SCALE;
    private float width, height;

    private Resources resources = Renderer.resources;
    private Camera camera;

    private com.ghs.mazegame.game.objects.Tile[][] map;

    public World(Camera camera, float x, float y, int width, int height) {
        this.camera = camera;
        map = new com.ghs.mazegame.game.objects.Tile[width][height];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if(i == 0 || j== 0 || i == map.length - 1 || j == map[0].length - 1)
                    map[i][j] = new com.ghs.mazegame.game.objects.Tile(camera, new Texture(resources, R.drawable.brick_wall), new Shader(resources, R.raw.vert, R.raw.frag), i * SCALE, j  * SCALE, SCALE, SCALE);
                else
                    map[i][j] = new com.ghs.mazegame.game.objects.Tile(camera, new Texture(resources, R.drawable.stone_floor), new Shader(resources, R.raw.vert, R.raw.frag), i * SCALE, j  * SCALE, SCALE, SCALE);
            }
        }
    }

    public void render() {
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                map[i][j].render();
            }
        }
    }
}
