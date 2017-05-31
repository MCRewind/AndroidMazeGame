package com.ghs.mazegame.engine.utils;

import com.ghs.mazegame.game.interfaces.GameObject;

/**
 * Created by cmeyer3887 on 4/24/2017.
 */

public class ObjectManager {

    private ObjectGroup objectGroups = new ObjectGroup("test", true, true);

    //only update and render active groups of objects
    //setable individually

    public void update() {
        objectGroups.update();
    }

    public void render() {
        objectGroups.render();
    }
}
