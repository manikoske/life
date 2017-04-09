package com.mygdx.game;

/**
 * manikoske on 9. 4. 2017.
 */
public class GameTile {

    private BackgroundType backgroundType;
    private Actor actor;

    public GameTile(BackgroundType backgroundType) {
        this.backgroundType = backgroundType;
    }

    public BackgroundType getBackgroundType() {
        return backgroundType;
    }

    public void setBackgroundType(BackgroundType backgroundType) {
        this.backgroundType = backgroundType;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
