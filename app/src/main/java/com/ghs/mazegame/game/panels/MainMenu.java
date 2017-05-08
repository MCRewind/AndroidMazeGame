package com.ghs.mazegame.game.panels;

import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.interfaces.Panel;
import com.ghs.mazegame.game.map.Map;
import com.ghs.mazegame.game.objects.Button;

import static com.ghs.mazegame.game.Renderer.SCALE;

public class MainMenu implements Panel {

    private int state;

    private Camera camera;

    private Button[] dir;
    private Map background;

    public MainMenu(Camera camera) {
        this.camera = camera;
        background = new Map(camera, camera.getWidth() * 2 / SCALE, camera.getWidth() * 2 / SCALE);

    }

    public void update() {

    }

    public void render() {
        background.render();
    }

    public int checkState() {
        int temp = state;
        state = -1;
        return temp;
    }

    public void setActive() {
        background.setState(Map.STATE_EDIT);
    }

    public void setActive(Map map) {}
}
