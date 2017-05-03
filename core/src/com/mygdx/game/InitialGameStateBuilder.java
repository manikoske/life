package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * manikoske on 18. 4. 2017.
 */
public class InitialGameStateBuilder extends GameStateBuilder {



    public InitialGameStateBuilder(IGameManager gameManager) {
        super(gameManager);
    }

    @Override
    protected List<Actor> createActors(IGameManager gameManager) {
        List<Actor> actors = new LinkedList<>();
        for (int i = 0; i < gameManager.getSettings().getMinimalActorCount(); i++) {
            actors.add(new Actor(ThreadLocalRandom.current().nextFloat() * gameManager.getSettings().getWidth(),
                    ThreadLocalRandom.current().nextFloat() * gameManager.getSettings().getHeight(),
                    gameManager.getSettings().getInitialRadius(), String.valueOf(i)));
        }
        return actors;
    }

    @Override
    protected Tile[][] createTiles(IGameManager gameManager) {
        int horizontalTileCount = gameManager.getSettings().getHorizontalTileCount();
        int verticalTileCount = gameManager.getSettings().getVerticalTileCount();
        Tile[][] tiles = new Tile[horizontalTileCount][verticalTileCount];

        float[][] elevationNoise =
                PerlinNoiseGenerator.generatePerlinNoise(horizontalTileCount, verticalTileCount, 7);
        float[][] humidityNoise =
                PerlinNoiseGenerator.generatePerlinNoise(horizontalTileCount, verticalTileCount, 6);
        float[][] temperatureNoise =
                PerlinNoiseGenerator.generatePerlinNoise(horizontalTileCount, verticalTileCount, 5);

        for (int x = 0; x < horizontalTileCount; x++) {
            for (int y = 0; y < verticalTileCount; y++) {
                tiles[x][y] = new Tile(elevationNoise[x][y], humidityNoise[x][y], temperatureNoise[x][y]);
            }
        }

        return tiles;
    }
}
