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

    protected abstract BackgroundType[][] createBackground(IGameManager gameManager);

    protected abstract List<Actor> createActors(IGameManager gameManager);

    protected abstract List<List<Integer>> createAreas(IGameManager gameManager, BackgroundType[][] backgroundTypes);

    protected abstract List<Area> nameAreas(IGameManager gameManager,
                                            List<List<Integer>> positionsOfAreas,
                                            BackgroundType[][] backgroundTypes);

    protected abstract GameTile[][] createGameTiles(IGameManager gameManager,
                                          BackgroundType[][] backgroundTypes,
                                          List<Area> areas,
                                          List<Actor> actors);

    public GameState build() {
        BackgroundType[][] backgroundTypes = createBackground(gameManager);
        List<List<Integer>> positionsOfAreas = createAreas(gameManager, backgroundTypes);
        List<Area> areas = nameAreas(gameManager, positionsOfAreas, backgroundTypes);
        List<Actor> actors = createActors(gameManager);
        GameTile[][] gameTiles = createGameTiles(gameManager, backgroundTypes, areas, actors);
        return new GameState(gameManager, gameTiles, areas, actors);
    }
}
