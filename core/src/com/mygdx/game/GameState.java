package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

/**
 * manikoske on 11. 4. 2017.
 */
public class GameState {

    private IGameManager gameManager;

    private Tile[][] tiles;
    private List<Actor> actors;
    private CollisionDetection collisionDetection;


    public GameState(IGameManager gameManager, Tile[][] tiles, List<Actor> actors) {
        this.gameManager = gameManager;
        this.tiles = tiles;
        this.actors = actors;
        this.collisionDetection = new CollisionDetection(gameManager);
    }

    public void update() {
        collisionDetection.executeBroadPhase(actors);
        // resolve interactions

        // use brain to determine next action

        // execute action

        // spawn new actors if needed
    }

    public void render() {
        ShapeRenderer shapeRenderer = gameManager.getRendering().getShapeRenderer();
        BitmapFont bitmapFont = gameManager.getRendering().getBitmapFont();
        SpriteBatch spriteBatch = gameManager.getRendering().getSpriteBatch();
        int tileDimensions = gameManager.getSettings().getTileDimensions();

        Tile tile;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < gameManager.getSettings().getHorizontalTileCount(); x ++) {
            for (int y = 0; y < gameManager.getSettings().getVerticalTileCount(); y ++) {
                tile = tiles[x][y];
                shapeRenderer.setColor(new Color(tile.getX(), tile.getY(), tile.getZ(), 1f));
                shapeRenderer.rect(x * tileDimensions, y * tileDimensions, tileDimensions, tileDimensions);
            }
        }
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < gameManager.getSettings().getHorizontalTileCount(); i++) {
            shapeRenderer.line(i * gameManager.getSettings().getTileDimensions(), 0,
                    i * gameManager.getSettings().getTileDimensions(),
                    gameManager.getSettings().getHeight());
        }
        for (int i = 0; i < gameManager.getSettings().getVerticalTileCount(); i++) {
            shapeRenderer.line(0, i * gameManager.getSettings().getTileDimensions(),
                    gameManager.getSettings().getWidth(),
                    i * gameManager.getSettings().getTileDimensions());
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Actor actor : actors) {
            shapeRenderer.circle(actor.getX(), actor.getY(), actor.getRadius(), 200);
        }
        shapeRenderer.end();

        spriteBatch.begin();
        for (Actor actor : actors) {
            bitmapFont.draw(spriteBatch, actor.getName(), actor.getX() - actor.getRadius()/ 2 , actor.getY() + actor.getRadius() + 15);
        }
        spriteBatch.end();
    }
}
