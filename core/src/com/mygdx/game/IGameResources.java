package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/**
 * manikoske on 11. 4. 2017.
 */
public interface IGameResources {

    public StaticTiledMapTile getBackgroundTile(BackgroundType backgroundType);

    public StaticTiledMapTile getActorTile(ActorType actorType);

    public int getTileDimensions();

}
