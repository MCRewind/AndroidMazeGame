package com.ghs.mazegame.game.objects;

import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.display.Surface;

public class DPad {

    private int dir = 0;
    private UIImage[] directions;

    public DPad(Camera camera, float x, float y, float width, float height) {
        directions = new UIImage[8];
        directions[0] = new UIImage(camera, new Texture(R.drawable.d_upleft),    new Shader(R.raw.defaultvs, R.raw.defaultfs), x,                    y + 2 * height / 3.0f, width / 3.0f, height / 3.0f);
        directions[1] = new UIImage(camera, new Texture(R.drawable.d_up),        new Shader(R.raw.defaultvs, R.raw.defaultfs), x + width / 3.0f,     y + height / 3.0f,     width / 3.0f, height / 3.0f);
        directions[2] = new UIImage(camera, new Texture(R.drawable.d_upright),   new Shader(R.raw.defaultvs, R.raw.defaultfs), x + 2 * width / 3.0f, y + 2 * height / 3.0f, width / 3.0f, height / 3.0f);
        directions[3] = new UIImage(camera, new Texture(R.drawable.d_left),      new Shader(R.raw.defaultvs, R.raw.defaultfs), x,                    y,                     width / 3.0f, height / 3.0f);
        directions[4] = new UIImage(camera, new Texture(R.drawable.d_right),     new Shader(R.raw.defaultvs, R.raw.defaultfs), x + 2 * width / 3.0f, y + 2 * height / 3.0f, width / 3.0f, height / 3.0f);
        directions[5] = new UIImage(camera, new Texture(R.drawable.d_downleft),  new Shader(R.raw.defaultvs, R.raw.defaultfs), x,                    y,                     width / 3.0f, height / 3.0f);
        directions[6] = new UIImage(camera, new Texture(R.drawable.d_down),      new Shader(R.raw.defaultvs, R.raw.defaultfs), x + width / 3.0f,     y + height / 3.0f,     width / 3.0f, height / 3.0f);
        directions[7] = new UIImage(camera, new Texture(R.drawable.d_downright), new Shader(R.raw.defaultvs, R.raw.defaultfs), x + 2 * width / 3.0f, y + 2 * height / 3.0f, width / 3.0f, height / 3.0f);
    }

    public void update() {
        float x = Surface.camX;
        float y = Surface.camY;
        for (int i = 0; i < directions.length; i++)
            if(directions[i].contains(x, y))
                dir = i + 1;
    }

    public void render() {
        for (int i = 0; i < directions.length; i++)
            directions[i].render();
    }

    public int getDir() {
        return dir;
    }
}
