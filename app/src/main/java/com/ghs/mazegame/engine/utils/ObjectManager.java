package com.ghs.mazegame.engine.utils;

import com.ghs.mazegame.game.interfaces.GameObject;

/**
 * Created by cmeyer3887 on 4/24/2017.
 */

public class ObjectManager {

    private ObjectGroup gameObjects = new ObjectGroup("test", true, true);

    //only update and render active groups of objects
    //setable individually

    public void update() {
        gameObjects.update();
    }

    public void render() {
        gameObjects.render();
    }
}
