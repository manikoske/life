package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/**
 * manikoske on 11. 4. 2017.
 */
public abstract class GameResources {

    public abstract StaticTiledMapTile getBackgroundTile(BackgroundType backgroundType);

    public abstract StaticTiledMapTile getActorTile(ActorType actorType);

    public abstract int getTileDimensions();

}
