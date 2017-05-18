package com.ghs.mazegame.game.panels;

import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.Renderer;
import com.ghs.mazegame.game.interfaces.Panel;
<<<<<<< HEAD
import com.ghs.mazegame.game.map.Map;
=======
>>>>>>> 03c7c07a41064460e358ce7be4b96ddf3387e28b
import com.ghs.mazegame.game.objects.Image;
import com.ghs.mazegame.game.objects.Rectangle;

import static com.ghs.mazegame.game.Renderer.cameraHeight;
import static com.ghs.mazegame.game.Renderer.cameraWidth;

public class SplashPanel implements Panel {

    private int state;

    private float fadeTime, start, duration;

    private Camera camera;
    private Image image;
    private Rectangle fade;

    public SplashPanel(Camera camera, float duration, Texture texture) {
        this.camera = camera;
        this.duration = duration;
        fadeTime = duration / 6f;
        image = new Image(camera, texture, 0, 0, cameraWidth, cameraHeight, 0.6f);
        fade = new Rectangle(camera, 0, 0, cameraWidth, cameraHeight, 0, 0, 0, 1);
        start = System.nanoTime() / 1000000f + 500;
    }

    public void update() {
        float time = System.nanoTime() / 1000000f;
        if(time > start) {
            if (time >= start + duration)
                state = Renderer.STATE_MAIN_MENU;
            else if (time - start < fadeTime)
                fade.setColor(0, 0, 0, 1f - ((time - start) / fadeTime));
            else if (time > start + duration - fadeTime)
                fade.setColor(0, 0, 0, 1f - ((start + duration - time) / fadeTime));
            else
                fade.setColor(0, 0, 0, 0);
        }
    }

<<<<<<< HEAD
    @Override
    public void setActive() {

    }

    public void setActive(Map map) {

    }

=======
>>>>>>> 03c7c07a41064460e358ce7be4b96ddf3387e28b
    public void render() {
        image.render();
        fade.render();
    }

    public int checkState() {
        int temp = state;
        state = -1;
        return temp;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 03c7c07a41064460e358ce7be4b96ddf3387e28b
