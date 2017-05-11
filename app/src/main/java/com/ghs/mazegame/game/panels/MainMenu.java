package com.ghs.mazegame.game.panels;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.interfaces.Panel;
import com.ghs.mazegame.game.map.Map;
import com.ghs.mazegame.game.objects.Button;

import static com.ghs.mazegame.game.Renderer.SCALE;
import static com.ghs.mazegame.game.Renderer.cameraHeight;
import static com.ghs.mazegame.game.Renderer.cameraWidth;

public class MainMenu implements Panel {

    private int state = -1;

    private int cur = 0;

    private Camera camera;

    private Button[] dir;
    private Map[] backgrounds;

    public MainMenu(Camera camera) {
        this.camera = camera;
        backgrounds = new Map[2];
        for (int i = 0; i < backgrounds.length; i++)
            backgrounds[i] = new Map(camera, (int) Math.ceil((float) cameraWidth / (float) SCALE), (int) Math.ceil((float) cameraHeight / (float) SCALE));
        backgrounds[0].setState(Map.STATE_PLAY);
        backgrounds[1].setState(Map.STATE_EDIT);
        int[][] map = new int[][] {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 3, 3, 3, 3, 3, 3},
            {1, 3, 1, 1, 1, 1, 3},
            {1, 3, 3, 3, 3, 3, 3},
            {1, 4, 1, 1, 1, 1, 1},
            {1, 4, 1, 1, 1, 1, 1},
            {1, 4, 4, 4, 4, 4, 1},
            {1, 5, 1, 1, 1, 4, 1},
            {1, 5, 1, 1, 1, 4, 4},
            {1, 5, 1, 1, 1, 1, 4},
            {1, 5, 1, 1, 1, 1, 4},
            {1, 5, 5, 5, 5, 5, 4},
        };
        backgrounds[0].setMap(map, 0, 0);
        dir = new Button[2];
        dir[0] = new Button(camera, new Texture(R.drawable.left_menu_arrow), new Texture(R.drawable.left_menu_arrow_pressed), 0, 0, 16, cameraHeight);
        dir[1] = new Button(camera, new Texture(R.drawable.right_menu_arrow), new Texture(R.drawable.right_menu_arrow_pressed), camera.getWidth() - 16, 0, 16, cameraHeight);
    }

    public void update() {
        dir[0].update();
        dir[1].update();
    }

    public void render() {
        backgrounds[cur].render();
        dir[0].render();
        dir[1].render();
    }

    public int checkState() {
        int temp = state;
        state = -1;
        return temp;
    }

    public void setActive() {
        backgrounds[0].setState(Map.STATE_PLAY);
        backgrounds[1].setState(Map.STATE_EDIT);
    }

    public Map getMap() {
        return new Map(camera, 0, 0);
    }
}
