package com.mygdx.game;

/**
 * manikoske on 17. 3. 2017.
 */
public enum ActorMapTile {

    HUMAN(2, 2);

    private int textureIndexX;
    private int textureIndexY;

    ActorMapTile(int textureIndexX, int textureIndexY) {
        this.textureIndexX = textureIndexX;
        this.textureIndexY = textureIndexY;
    }

    public int getTextureIndexX() {
        return textureIndexX;
    }

    public int getTextureIndexY() {
        return textureIndexY;
    }
}
