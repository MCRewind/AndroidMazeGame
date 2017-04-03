package com.ghs.mazegame.objects;


import com.ghs.engine.components.Shader;
import com.gnarly.engine.components.Texture;
import com.gnarly.engine.display.Camera;

public class Item extends Tile {

    public Item(Camera camera, Texture texture, Shader shader, float x, float y, float width, float height, boolean solid) {
        super(camera, texture, shader, x, y, width, height, solid, true);
    }

}
