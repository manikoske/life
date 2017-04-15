package com.mygdx.game;

import java.util.*;

/**
 * manikoske on 11. 4. 2017.
 */
public class GameState {

    private int width;
    private int height;

    private GameTile[][] tiles;
    private List<Actor> actors;

    public GameState(int width, int height) {
        this.width = width;
        this.height = height;
        initialize();
    }

    private void initialize() {
        createBackground();
        createActors();
    }

    private void createActors() {
        actors = new LinkedList<>();
        actors.add(new Actor(0, ActorType.HUMAN));
    }

    private void createBackground() {
        tiles = new GameTile[width][height];

        float[][] elevationNoise =
                PerlinNoiseGenerator.generatePerlinNoise(width, height, 7);
        float[][] humidityNoise =
                PerlinNoiseGenerator.generatePerlinNoise(width, height, 6);
        float[][] temperatureNoise =
                PerlinNoiseGenerator.generatePerlinNoise(width, height, 5);

        BackgroundType nearest;
        double minDistance;
        double currentDistance;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nearest = BackgroundType.GRASSLAND1;
                minDistance = 100.0;
                for (BackgroundType backgroundType : BackgroundType.values()) {
                    currentDistance =
                            backgroundType.getDistance(elevationNoise[x][y], humidityNoise[x][y], temperatureNoise[x][y]);
                    if (currentDistance < minDistance) {
                        minDistance = currentDistance;
                        nearest = backgroundType;
                    }
                }
                tiles[x][y] = new GameTile(nearest);
            }
        }
        createAreas();
    }

    public void createAreas() {
        Deque<Integer> allToProcess = new ArrayDeque<>();
        allToProcess.push(0);
        Deque<Set<Integer>> result = new LinkedList<>();
        divideAreas(new HashSet<>(), result, new ArrayDeque<>(), allToProcess);
    }

    private void divideAreas(Set<Integer> visited,
                             Deque<Set<Integer>> result,
                             Deque<Integer> sameToProcess,
                             Deque<Integer> allToProcess) {

        while (!sameToProcess.isEmpty() || !allToProcess.isEmpty()) {
            int centerPosition;
            if (!sameToProcess.isEmpty()) {
                centerPosition = sameToProcess.pop();
                if (!visited.contains(centerPosition)) {
                    result.peekLast().add(centerPosition);
                } else {
                    continue;
                }
            } else {
                centerPosition = allToProcess.pop();
                if (!visited.contains(centerPosition)) {
                    Set<Integer> newArea = new HashSet<>();
                    newArea.add(centerPosition);
                    result.add(newArea);
                } else {
                    continue;
                }
            }
            visited.add(centerPosition);
            BackgroundType centerBackgroundType = tiles[GameUtils.get().getX(centerPosition)][GameUtils.get().getY(centerPosition)].getBackgroundType();
            BackgroundType relativeBackgroundType;
            BackgroundAreaType backgroundAreaType;
            List<Integer> neighboringCoordinates = new LinkedList<>();
            neighboringCoordinates.add(GameUtils.get().getTopMidPosition(centerPosition));
            neighboringCoordinates.add(GameUtils.get().getBottomMidPosition(centerPosition));
            neighboringCoordinates.add(GameUtils.get().getMidLeftPosition(centerPosition));
            neighboringCoordinates.add(GameUtils.get().getMidRightPosition(centerPosition));

            for (Integer neighboringCoordinate : neighboringCoordinates) {
                if (GameUtils.get().isValidCoordinate(neighboringCoordinate) && !visited.contains(neighboringCoordinate)) {
                    relativeBackgroundType =
                            tiles[GameUtils.get().getX(neighboringCoordinate)][GameUtils.get().getY(neighboringCoordinate)].getBackgroundType();
                    backgroundAreaType = BackgroundAreaType.getCommonBackgroundAreaType(relativeBackgroundType, centerBackgroundType);
                    if (backgroundAreaType != BackgroundAreaType.UNDEFINED) {
                        sameToProcess.push(neighboringCoordinate);
                    } else {
                        allToProcess.push(neighboringCoordinate);
                    }
                }
            }
        }
    }

    public void executeCycle() {
        for (Actor actor : actors) {
            actor.move();
            int currentPosition = actor.getCurrentPosition();
            tiles[GameUtils.get().getX(currentPosition)][GameUtils.get().getY(currentPosition)].setActor(actor);
            int previousPosition = actor.getPreviousPosition();
            tiles[GameUtils.get().getX(previousPosition)][GameUtils.get().getY(previousPosition)].setActor(null);
        }
    }

    public GameTile getTile(int x, int y) {
        return tiles[x][y];
    }

    public List<Actor> getActors() {
        return actors;
    }
}
