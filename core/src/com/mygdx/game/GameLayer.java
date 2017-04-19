package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * manikoske on 17. 4. 2017.
 */
public abstract class GameLayer {

    private IGameManager gameManager;
    private TiledMapTileLayer tiledMapTileLayer;

    public GameLayer(IGameManager gameManager) {
        this.gameManager = gameManager;
        this.tiledMapTileLayer =
                new TiledMapTileLayer(gameManager.getWidth(),
                        gameManager.getHeight(),
                        gameManager.getResources().getTileDimensions(),
                        gameManager.getResources().getTileDimensions());
    }

    protected abstract void accept(GameLayerVisitor visitor);

    public TiledMapTileLayer getTiledMapTileLayer() {
        return tiledMapTileLayer;
    }
}
