package com.mygdx.game;

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
    }

    public void executeCycle() {
        for (Actor actor : actors) {
            actor.move();
            int currentPosition = actor.getCurrentPosition();
            tiles[gameManager.getUtils().getX(currentPosition)][gameManager.getUtils().getY(currentPosition)].setActor(actor);
            int previousPosition = actor.getPreviousPosition();
            tiles[gameManager.getUtils().getX(previousPosition)][gameManager.getUtils().getY(previousPosition)].setActor(null);
        }
    }

    public GameTile getTile(int x, int y) {
        return tiles[x][y];
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Area> getAreas() {
        return areas;
    }
}
