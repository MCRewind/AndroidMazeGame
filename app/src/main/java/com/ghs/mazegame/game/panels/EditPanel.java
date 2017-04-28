package com.ghs.mazegame.game.panels;

import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.objects.Map;

import static com.ghs.mazegame.engine.display.Surface.camX;
import static com.ghs.mazegame.engine.display.Surface.camY;
import static com.ghs.mazegame.game.Renderer.SCALE;

public class EditPanel implements Panel {

    private Map map;

    private Camera camera;

    private int curType;

    public EditPanel(Camera camera) {
        this.camera = camera;
        this.map = new Map(camera, 20, 20);
        curType = Map.TYPE_WALL;
        for (int i = 0; i < map.getWidth(); ++i)
            for (int j = 0; j < map.getHeight(); ++j)
                map.setTile(Map.TYPE_FLOOR, i, j);
    }

    public void update() {
        draw();
    }

    private void draw() {
        int x = (int) ((camX - camera.getX()) / SCALE);
        int y = (int) ((camY - camera.getY()) / SCALE);
        map.setTile(curType, x, y);
    }

    public void render() {
        map.render();
    }
}
