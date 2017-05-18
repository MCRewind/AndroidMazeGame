package com.ghs.mazegame.engine.math;

public class Vector3f {

    public float x = 0, y = 0, z = 0;

    public Vector3f() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public Vector3f add(Vector3f vector) {
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }

    public Vector3f sub(Vector3f vector) {
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return this;
    }

    public Vector3f add(Vector3f vector, Vector3f target) {
        target.x = x + vector.x;
        target.y = y + vector.y;
        target.z = z + vector.z;
        return target;
    }

    public Vector3f sub(Vector3f vector, Vector3f target) {
        target.x = x - vector.x;
        target.y = y - vector.y;
        target.z = z - vector.z;
        return target;
    }

    public Vector3f add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3f sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3f add(float x, float y, float z, Vector3f target) {
        target.x = this.x + x;
        target.y = this.y + y;
        target.z = this.z + z;
        return target;
    }

    public Vector3f sub(float x, float y, float z, Vector3f target) {
        target.x = this.x - x;
        target.y = this.y - y;
        target.z = this.z - z;
        return target;
    }

    public Vector3f mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    public Vector3f mul(float scalar, Vector3f target) {
        target.x = x * scalar;
        target.y = y * scalar;
        target.z = z * scalar;
        return target;
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }
}
