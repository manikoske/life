package com.mygdx.game;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.List;

/**
 * manikoske on 3. 4. 2017.
 */
public interface IGameManager {

    public void update();

    public List<GameLayer> getGameLayers();

    public int getWidth();

    public int getHeight();

    public GameUtils getUtils();

    public GameState getState();

    public IGameResources getResources();
}
