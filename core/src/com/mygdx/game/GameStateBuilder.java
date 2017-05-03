package com.mygdx.game;


import java.util.*;

/**
 * manikoske on 16. 4. 2017.
 */
public abstract class GameStateBuilder {

    private IGameManager gameManager;

    public GameStateBuilder(IGameManager gameManager) {
        this.gameManager = gameManager;
    }

    protected abstract List<Actor> createActors(IGameManager gameManager);

    protected abstract Tile[][] createTiles(IGameManager gameManager);

    public GameState build() {
        Tile[][] tiles  = createTiles(gameManager);
        List<Actor> actors = createActors(gameManager);
        return new GameState(gameManager, tiles, actors);
    }
}
