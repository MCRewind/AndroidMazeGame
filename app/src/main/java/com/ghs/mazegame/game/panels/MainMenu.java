package com.ghs.mazegame.game.panels;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.interfaces.Panel;
import com.ghs.mazegame.game.objects.Button;
import com.ghs.mazegame.game.objects.Rectangle;
import com.ghs.mazegame.game.objects.Thumbnail;

import java.util.ArrayList;

import static com.ghs.mazegame.game.Main.SCALE;
import static com.ghs.mazegame.game.Main.STATE_EDIT;
import static com.ghs.mazegame.game.Main.cameraHeight;
import static com.ghs.mazegame.game.Main.cameraWidth;
import static com.ghs.mazegame.game.Main.maps;
import static com.ghs.mazegame.game.objects.Thumbnail.STATE_RELEASED;

public class MainMenu implements Panel {

    private final int HOR_VIEWS = 2;

    private final float SPEED = 10;

    private int state = -1;

    private int x, y, ax, ay;

    private int map = -1;

    private Camera camera;

    private Button[] dir;
    private Button[] actions;
    private Rectangle[] colors;

    private ArrayList<Thumbnail> thumbnails;
    private Button newMap;

    public MainMenu(Camera camera) {
        this.camera = camera;
        x = 0;
        y = 0;
        ay = 0;
        ax = 0;
        actions = new Button[HOR_VIEWS];
        actions[0] = new Button(camera, new Texture(R.drawable.menu_play_button), new Texture(R.drawable.menu_play_button_pressed), cameraWidth / 4, cameraHeight / 4f, 0.2f, cameraWidth / 2f, cameraHeight / 2f, false);
        actions[1] = new Button(camera, new Texture(R.drawable.menu_edit_button), new Texture(R.drawable.menu_edit_button_pressed), cameraWidth + cameraWidth / 4, cameraHeight / 4f, 0.2f, cameraWidth / 2f, cameraHeight / 2f, false);
        colors = new Rectangle[HOR_VIEWS];
        colors[0] = new Rectangle(camera, 0, 0, 0.5f, camera.getWidth(), camera.getHeight(), 0.6f, 0, 0.15f, 1);
        colors[1] = new Rectangle(camera, cameraWidth, 0, 0.5f, camera.getWidth(), camera.getHeight(), 0, 0.5f, 0.5f, 1);
        dir = new Button[2];
        dir[0] = new Button(camera, new Texture(R.drawable.left_menu_arrow), new Texture(R.drawable.left_menu_arrow_pressed), 0, 0, 0.1f, SCALE * 2, cameraHeight, false);
        dir[1] = new Button(camera, new Texture(R.drawable.right_menu_arrow), new Texture(R.drawable.right_menu_arrow_pressed), camera.getWidth() - SCALE * 2, 0, 0.1f, SCALE * 2, cameraHeight, false);
        thumbnails = new ArrayList<>();
        loadThumbnails();
    }

    public void update() {
        if (y == 0) {
            dir[0].update();
            dir[1].update();
            if (dir[0].getState() == Button.STATE_PRESSED && x > 0) {
                ax = x;
                --x;
            } else if (dir[1].getState() == Button.STATE_PRESSED && x < HOR_VIEWS - 1) {
                ax = x;
                ++x;
            }
            if(camera.getX() / cameraWidth > x) {
                camera.translate(-cameraWidth / SPEED, 0, 0);
                if(camera.getX() / cameraWidth < x)
                    camera.setPosition(x * cameraWidth, camera.getY(), 0);
            }
            else if(camera.getX() / cameraWidth < x) {
                camera.translate(cameraWidth / SPEED, 0, 0);
                if(camera.getX() / cameraWidth > x)
                    camera.setPosition(x * cameraWidth, camera.getY(), 0);
            }
            else
                ax = -1;
            if(ax == -1) {
                actions[x].update();
                if (actions[1].getState() == Button.STATE_RELEASED) {
                    y = 1;
                    ay = 0;
                    for (int i = 0; i < thumbnails.size(); i++)
                        thumbnails.get(i).setOffset(cameraWidth * x, 0);
                    newMap.setOffset(cameraWidth * x, 0);
                }
            }
            dir[0].setX(camera.getX());
            dir[1].setX(camera.getX() + cameraWidth - dir[1].getWidth());
        }
        else if(y == 1) {
            colors[x].setPosition(camera.getX(), camera.getY());
            if(camera.getY() < cameraHeight) {
                camera.translate(0, cameraHeight / SPEED, 0);
                if(camera.getY() > cameraHeight)
                    camera.setPosition(camera.getX(), cameraHeight, 0);
            }
            else
                ay = -1;
            for (int i = 0; i < thumbnails.size(); i++) {
                thumbnails.get(i).update();
                if(thumbnails.get(i).getState() == STATE_RELEASED) {
                    state = STATE_EDIT;
                    map = i;
                }
            }
            newMap.update();
            if(newMap.getState() == STATE_RELEASED) {
                state = STATE_EDIT;
                map = thumbnails.size();
            }
        }
    }

    public void render() {
        if (y == 0) {
            colors[x].render();
            actions[x].render();
            if (ax != -1) {
                colors[ax].render();
                actions[ax].render();
            }
            dir[0].render();
            dir[1].render();
        }
        else if(y == 1) {
            colors[x].render();
            if(ay == 0) {
                actions[x].render();
                dir[0].render();
                dir[1].render();
            }
            for (int i = 0; i < thumbnails.size(); i++)
                thumbnails.get(i).render();
            newMap.render();
        }
    }

    public int checkState() {
        int temp = state;
        state = -1;
        return temp;
    }

    public void setActive() {
        loadThumbnails();
    }

    private void loadThumbnails() {
        thumbnails.clear();
        for (int i = 0; i < maps.size(); i++)
            thumbnails.add(new Thumbnail(camera, maps.get(i), ((i % 3) * ((cameraWidth - SCALE) / 3f)) + (SCALE * (i % 3 + 1) / 4f),
                ((i / 3) * ((cameraWidth - SCALE) * 2 / 9f)) + (SCALE * (i / 3 + 1) / 4f) + cameraHeight + SCALE, 0.1f));
        int i = maps.size();
        newMap = new Button(camera, new Texture(R.drawable.new_map_button), new Texture(R.drawable.new_map_button_pressed),
            ((i % 3) * ((cameraWidth - SCALE) / 3f)) + (SCALE * (i % 3 + 1) / 4f),
            ((i / 3) * ((cameraWidth - SCALE) * 2 / 9f)) + (SCALE * (i / 3 + 1) / 4f) + cameraHeight + SCALE, 0.1f, (cameraWidth - SCALE) / 3f, (cameraWidth - SCALE) * 2 / 9f, false);
    }

    public int getMap() {
        return map;
    }
}