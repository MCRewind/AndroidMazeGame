package com.ghs.mazegame.game.objects;

import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.math.Vector3f;

import static com.ghs.mazegame.engine.display.Surface.camX;
import static com.ghs.mazegame.engine.display.Surface.camY;

public class DPad extends UIObject {

    private float x, y, width, height;
    private int selected = 0;
    private Shader shader;
    private UIImage[] directions;
    private UIImage center;
    private Vector3f dir;

    public DPad(Camera camera, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dir = new Vector3f();
        shader = new Shader(R.raw.defaultvs, R.raw.dpadfs);
        directions = new UIImage[8];
        directions[0] = new UIImage(camera, new Texture(R.drawable.d_upleft),    shader, x,                    y,                     width / 3.0f, height / 3.0f);
        directions[1] = new UIImage(camera, new Texture(R.drawable.d_up),        shader, x + width / 3.0f,     y,                     width / 3.0f, height / 3.0f);
        directions[2] = new UIImage(camera, new Texture(R.drawable.d_upright),   shader, x + 2 * width / 3.0f, y,                     width / 3.0f, height / 3.0f);
        directions[3] = new UIImage(camera, new Texture(R.drawable.d_left),      shader, x,                    y + height / 3.0f,     width / 3.0f, height / 3.0f);
        directions[4] = new UIImage(camera, new Texture(R.drawable.d_right),     shader, x + 2 * width / 3.0f, y + height / 3.0f,     width / 3.0f, height / 3.0f);
        directions[5] = new UIImage(camera, new Texture(R.drawable.d_downleft),  shader, x,                    y + 2 * height / 3.0f, width / 3.0f, height / 3.0f);
        directions[6] = new UIImage(camera, new Texture(R.drawable.d_down),      shader, x + width / 3.0f,     y + 2 * height / 3.0f, width / 3.0f, height / 3.0f);
        directions[7] = new UIImage(camera, new Texture(R.drawable.d_downright), shader, x + 2 * width / 3.0f, y + 2 * height / 3.0f, width / 3.0f, height / 3.0f);
        center        = new UIImage(camera, new Texture(R.drawable.d_center),    shader, x + width / 3.0f,     y + height / 3.0f,     width / 3.0f, height / 3.0f);
    }

    public void update() {
        selected = -1;
        for (int i = 0; i < directions.length; i++)
            if (directions[i].contains(camX, camY))
                selected = i + 1;
        if(center.contains(camX, camY))
            selected = 0;
        if (selected != -1) {
            float vecX = camX - (x + width / 2);
            float vecY = camY - (y + height / 2);
            float length = (float) Math.sqrt(vecX * vecX + vecY * vecY);
            dir.x = vecX / length;
            dir.y = vecY / length;
            if(selected == 0) {
                dir.x *= 0.5f;
                dir.y *= 0.5f;
            }
        }
        else {
            dir.x = 0;
            dir.y = 0;
        }
    }

    public void render() {
        for (int i = 0; i < directions.length; i++) {
            if ( selected - 1 == i)
                shader.setUniform4f("opacity", 0.3f, 0.4f, 0.7f, 1.0f);
            else
                shader.setUniform4f("opacity", 0.4f, 0.4f, 0.4f, 0.65f);
            directions[i].render();
        }
        shader.setUniform4f("opacity", 0.4f, 0.4f, 0.4f, 0.65f);
        center.render();
    }

    public Vector3f getDir() {
        return dir;
    }
}
