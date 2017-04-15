package com.mygdx.game;

import java.util.Deque;
import java.util.LinkedList;

/**
 * manikoske on 9. 4. 2017.
 */
public class Actor {

    private static final int HISTORY_STACK_SIZE = 5;

    private Deque<Integer> previousPositions;
    private ActorType actorType;
    private int currentPosition;

    public Actor(int currentPosition, ActorType actorType) {
        this.currentPosition = currentPosition;
        this.previousPositions = new LinkedList<>();
        this.actorType = actorType;
    }

    public void move() {
        int nextPosition = currentPosition;
        if (previousPositions.size() == HISTORY_STACK_SIZE) {
            previousPositions.removeLast();
            previousPositions.add(currentPosition);
        } else {
            previousPositions.add(currentPosition);
        }
        currentPosition = nextPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getPreviousPosition() {
        if (previousPositions.size() > 0) {
            return previousPositions.peekLast();
        } else {
            return currentPosition;
        }
    }

    public ActorType getActorType() {
        return actorType;
    }
}
