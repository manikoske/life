package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * manikoske on 3. 4. 2017.
 */
public class GameManager {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    private GameResources resources;
    private GameState state;

    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer actorLayer;


    public GameManager() {
        resources = new GameResourcesAngband();
        state = new GameState(WIDTH, HEIGHT);
        initializeLayers();

    }

    private void initializeLayers() {
        backgroundLayer =
                new TiledMapTileLayer(GameManager.WIDTH, GameManager.HEIGHT, resources.getTileDimensions(), resources.getTileDimensions());
        actorLayer =
                new TiledMapTileLayer(GameManager.WIDTH, GameManager.HEIGHT, resources.getTileDimensions(), resources.getTileDimensions());

        TiledMapTileLayer.Cell cell;
        GameTile gameTile;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
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
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
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
