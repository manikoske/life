package com.mygdx.game;

import java.util.*;
import java.util.stream.Collectors;

/**
 * manikoske on 1. 5. 2017.
 */
public class CollisionDetection {

    private IGameManager gameManager;

    public CollisionDetection(IGameManager gameManager) {
        this.gameManager = gameManager;
    }

    private List<List<Actor>> executeBroadPhase(List<Actor> actors) {
        Map<Box, List<Actor>> result = new HashMap<>();

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
                    result.compute(box, (key, value) -> {
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
        return result.values().stream().collect(Collectors.toList());
    }

    public List<PriorityQueue<Actor>> getCollisions(List<Actor> actors) {
        List<List<Actor>> boxes = executeBroadPhase(actors);
        List<PriorityQueue<Actor>> result = new LinkedList<>();
        for (List<Actor> box : boxes) {

        }
        return result;
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
