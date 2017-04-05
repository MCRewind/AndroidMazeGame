package com.ghs.engine.util;

import com.ghs.engine.math.Vector3f;

public class Hitbox {

    private Vector3f center, halfExtent;

    public Hitbox(float x, float y, float width, float height) {
        center = new Vector3f(x + width / 2, y + height / 2, 0);
        halfExtent = new Vector3f(width / 2, height / 2, 0);
    }

    public Vector3f collisionAdjust(Hitbox hitbox) {
        Vector3f ret = new Vector3f();
        Vector3f distance = hitbox.center.sub(center, new Vector3f());
        distance.x = (float)Math.abs(distance.x);
        distance.y = (float)Math.abs(distance.y);

        distance.sub(halfExtent.add(hitbox.getHalfExtent(), new Vector3f()));
        if (distance.x < 0 && distance.y < 0) {
            Vector3f correction = hitbox.getCenter().sub(center, new Vector3f());
            if(distance.x > distance.y) {
                if(correction.x > 0)
                    ret.x = distance.x;
                else
                    ret.x = -distance.x;
            }
            else {
                if(correction.y > 0)
                    ret.y = distance.y;
                else
                    ret.y = -distance.y;
            }
        }
        return ret;
    }

    public boolean checkCollision(Hitbox hitbox) {
        Vector3f distance = hitbox.center.sub(center, new Vector3f());
        distance.x = (float)Math.abs(distance.x);
        distance.y = (float)Math.abs(distance.y);

        distance.sub(halfExtent.add(hitbox.getHalfExtent(), new Vector3f()));
        return (distance.x < 0 && distance.y < 0);
    }

    public void setPosition(float x, float y) {
        center.x = x + halfExtent.x;
        center.y = y + halfExtent.y;
    }

    public float getX() {
        return center.x - halfExtent.x;
    }

    public float getY() {
        return center.y - halfExtent.y;
    }

    public Vector3f getCenter() {
        return center;
    }

    public Vector3f getHalfExtent() {
        return halfExtent;
    }
}
