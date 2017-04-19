package com.mygdx.game;


/**
 * manikoske on 17. 4. 2017.
 */
public class ActorGameLayer extends GameLayer {

    public ActorGameLayer(IGameManager gameManager) {
        super(gameManager);
    }

    @Override
    protected void accept(GameLayerVisitor visitor) {
        visitor.visit(this);
    }
}
