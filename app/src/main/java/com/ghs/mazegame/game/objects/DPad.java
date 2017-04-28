package com.ghs.mazegame.game.objects;

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
    private UIImage[] dpad;
    private Vector3f dir;

    public DPad(Camera camera, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dir = new Vector3f();
        shader = new Shader(R.raw.defaultvs, R.raw.dpadfs);
        dpad = new UIImage[9];
        dpad[0] = new UIImage(camera, new Texture(R.drawable.d_upleft),    shader, x,                    y,                     width / 3.0f, height / 3.0f);
        dpad[1] = new UIImage(camera, new Texture(R.drawable.d_up),        shader, x + width / 3.0f,     y,                     width / 3.0f, height / 3.0f);
        dpad[2] = new UIImage(camera, new Texture(R.drawable.d_upright),   shader, x + 2 * width / 3.0f, y,                     width / 3.0f, height / 3.0f);
        dpad[3] = new UIImage(camera, new Texture(R.drawable.d_left),      shader, x,                    y + height / 3.0f,     width / 3.0f, height / 3.0f);
        dpad[4] = new UIImage(camera, new Texture(R.drawable.d_center),    shader, x + width / 3.0f,     y + height / 3.0f,     width / 3.0f, height / 3.0f);
        dpad[5] = new UIImage(camera, new Texture(R.drawable.d_right),     shader, x + 2 * width / 3.0f, y + height / 3.0f,     width / 3.0f, height / 3.0f);
        dpad[6] = new UIImage(camera, new Texture(R.drawable.d_downleft),  shader, x,                    y + 2 * height / 3.0f, width / 3.0f, height / 3.0f);
        dpad[7] = new UIImage(camera, new Texture(R.drawable.d_down),      shader, x + width / 3.0f,     y + 2 * height / 3.0f, width / 3.0f, height / 3.0f);
        dpad[8] = new UIImage(camera, new Texture(R.drawable.d_downright), shader, x + 2 * width / 3.0f, y + 2 * height / 3.0f, width / 3.0f, height / 3.0f);
    }

    public void update() {
        selected = -1;
        for (int i = 0; i < dpad.length; i++)
            if (dpad[i].contains(camX, camY))
                selected = i;
        if (selected != -1) {
            float vecX = camX - (x + width / 2);
            float vecY = camY - (y + height / 2);
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
            if (selected == i)
                shader.setUniform4f("opacity", 0.3f, 0.45f, 0.7f, 0.3f);
            else
                shader.setUniform4f("opacity", 0.4f, 0.4f, 0.4f, 0.15f);
            dpad[i].render();
        }
    }

    public Vector3f getDir() {
        return dir;
    }
}
