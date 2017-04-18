package com.mygdx.game;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.LinkedList;
import java.util.List;

/**
 * manikoske on 3. 4. 2017.
 */
public class GameManager implements IGameManager {

    private IGameResources resources;
    private GameState state;
    private GameUtils utils;
    private List<GameLayer> layers;

    private int width;
    private int height;


    public GameManager(int width, int height) {
        this.width = width;
        this.height = height;
        utils = new GameUtils(this);
        resources = new GameResources();
        state = new InitialGameStateBuilder(this).build();
        layers = new LinkedList<>();
        layers.add(new BackgroundGameLayer(this));

        initializeLayers();
    }

    private void initializeLayers() {
//        backgroundLayer =
//                new TiledMapTileLayer(width, height, resources.getTileDimensions(), resources.getTileDimensions());
//        actorLayer =
//                new TiledMapTileLayer(width, height, resources.getTileDimensions(), resources.getTileDimensions());
//
//        TiledMapTileLayer.Cell cell;
//        GameTile gameTile;
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                gameTile = state.getTile(x, y);
//                cell = new TiledMapTileLayer.Cell();
//                cell.setTile(resources.getBackgroundTile(gameTile.getBackgroundType()));
//                backgroundLayer.setCell(x, y, cell);
//            }
//        }
    }

    @Override
    public void update() {
//        state.executeCycle();
//
//        TiledMapTileLayer.Cell cell;
//        GameTile gameTile;
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                gameTile = state.getTile(x, y);
//                cell = new TiledMapTileLayer.Cell();
//                if (actorLayer.getCell(x, y) != null) {
//                    actorLayer.setCell(x, y, null);
//                }
//                if (gameTile.getActor() != null) {
//                    cell.setTile(resources.getActorTile(gameTile.getActor().getActorType()));
//                    actorLayer.setCell(x, y, cell);
//                }
//            }
//        }
    }

    @Override
    public void addLayers(TiledMap map) {
//        MapLayers layers = map.getLayers();
//        layers.add(backgroundLayer);
//        layers.add(actorLayer);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public GameUtils getUtils() {
        return utils;
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public IGameResources getResources() {
        return resources;
    }
}
