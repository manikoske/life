package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

import java.util.Deque;
import java.util.LinkedList;

/**
 * manikoske on 9. 4. 2017.
 */
public class Actor {

    private float x;
    private float y;
    private float radius;
    private String name;

    public Actor(float x, float y, float radius, String name) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public String getName() {
        return name;
    }



}

