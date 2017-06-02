package com.ghs.mazegame.game.panels;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.objects.Image;

import static com.ghs.mazegame.game.Main.cameraHeight;
import static com.ghs.mazegame.game.Main.cameraWidth;

public class NamePanel {

    private int state, nextState;
    private Image background;

    public NamePanel(Camera camera, int nextState) {
        camera.setPosition(0, 0, 0);
        this.nextState = nextState;
        background = new Image(camera, new Texture(R.drawable.menu_level_background), 0, 0, 0.9f, cameraWidth, cameraHeight, true);
    }

    public void update() {

    }

    public void render() {
        background.render();
    }
}
