package com.mygdx.game;

import java.util.*;

/**
 * manikoske on 1. 5. 2017.
 */
public class CollisionDetection {

    private IGameManager gameManager;

    private Map<Box, List<Actor>> boxes;

    public CollisionDetection(IGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void executeBroadPhase(List<Actor> actors) {
        boxes = new HashMap<>();

        int boxDimensions = gameManager.getSettings().getInitialBroadPhaseBoxDimensions();
        int aX;
        int aY;
        int bX;
        int bY;
        for (Actor actor : actors) {
            aX = Math.max(0, Math.round(actor.getX() - actor.getRadius()));
            aY = Math.max(0, Math.round(actor.getY() - actor.getRadius()));
            bX = Math.min(gameManager.getSettings().getWidth(), Math.round(actor.getX() + actor.getRadius()));
            bY = Math.min(gameManager.getSettings().getHeight(), Math.round(actor.getY() + actor.getRadius()));
            for (int i = aX / boxDimensions; i <= bX / boxDimensions; i++) {
                for (int j = aY / boxDimensions; j <= bY / boxDimensions; j++) {
                    Box box = new Box(i * boxDimensions, j * boxDimensions);
                    boxes.compute(box, (key, value) -> {
                        if (value == null) {
                            value = new ArrayList<>();
                            value.add(actor);
                            return value;
                        } else {
                            value.add(actor);
                            return value;
                        }
                    });
                }
            }
        }
    }

    public static class Box {

        private int x;
        private int y;

        public Box(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Box box = (Box) o;

            if (x != box.x) return false;
            return y == box.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

}
