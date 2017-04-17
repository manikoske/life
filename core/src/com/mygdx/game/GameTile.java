package com.mygdx.game;

/**
 * manikoske on 9. 4. 2017.
 */
public class GameTile {

    private BackgroundType backgroundType;
    private Actor actor;
    private Area area;

    public GameTile(BackgroundType backgroundType) {
        this.backgroundType = backgroundType;
    }

    public BackgroundType getBackgroundType() {
        return backgroundType;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public static class GameTileBuilder {

        private BackgroundType backgroundType;
        private Actor actor;
        private Area area;

        public GameTileBuilder(BackgroundType backgroundType) {
            this.backgroundType = backgroundType;
        }

        public GameTileBuilder setActor(Actor actor) {
            this.actor = actor;
            return this;
        }

        public GameTileBuilder setArea(Area area) {
            this.area = area;
            return this;
        }

        public GameTile build() {
            GameTile gameTile = new GameTile(backgroundType);
            gameTile.setActor(actor);
            gameTile.setArea(area);
            return gameTile;
        }
    }
}
