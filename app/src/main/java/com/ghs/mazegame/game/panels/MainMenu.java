package com.ghs.mazegame.game.panels;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.Main;
import com.ghs.mazegame.game.interfaces.Panel;
import com.ghs.mazegame.game.map.Map;
import com.ghs.mazegame.game.objects.Button;
import com.ghs.mazegame.game.objects.Image;
import com.ghs.mazegame.game.objects.Rectangle;
import com.ghs.mazegame.game.objects.Thumbnail;

import java.io.File;
import java.util.ArrayList;

import static com.ghs.mazegame.game.Main.SCALE;
import static com.ghs.mazegame.game.Main.cameraHeight;
import static com.ghs.mazegame.game.Main.cameraWidth;
import static com.ghs.mazegame.game.Main.context;
import static com.ghs.mazegame.game.Main.maps;

public class MainMenu implements Panel {

    private final int HOR_VIEWS = 2, VERT_VIEWS = 2;

    private final float SPEED = 10;

    private int state = -1;

    private int x, y, ax, xy;

    private Camera camera;

    private Button[] dir;
    private Button[] actions;
    private Rectangle[] colors;

    private ArrayList<Thumbnail> thumbnails;
    private Image image;

    public MainMenu(Camera camera) {
        this.camera = camera;
        actions = new Button[HOR_VIEWS];
        actions[0] = new Button(camera, new Texture(R.drawable.menu_play_button), new Texture(R.drawable.menu_play_button_pressed), cameraWidth / 4, cameraHeight / 4f, cameraWidth / 2f, cameraHeight / 2f, 0.1f, false);
        actions[1] = new Button(camera, new Texture(R.drawable.menu_edit_button), new Texture(R.drawable.menu_edit_button_pressed), cameraWidth + cameraWidth / 4, cameraHeight / 4f, cameraWidth / 2f, cameraHeight / 2f, 0.1f, false);
        colors = new Rectangle[HOR_VIEWS];
        colors[0] = new Rectangle(camera, 0, 0, camera.getWidth(), camera.getHeight(), 0.6f, 0, 0.15f, 1);
        colors[1] = new Rectangle(camera, cameraWidth, 0, camera.getWidth(), camera.getHeight(), 0, 0.5f, 0.5f, 1);
        dir = new Button[2];
        dir[0] = new Button(camera, new Texture(R.drawable.left_menu_arrow), new Texture(R.drawable.left_menu_arrow_pressed), 0, 0, SCALE * 2, cameraHeight, 0.1f, true);
        dir[1] = new Button(camera, new Texture(R.drawable.right_menu_arrow), new Texture(R.drawable.right_menu_arrow_pressed), camera.getWidth() - SCALE * 2, 0, SCALE * 2, cameraHeight, 0.1f, true);
        thumbnails = new ArrayList<>();
        loadThumbnails();
    }

    public void update() {
        dir[0].update();
        dir[1].update();
        if (dir[0].getState() == Button.STATE_PRESSED && x > 0) {
            ax = x;
            --x;
        }
        else if(dir[1].getState() == Button.STATE_PRESSED && x < HOR_VIEWS - 1) {
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
            if (actions[1].getState() == Button.STATE_RELEASED)
                state = Main.STATE_EDIT;
        }
    }

    public void render() {
        colors[x].render();
        actions[x].render();
        if(ax != -1) {
            colors[ax].render();
            actions[ax].render();
        }
        for (int i = 0; i < thumbnails.size(); i++)
            thumbnails.get(i).render();
        dir[0].render();
        dir[1].render();
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
            thumbnails.add(new Thumbnail(camera, maps.get(i), ((i % 3) * ((cameraWidth - SCALE) / 3f)) + (SCALE * (i % 3 + 1) / 4f), ((i / 3) * ((cameraWidth - SCALE) / 6f)) + (SCALE * (i / 3f + 1) / 4f), 0.1f));
        int i = maps.size();
        thumbnails.add(new Thumbnail(camera, ((i % 3) * ((cameraWidth - SCALE) / 3f)) + (SCALE * (i % 3 + 1) / 4f), ((i / 3) * ((cameraWidth - SCALE) * 2 / 3f)) + (SCALE * (i / 3f + 1) / 4f), 0.1f));
    }
}
