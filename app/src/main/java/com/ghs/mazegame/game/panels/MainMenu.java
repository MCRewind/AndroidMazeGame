package com.ghs.mazegame.game.panels;

import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.game.interfaces.Panel;
import com.ghs.mazegame.game.objects.Button;
import com.ghs.mazegame.game.objects.Image;
import com.ghs.mazegame.game.objects.Thumbnail;

import java.util.ArrayList;

import static com.ghs.mazegame.engine.display.Surface.swipe;
import static com.ghs.mazegame.engine.display.Surface.touchX;
import static com.ghs.mazegame.engine.display.Surface.touchY;
import static com.ghs.mazegame.game.Main.SCALE;
import static com.ghs.mazegame.game.Main.STATE_EDIT;
import static com.ghs.mazegame.game.Main.cameraHeight;
import static com.ghs.mazegame.game.Main.cameraWidth;
import static com.ghs.mazegame.game.Main.maps;
import static com.ghs.mazegame.game.objects.Thumbnail.STATE_RELEASED;
import static com.ghs.mazegame.game.objects.Thumbnail.STATE_UNPRESSED;

public class MainMenu implements Panel {

    private final int HOR_VIEWS = 2;

    private final float SPEED = 10;

    private int state = -1;

    private int x, y, ax, ay;

    private int map = -1;

    private Camera camera;

    private Button[] dir;
    private Button[] actions;
    private Image background;

    private ArrayList<Thumbnail> thumbnails;
    private Button newMap;

    private Vector3f lastTouch;
    private Vector3f scroll;
    private boolean scrolled;

    public MainMenu(Camera camera) {
        this.camera = camera;
        x = 0;
        y = 0;
        ay = 0;
        ax = 0;
        background = new Image(camera, new Texture(R.drawable.menu_background), 0, 0, 0.9f, cameraWidth, cameraHeight);
        actions = new Button[HOR_VIEWS];
        actions[0] = new Button(camera, new Texture(R.drawable.menu_play_button), new Texture(R.drawable.menu_play_button_pressed), cameraWidth / 4, cameraHeight / 4f, 0.2f, cameraWidth / 2f, cameraHeight / 2f, false);
        actions[1] = new Button(camera, new Texture(R.drawable.menu_edit_button), new Texture(R.drawable.menu_edit_button_pressed), cameraWidth + ((cameraWidth - 48) / 2f), 57, 0.2f, 48, 20, false);
        dir = new Button[2];
        dir[0] = new Button(camera, new Texture(R.drawable.left_menu_arrow), new Texture(R.drawable.left_menu_arrow_pressed), 0, 0, 0.1f, SCALE * 2, cameraHeight, false);
        dir[1] = new Button(camera, new Texture(R.drawable.right_menu_arrow), new Texture(R.drawable.right_menu_arrow_pressed), camera.getWidth() - SCALE * 2, 0, 0.1f, SCALE * 2, cameraHeight, false);
        thumbnails = new ArrayList<>();
        lastTouch = new Vector3f(-1, -1, -1);
        scroll = new Vector3f();
        scrolled = false;
        loadThumbnails();
    }

    public void update() {
        if (y == 0) {
            dir[0].update();
            dir[1].update();
            if (dir[0].getState() == Button.STATE_RELEASED && x > 0) {
                ax = x;
                --x;
            } else if (dir[1].getState() == Button.STATE_RELEASED && x < HOR_VIEWS - 1) {
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
                if (actions[0].getState() == Button.STATE_RELEASED) {
                    y = 1;
                    ay = 0;
                }
            }
            dir[0].setX(camera.getX());
            dir[1].setX(camera.getX() + cameraWidth - dir[1].getWidth());
        }
        else if(y == 1) {
            if (camera.getY() < cameraHeight && ay != -1) {
                camera.translate(0, cameraHeight / SPEED, 0);
                if (camera.getY() > cameraHeight)
                    camera.setPosition(camera.getX(), cameraHeight, 0);
            } else
                ay = -1;
            if (!scrolled) {
                for (int i = 0; i < thumbnails.size(); i++) {
                    thumbnails.get(i).update();
                    if (thumbnails.get(i).getState() == STATE_RELEASED) {
                        state = STATE_EDIT;
                        map = i;
                    }
                }
                newMap.update();
                if (newMap.getState() == STATE_RELEASED) {
                    state = STATE_EDIT;
                    map = thumbnails.size();
                }
            }
            else {
                for (int i = 0; i < thumbnails.size(); i++) {
                    thumbnails.get(i).setState(STATE_UNPRESSED);
                }
                newMap.setState(STATE_UNPRESSED);
            }
            if(lastTouch.y != -1 && touchY != -1)
                scroll.y = lastTouch.y - touchY;
            else if(swipe.y != 0)
                scroll.y = swipe.y;
            camera.translate(scroll);
            if(camera.getY() + cameraHeight > newMap.getY() + newMap.getHeight() + 11)
                camera.setPosition(camera.getX(), newMap.getY() + newMap.getHeight() + 11 - cameraHeight, 0);
            if(camera.getY() < cameraHeight)
                camera.setPosition(camera.getX(), cameraHeight, 0);
            if(touchY == -1 && lastTouch.y != -1 && scroll.y < 3 && scroll.y > -3)
                scroll.y = 0;
            if(scroll.y >= 1 || scroll.y <= -1)
                scrolled = true;
            else if(scroll.y == 0 && touchY == -1)
                scrolled = false;
            if(scroll.y != 0) {
                scroll.y *= 0.9f;
                if(scroll.y < 0.5f && scroll.y > -0.5f)
                    scroll.y = 0;
            }
            lastTouch.x = touchX;
            lastTouch.y = touchY;
        }
    }

    public void render() {
        background.render();
        if (y == 0) {
            actions[x].render();
            if (ax != -1)
                actions[ax].render();
            dir[0].render();
            dir[1].render();
        }
        else if(y == 1) {
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
        int i;
        for (i = 0; i < maps.size(); i++)
            thumbnails.add(new Thumbnail(camera, maps.get(i), ((i % 3) * ((cameraWidth - SCALE) / 3f)) + (SCALE * (i % 3 + 1) / 4f),
                    ((i / 3) * ((cameraWidth - SCALE) * 2 / 9f + 6)) + (SCALE * (i / 3 + 1) / 4f) + cameraHeight + SCALE, 0.1f));
        newMap = new Button(camera, new Texture(R.drawable.new_map_button), new Texture(R.drawable.new_map_button_pressed),
                ((i % 3) * ((cameraWidth - SCALE) / 3f)) + (SCALE * (i % 3 + 1) / 4f),
                ((i / 3) * ((cameraWidth - SCALE) * 2 / 9f + 6)) + (SCALE * (i / 3 + 1) / 4f) + cameraHeight + SCALE, 0.1f, (cameraWidth - SCALE) / 3f, (cameraWidth - SCALE) * 2 / 9f, false);
    }

    public int getMap() {
        return map;
    }
}