package com.mygdx.game;

/**
 * manikoske on 24. 4. 2017.
 */
public class Tile {

    private float x;
    private float y;
    private float z;

    public Tile(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
