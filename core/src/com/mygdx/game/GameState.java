package com.mygdx.game;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        actors.add(new Actor(new Coordinates(0, 0), ActorType.HUMAN));
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
    }

    private void produceAreas() {
        Set<Coordinates> visited = new HashSet<>();
        List<Set<Coordinates>> result;

    }

    public void executeCycle() {
        for (Actor actor : actors) {
            actor.move();
            Coordinates currentPosition = actor.getCurrentPosition();
            tiles[currentPosition.getX()][currentPosition.getY()].setActor(actor);
            Coordinates previousPosition = actor.getPreviousPosition();
            tiles[previousPosition.getX()][previousPosition.getY()].setActor(null);
        }
    }

    public GameTile getTile(int x, int y) {
        return tiles[x][y];
    }

    public List<Actor> getActors() {
        return actors;
    }
}
