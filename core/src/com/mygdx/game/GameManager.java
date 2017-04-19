package com.mygdx.game;

import java.util.LinkedList;
import java.util.List;

/**
 * manikoske on 3. 4. 2017.
 */
public class GameManager implements IGameManager {

    private IGameResources resources;
    private GameState state;
    private GameUtils utils;
    private BackgroundGameLayer backgroundGameLayer;
    private ActorGameLayer actorGameLayer;

    private int width;
    private int height;


    public GameManager(int width, int height) {
        this.width = width;
        this.height = height;
        utils = new GameUtils(this);
        resources = new GameResources();
        state = new InitialGameStateBuilder(this).build();
        backgroundGameLayer = new BackgroundGameLayer(this);
        actorGameLayer = new ActorGameLayer(this);
        backgroundGameLayer.accept(state.getRenderingGameLayerVisitor());
    }

    @Override
    public void update() {
        state.executeCycle();
        actorGameLayer.accept(state.getRenderingGameLayerVisitor());
    }

    @Override
    public List<GameLayer> getGameLayers() {
        List<GameLayer> layers = new LinkedList<>();
        layers.add(backgroundGameLayer);
        layers.add(actorGameLayer);
        return layers;
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
