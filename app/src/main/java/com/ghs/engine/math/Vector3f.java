package com.ghs.engine.math;

/**
 * Created by jwentzel8738 on 4/3/2017.
 */

public final class Vector3f {
    public float x;
    public float y;
    public float z;

    public static final Vector3f ZERO = new Vector3f(0, 0, 0);

    public Vector3f() {
    }

    public Vector3f(float xValue, float yValue, float zValue) {
        set(xValue, yValue, zValue);
    }

    public Vector3f(Vector3f other) {
        set(other);
    }

    public final void add(Vector3f other) {
        x += other.x;
        y += other.y;
        z += other.z;
    }

    public final Vector3f add(Vector3f other, Vector3f dest){
        dest.add(other);
        return dest;
    }

    public final void add(float otherX, float otherY, float otherZ) {
        x += otherX;
        y += otherY;
        z += otherZ;
    }

    public final void sub(Vector3f other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
    }

    public final Vector3f sub(Vector3f other, Vector3f dest){
        dest.sub(other);
        return dest;
    }

    public final void multiply(float magnitude) {
        x *= magnitude;
        y *= magnitude;
        z *= magnitude;
    }

    public final void multiply(Vector3f other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
    }

    public final void divide(float magnitude) {
        if (magnitude != 0.0f) {
            x /= magnitude;
            y /= magnitude;
            z /= magnitude;
        }
    }

    public final void set(Vector3f other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public final void set(float xValue, float yValue, float zValue) {
        x = xValue;
        y = yValue;
        z = zValue;
    }

    public final float dot(Vector3f other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public final float length() {
        return (float) Math.sqrt(length2());
    }

    public final float length2() {
        return (x * x) + (y * y) + (z * z);
    }

    public final float distance2(Vector3f other) {
        float dx = x - other.x;
        float dy = y - other.y;
        float dz = z - other.z;
        return (dx * dx) + (dy * dy) + (dz * dz);
    }

    public final float normalize() {
        final float magnitude = length();

        // TODO: I'm choosing safety over speed here.
        if (magnitude != 0.0f) {
            x /= magnitude;
            y /= magnitude;
            z /= magnitude;
        }

        return magnitude;
    }

    public final void zero() {
        set(0.0f, 0.0f, 0.0f);
    }

}