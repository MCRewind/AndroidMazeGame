package com.ghs.mazegame.game.objects;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.game.interfaces.GameObject;

import static com.ghs.mazegame.engine.display.Surface.touchX;
import static com.ghs.mazegame.engine.display.Surface.touchY;

public class DPad implements GameObject {

    private float x, y, width, height;
    private int selected = 0;
    private Shader shader;
    private Image[] dpad;
    private Vector3f dir;
    private float scale = 3;

    public DPad(Camera camera, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dir = new Vector3f();
        shader = new Shader(R.raw.defaultvs, R.raw.dpadfs);
        dpad = new Image[9];
        dpad[0] = new Image(camera, new Texture(R.drawable.d_upleft),    shader, x,                     y,                      0.0f, width / scale, height / scale, true);
        dpad[1] = new Image(camera, new Texture(R.drawable.d_up),        shader, x + width / scale,     y,                      0.0f, width / scale, height / scale, true);
        dpad[2] = new Image(camera, new Texture(R.drawable.d_upright),   shader, x + 2 * width / scale, y,                      0.0f, width / scale, height / scale, true);
        dpad[3] = new Image(camera, new Texture(R.drawable.d_left),      shader, x,                     y + height / scale,     0.0f, width / scale, height / scale, true);
        dpad[4] = new Image(camera, new Texture(R.drawable.d_center),    shader, x + width / scale,     y + height / scale,     0.0f, width / scale, height / scale, true);
        dpad[5] = new Image(camera, new Texture(R.drawable.d_right),     shader, x + 2 * width / scale, y + height / scale,     0.0f, width / scale, height / scale, true);
        dpad[6] = new Image(camera, new Texture(R.drawable.d_downleft),  shader, x,                     y + 2 * height / scale, 0.0f, width / scale, height / scale, true);
        dpad[7] = new Image(camera, new Texture(R.drawable.d_down),      shader, x + width / scale,     y + 2 * height / scale, 0.0f, width / scale, height / scale, true);
        dpad[8] = new Image(camera, new Texture(R.drawable.d_downright), shader, x + 2 * width / scale, y + 2 * height / scale, 0.0f, width / scale, height / scale, true);
    }

    public void update() {
        selected = -1;
        for (int i = 0; i < dpad.length; i++)
            if (dpad[i].contains(touchX, touchY))
                selected = i;
        if (selected != -1) {
            float vecX = touchX - (x + width / 2);
            float vecY = touchY - (y + height / 2);
            float length = (float) Math.sqrt(vecX * vecX + vecY * vecY);
            dir.x = vecX / length;
            dir.y = vecY / length;
            if(length <= width / 6.0f) {
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
        for (int i = 0; i < dpad.length; i++) {
            shader.enable();
            if (selected == i)
                shader.setUniform4f("color", 0.3f, 0.5f, 0.7f, 0.6f);
            else
                shader.setUniform4f("color", 0.4f, 0.4f, 0.4f, 0.3f);
            dpad[i].render();
        }
    }

    public boolean contains(float x, float y) {
        if ((x >= this.x && x < this.x + this.width) && (y >= this.y && y < this.y + this.height)) {
            return true;
        } else {
            return false;
        }
    }

    public Vector3f getDir() {
        return dir;
    }
}
