package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * manikoske on 26. 4. 2017.
 */
public interface IGameRendering {

    BitmapFont getBitmapFont();

    SpriteBatch getSpriteBatch();

    ShapeRenderer getShapeRenderer();

}
