package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import java.util.HashMap;
import java.util.Map;

/**
 * manikoske on 3. 4. 2017.
 */
public class GameManager {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int TILE_DIMENSIONS = 32;

    private static final String BACKGROUND_IMAGE = "dg_grounds32.gif";

    private BackgroundMapTile[][] background;

    public GameManager() {
        createBackground();
    }

    public void createBackground() {
        background = new BackgroundMapTile[WIDTH][HEIGHT];

        float[][] elevationNoise =
                PerlinNoiseGenerator.generatePerlinNoise(WIDTH, HEIGHT, 7);
        float[][] humidityNoise =
                PerlinNoiseGenerator.generatePerlinNoise(WIDTH, HEIGHT, 6);
        float[][] temperatureNoise =
                PerlinNoiseGenerator.generatePerlinNoise(WIDTH, HEIGHT, 5);

        float elevation;
        float humidity;
        float temperature;

        BackgroundMapTile nearest;
        double minDistance;
        double currentDistance;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                elevation = elevationNoise[x][y];
                humidity = humidityNoise[x][y];
                temperature = temperatureNoise[x][y];
                nearest = BackgroundMapTile.GRASSLAND1;
                minDistance = 100.0;
                for (BackgroundMapTile backgroundMapTile : BackgroundMapTile.values()) {
                    currentDistance = backgroundMapTile.getDistance(elevation, humidity, temperature);
                    if (currentDistance < minDistance) {
                        minDistance = currentDistance;
                        nearest = backgroundMapTile;
                    }
                }
                background[x][y] = nearest;
            }
        }
    }

    public void updateBackgroundLayer(TiledMapTileLayer backgroundLayer) {
        final TextureRegion[][] splitTiles =
                TextureRegion.split(new Texture(Gdx.files.internal(BACKGROUND_IMAGE)), TILE_DIMENSIONS, TILE_DIMENSIONS);
        Map<BackgroundMapTile, StaticTiledMapTile> mapTiles = new HashMap<>();
        TiledMapTileLayer.Cell cell;
        BackgroundMapTile actualBackgroundMapTile;
        StaticTiledMapTile actualStaticTiledMapTile;

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                cell = new TiledMapTileLayer.Cell();
                actualBackgroundMapTile = background[x][y];
                actualStaticTiledMapTile = mapTiles.computeIfAbsent(actualBackgroundMapTile,
                        backgroundMapTile -> new StaticTiledMapTile(
                                splitTiles[backgroundMapTile.getTextureIndexX()][backgroundMapTile.getTextureIndexY()]));
                cell.setTile(actualStaticTiledMapTile);
                backgroundLayer.setCell(x, y, cell);
            }
        }
    }
}
