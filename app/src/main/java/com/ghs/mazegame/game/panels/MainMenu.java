package com.ghs.mazegame.game.panels;

import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.interfaces.Panel;
import com.ghs.mazegame.game.map.Map;
import com.ghs.mazegame.game.objects.Button;
import com.ghs.mazegame.game.objects.Rectangle;

import static com.ghs.mazegame.game.Renderer.SCALE;
import static com.ghs.mazegame.game.Renderer.cameraHeight;
import static com.ghs.mazegame.game.Renderer.cameraWidth;

public class MainMenu implements Panel {

    private final int HOR_VIEWS = 2;

    private final float SPEED = 30;

    private int state = -1;

    private int cur = 0, additional = -1;

    private Camera camera;

    private Button[] dir;
    private Map[] backgrounds;
    private Rectangle[] colors;

    public MainMenu(Camera camera) {
        this.camera = camera;
        backgrounds = new Map[2];
        for (int i = 0; i < backgrounds.length; i++)
            backgrounds[i] = new Map(camera, cameraWidth * i, 0, (int) Math.ceil((float) cameraWidth / (float) SCALE), (int) Math.ceil((float) cameraHeight / (float) SCALE) * 2);
        backgrounds[0].setState(Map.STATE_EDIT);
        backgrounds[1].setState(Map.STATE_EDIT);
        int[][] map = new int[][] {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 4, 4, 4, 4, 4, 4},
            {1, 4, 1, 1, 1, 1, 4},
            {1, 4, 4, 4, 4, 4, 4},
            {1, 5, 1, 1, 1, 1, 1},
            {1, 5, 1, 1, 1, 1, 1},
            {1, 5, 5, 5, 5, 5, 1},
            {1, 6, 1, 1, 1, 5, 1},
            {1, 6, 1, 1, 1, 5, 5},
            {1, 6, 1, 1, 1, 1, 5},
            {1, 6, 1, 1, 1, 1, 5},
            {1, 6, 6, 6, 6, 6, 5},
        };
        backgrounds[0].setMap(map, 0, 0);
        colors = new Rectangle[2];
        colors[0] = new Rectangle(camera, 0, 0, camera.getWidth(), camera.getHeight(), 0.6f, 0, 0.15f, 0.5f);
        colors[1] = new Rectangle(camera, cameraWidth, 0, camera.getWidth(), camera.getHeight(), 0, 0.5f, 0.5f, 0.5f);
        dir = new Button[2];
        dir[0] = new Button(camera, new Texture(R.drawable.left_menu_arrow), new Texture(R.drawable.left_menu_arrow_pressed), 0, 0, SCALE * 2, cameraHeight);
        dir[1] = new Button(camera, new Texture(R.drawable.right_menu_arrow), new Texture(R.drawable.right_menu_arrow_pressed), camera.getWidth() - SCALE * 2, 0, SCALE * 2, cameraHeight);
    }

    public void update() {
        dir[0].update();
        dir[1].update();
        if (dir[0].getState() == Button.STATE_PRESSED && cur > 0) {
            additional = cur;
            --cur;
        }
        else if(dir[1].getState() == Button.STATE_PRESSED && cur < HOR_VIEWS - 1) {
            additional = cur;
            ++cur;
        }
        if(camera.getX() / cameraWidth > cur) {
            camera.translate(-cameraWidth / SPEED, 0, 0);
            if(camera.getX() / cameraWidth < cur)
                camera.setPosition(cur * cameraWidth, camera.getY(), 0);
        }
        else if(camera.getX() / cameraWidth < cur) {
            camera.translate(cameraWidth / SPEED, 0, 0);
            if(camera.getX() / cameraWidth > cur)
                camera.setPosition(cur * cameraWidth, camera.getY(), 0);
        }
        else
            additional = -1;
    }

    public void render() {
        backgrounds[cur].render();
        colors[cur].render();
        if(additional != -1) {
            backgrounds[additional].render();
            colors[additional].render();
        }
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
}
