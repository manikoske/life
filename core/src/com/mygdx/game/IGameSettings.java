package com.mygdx.game;

/**
 * manikoske on 26. 4. 2017.
 */
public interface IGameSettings {

    int getTileDimensions();

    int getHorizontalTileCount();

    int getVerticalTileCount();

    int getWidth();

    int getHeight();

    int getMinimalActorCount();

    int getInitialRadius();

    int getInitialBroadPhaseBoxDimensions();

}
