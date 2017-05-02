package com.ghs.mazegame.engine.utils;

import com.ghs.mazegame.game.interfaces.GameObject;
import com.ghs.mazegame.game.map.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cmeyer3887 on 4/24/2017.
 */

public class ObjectManager {

    HashMap<String, ArrayList<GameObject>> objects = new HashMap<String, ArrayList<GameObject>>();

    //only update and render active groups of objects
    //setable individually

    public void update() {
      
    }

    public void render() {

    }

    public GameObject get(String group, int index) {
        return objects.get(group).get(index);
    }

    public void set(String group, int index, GameObject child){
        objects.get(group).set(index, child);
    }

}
