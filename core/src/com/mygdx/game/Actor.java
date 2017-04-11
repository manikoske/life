package com.mygdx.game;

import java.util.Deque;
import java.util.LinkedList;

/**
 * manikoske on 9. 4. 2017.
 */
public class Actor {

    private static final int HISTORY_STACK_SIZE = 5;

    private Deque<Coordinates> pathHistory;
    private ActorType actorType;
    private Coordinates currentPosition;

    public Actor(Coordinates coordinates, ActorType actorType) {
        this.currentPosition = coordinates;
        this.pathHistory = new LinkedList<>();
        this.actorType = actorType;
    }

    public void move() {
        Coordinates nextPosition = new Coordinates(currentPosition.getX(), currentPosition.getY());
//        Coordinates nextPosition = new Coordinates(currentPosition.getX() + 1, currentPosition.getY() + 1);
        if (pathHistory.size() == HISTORY_STACK_SIZE) {
            pathHistory.removeLast();
            pathHistory.add(currentPosition);
        } else {
            pathHistory.add(currentPosition);
        }
        currentPosition = nextPosition;
    }

    public Coordinates getCurrentPosition() {
        return currentPosition;
    }

    public Coordinates getPreviousPosition() {
        if (pathHistory.size() > 0) {
            return pathHistory.peekLast();
        } else {
            return currentPosition;
        }
    }

    public ActorType getActorType() {
        return actorType;
    }
}
