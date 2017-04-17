package com.mygdx.game;


/**
 * manikoske on 17. 4. 2017.
 */
public class BackgroundGameLayer extends GameLayer {

    public BackgroundGameLayer(IGameManager gameManager) {
        super(gameManager);
    }

    @Override
    protected void accept(GameLayerVisitor visitor) {
        visitor.visit(this);
    }
}
