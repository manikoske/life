package com.mygdx.game;

import java.util.Deque;
import java.util.LinkedList;

/**
 * manikoske on 9. 4. 2017.
 */
public class Actor {

    private static final int HISTORY_STACK_SIZE = 5;

    private Deque<Coordinates> path;
    private ActorType actorType;

    public Actor(Coordinates coordinates, ActorType actorType) {
        this.path = new LinkedList<>();
        this.path.add(coordinates);
        this.actorType = actorType;
    }

    public void move() {
        Coordinates currentCoordinates = path.peek();
        Coordinates futureCoordinates = new Coordinates(currentCoordinates.getX() + 1, currentCoordinates.getY() + 1);

        if (path.size() == HISTORY_STACK_SIZE) {
            path.removeLast();
            path.add(futureCoordinates);
        } else {
            path.add(futureCoordinates);
        }
    }

    public Coordinates getCurrentPosition() {
        return path.peek();
    }

    public Coordinates getPreviousPosition() {
        if (path.size() > 1) {
            return path.iterator().
        }
        return path.peek();
    }

    public ActorType getActorType() {
        return actorType;
    }
}
