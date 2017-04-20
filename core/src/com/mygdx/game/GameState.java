package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.*;

/**
 * manikoske on 11. 4. 2017.
 */
public class GameState {

    private IGameManager gameManager;

    private GameTile[][] tiles;
    private List<Area> areas;
    private List<Actor> actors;
    private GameLayerVisitor renderingGameLayerVisitor;


    public GameState(IGameManager gameManager, GameTile[][] tiles, List<Area> areas, List<Actor> actors) {
        this.gameManager = gameManager;
        this.tiles = tiles;
        this.areas = areas;
        this.actors = actors;

        renderingGameLayerVisitor = new GameLayerVisitor() {
            @Override
            public void visit(BackgroundGameLayer backgroundGameLayer) {
                TiledMapTileLayer.Cell cell;
                for (int x = 0; x < gameManager.getWidth(); x++) {
                    for (int y = 0; y < gameManager.getHeight(); y++) {
                        cell = new TiledMapTileLayer.Cell();
                        cell.setTile(gameManager.getResources().getBackgroundTile(tiles[x][y].getBackgroundType()));
                        backgroundGameLayer.getTiledMapTileLayer().setCell(x, y, cell);
                    }
                }
            }

            @Override
            public void visit(ActorGameLayer actorGameLayer) {
                TiledMapTileLayer.Cell cell;
                for (int x = 0; x < gameManager.getWidth(); x++) {
                    for (int y = 0; y < gameManager.getHeight(); y++) {
                        if (actorGameLayer.getTiledMapTileLayer().getCell(x, y) != null) {
                            actorGameLayer.getTiledMapTileLayer().setCell(x, y, null);
                        }
                        if (tiles[x][y].getActor() != null) {
                            cell = new TiledMapTileLayer.Cell();
                            cell.setTile(gameManager.getResources().getActorTile(tiles[x][y].getActor().getActorType()));
                            actorGameLayer.getTiledMapTileLayer().setCell(x, y, cell);
                        }
                    }
                }
            }
        };
    }

    public void executeCycle() {
        for (Actor actor : actors) {
            actor.move();
            int currentPosition = actor.getCurrentPosition();
            tiles[gameManager.getUtils().getX(currentPosition)][gameManager.getUtils().getY(currentPosition)].setActor(actor);
            int previousPosition = actor.getPreviousPosition();
            if (currentPosition != previousPosition) {
                tiles[gameManager.getUtils().getX(previousPosition)][gameManager.getUtils().getY(previousPosition)].setActor(null);
            }

        }
    }

    public GameLayerVisitor getRenderingGameLayerVisitor() {
        return renderingGameLayerVisitor;
    }
}
