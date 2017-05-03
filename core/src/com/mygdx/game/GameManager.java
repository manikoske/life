package com.mygdx.game;

import java.util.LinkedList;
import java.util.List;

/**
 * manikoske on 3. 4. 2017.
 */
public class GameManager implements IGameManager {

    private GameState state;
    private GameUtils utils;
    private IGameSettings settings;
    private IGameRendering rendering;

    public GameManager(IGameSettings settings, IGameRendering rendering) {
        this.settings = settings;
        this.rendering = rendering;
        utils = new GameUtils(settings);
        state = new InitialGameStateBuilder(this).build();
    }

    @Override
    public void executeCycle() {
        state.update();
        state.render();
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
    public IGameSettings getSettings() {
        return settings;
    }

    @Override
    public IGameRendering getRendering() {
        return rendering;
    }

}
