package com.ghs.mazegame.engine.utils;

import com.ghs.mazegame.game.interfaces.GameObject;

import java.util.ArrayList;

/**
 * Created by cmeyer3887 on 5/4/2017.
 */

public class ObjectGroup {

    private boolean updateable, renderable;

    private String name;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    public ObjectGroup(String name, boolean updateable, boolean renderable) {
        this.name = name;
        this.updateable = updateable;
        this.renderable = renderable;
    }

    public void update() {
        if(updateable) {
            for (GameObject object : gameObjects)
                object.update();
        }
    }

    public void render() {
        if(renderable) {
            for (GameObject object : gameObjects)
                object.render();
        }
    }

    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

    public void setRenderable(boolean renderable) {
        this.renderable = renderable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
