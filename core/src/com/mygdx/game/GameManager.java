package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * manikoske on 3. 4. 2017.
 */
public class GameManager {

    private GameResources resources;
    private GameState state;
    private GameUtils utils;

    private int width;
    private int height;

    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer actorLayer;


    public GameManager(int width, int height) {
        this.width = width;
        this.height = height;
        utils = new GameUtils(width, height);
        resources = new GameResourcesAngband();
        state = new GameState(width, height);
        initializeLayers();

    }

    private void initializeLayers() {
        backgroundLayer =
                new TiledMapTileLayer(width, height, resources.getTileDimensions(), resources.getTileDimensions());
        actorLayer =
                new TiledMapTileLayer(width, height, resources.getTileDimensions(), resources.getTileDimensions());

        TiledMapTileLayer.Cell cell;
        GameTile gameTile;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gameTile = state.getTile(x, y);
                cell = new TiledMapTileLayer.Cell();
                cell.setTile(resources.getBackgroundTile(gameTile.getBackgroundType()));
                backgroundLayer.setCell(x, y, cell);
            }
        }
    }

    public void update() {
        state.executeCycle();

        TiledMapTileLayer.Cell cell;
        GameTile gameTile;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gameTile = state.getTile(x, y);
                cell = new TiledMapTileLayer.Cell();
                if (actorLayer.getCell(x, y) != null) {
                    actorLayer.setCell(x, y, null);
                }
                if (gameTile.getActor() != null) {
                    cell.setTile(resources.getActorTile(gameTile.getActor().getActorType()));
                    actorLayer.setCell(x, y, cell);
                }
            }
        }
    }

    public TiledMapTileLayer getBackgroundLayer() {
        return backgroundLayer;
    }

    public TiledMapTileLayer getActorLayer() {
        return actorLayer;
    }
}
