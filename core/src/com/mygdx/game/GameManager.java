package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import java.util.*;

/**
 * manikoske on 3. 4. 2017.
 */
public class GameManager {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int TILE_DIMENSIONS = 32;

    private static final String BACKGROUND_IMAGE = "dg_grounds32.gif";
    private static final String ACTOR_IMAGE = "dg_classm32.gif";

    private EnumMap<BackgroundType, StaticTiledMapTile> backgroundResources;
    private EnumMap<ActorType, StaticTiledMapTile> actorResources;

    private GameTile[][] tiles;
    private List<Actor> actors;

    public GameManager() {
        createResources();
        createBackground();
        createActors();
    }

    private void createResources() {
        final TextureRegion[][] backgroundSplitTiles =
                TextureRegion.split(new Texture(Gdx.files.internal(BACKGROUND_IMAGE)), TILE_DIMENSIONS, TILE_DIMENSIONS);
        backgroundResources = new EnumMap<>(BackgroundType.class);
        backgroundResources.put(BackgroundType.WATER_SEA_DEEP, new StaticTiledMapTile(backgroundSplitTiles[2][2]));
        backgroundResources.put(BackgroundType.WATER_SEA_MEDIUM, new StaticTiledMapTile(backgroundSplitTiles[2][1]));
        backgroundResources.put(BackgroundType.WATER_SEA_SHALLOW, new StaticTiledMapTile(backgroundSplitTiles[2][0]));
        backgroundResources.put(BackgroundType.WATER_LAKE, new StaticTiledMapTile(backgroundSplitTiles[2][0]));
        backgroundResources.put(BackgroundType.WATER_HIGHLAND, new StaticTiledMapTile(backgroundSplitTiles[12][0]));
        backgroundResources.put(BackgroundType.DESERT, new StaticTiledMapTile(backgroundSplitTiles[1][3]));
        backgroundResources.put(BackgroundType.WASTELAND, new StaticTiledMapTile(backgroundSplitTiles[3][0]));
        backgroundResources.put(BackgroundType.GRASSLAND1, new StaticTiledMapTile(backgroundSplitTiles[1][2]));
        backgroundResources.put(BackgroundType.GRASSLAND2, new StaticTiledMapTile(backgroundSplitTiles[1][1]));
        backgroundResources.put(BackgroundType.GRASSLAND3, new StaticTiledMapTile(backgroundSplitTiles[1][0]));
        backgroundResources.put(BackgroundType.RAIN_FOREST, new StaticTiledMapTile(backgroundSplitTiles[2][3]));
        backgroundResources.put(BackgroundType.GRASSLAND_HILL, new StaticTiledMapTile(backgroundSplitTiles[10][3]));
        backgroundResources.put(BackgroundType.GRASSLAND_TREE_OAK, new StaticTiledMapTile(backgroundSplitTiles[6][0]));
        backgroundResources.put(BackgroundType.GRASSLAND_TREE_BIRCH, new StaticTiledMapTile(backgroundSplitTiles[6][3]));
        backgroundResources.put(BackgroundType.GRASSLAND_TREE_PINE, new StaticTiledMapTile(backgroundSplitTiles[6][6]));
        backgroundResources.put(BackgroundType.GRASSLAND_TREE_DEAD, new StaticTiledMapTile(backgroundSplitTiles[15][8]));
        backgroundResources.put(BackgroundType.DESERT_TREE_PALM, new StaticTiledMapTile(backgroundSplitTiles[7][6]));
        backgroundResources.put(BackgroundType.DESERT_TREE_CACTUS, new StaticTiledMapTile(backgroundSplitTiles[16][6]));
        backgroundResources.put(BackgroundType.SNOW_TREE_PINE, new StaticTiledMapTile(backgroundSplitTiles[10][0]));
        backgroundResources.put(BackgroundType.RAIN_FOREST_TREE, new StaticTiledMapTile(backgroundSplitTiles[7][0]));
        backgroundResources.put(BackgroundType.SNOW_MOUNTAIN, new StaticTiledMapTile(backgroundSplitTiles[9][6]));
        backgroundResources.put(BackgroundType.WASTELAND_MOUNTAIN, new StaticTiledMapTile(backgroundSplitTiles[9][0]));
        backgroundResources.put(BackgroundType.DESERT_MOUNTAIN, new StaticTiledMapTile(backgroundSplitTiles[13][6]));
        backgroundResources.put(BackgroundType.GRASSLAND_MOUNTAIN, new StaticTiledMapTile(backgroundSplitTiles[13][0]));
        backgroundResources.put(BackgroundType.TAIGA, new StaticTiledMapTile(backgroundSplitTiles[4][6]));

        final TextureRegion[][] actorSplitTiles =
                TextureRegion.split(new Texture(Gdx.files.internal(ACTOR_IMAGE)), TILE_DIMENSIONS, TILE_DIMENSIONS);
        actorResources = new EnumMap<>(ActorType.class);
        actorResources.put(ActorType.HUMAN, new StaticTiledMapTile(actorSplitTiles[0][3]));

    }

    private void createActors() {
        actors = new LinkedList<>();
        actors.add(new Actor(new Coordinates(10, 10), ActorType.HUMAN));
    }

    private void createBackground() {
        tiles = new GameTile[WIDTH][HEIGHT];

        float[][] elevationNoise =
                PerlinNoiseGenerator.generatePerlinNoise(WIDTH, HEIGHT, 7);
        float[][] humidityNoise =
                PerlinNoiseGenerator.generatePerlinNoise(WIDTH, HEIGHT, 6);
        float[][] temperatureNoise =
                PerlinNoiseGenerator.generatePerlinNoise(WIDTH, HEIGHT, 5);

        BackgroundType nearest;
        double minDistance;
        double currentDistance;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
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

    public void updateActorLayer(TiledMapTileLayer actorMapLayer) {
        TiledMapTileLayer.Cell cell;
        for (Actor actor : actors) {
            cell = new TiledMapTileLayer.Cell();
            cell.setTile(actorResources.get(actor.getActorType()));
            Coordinates currentPosition = actor.getCurrentPosition();
            actorMapLayer.setCell(currentPosition.getX(), currentPosition.getY(), cell);
        }
    }

    public void updateBackgroundLayer(TiledMapTileLayer backgroundLayer) {
        TiledMapTileLayer.Cell cell;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                cell = new TiledMapTileLayer.Cell();
                cell.setTile(backgroundResources.get(tiles[x][y].getBackgroundType()));
                backgroundLayer.setCell(x, y, cell);
            }
        }
    }

    public void executeCycle() {
        for (Actor actor : actors) {
            actor.move();
        }
    }
}
