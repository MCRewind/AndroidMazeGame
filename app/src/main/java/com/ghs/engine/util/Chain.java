package com.ghs.engine.util;

import java.util.ArrayList;

import com.ghs.mazegame.objects.Item;

public class Chain {

    Hitbox target;

    int elementSpacing;

    public Chain(Hitbox target, int elementSpacing) {
        this.target = target;
        this.elementSpacing = elementSpacing;
    }

    ArrayList<Item> elements = new ArrayList<>();

    public ArrayList<Item> getElements() {
        return elements;
    }

    public int convertSize(int size, int pos) {
        return size/pos;
    }

    public void update() {

    }

    public void posCalc() {

    }

}
