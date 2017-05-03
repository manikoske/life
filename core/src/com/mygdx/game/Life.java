package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;


public class Life implements ApplicationListener {

    static final float VIEWPORT_SIZE = 1000;
    private static final int TILE_DIMENSIONS = 32;
    private static final int TILE_HORIZONTAL_COUNT = 100;
    private static final int TILE_VERTICAL_COUNT = 100;
    private static final int MINIMAL_ACTOR_COUNT = 50;
    private static final int STARTING_ACTOR_RADIUS = 15;

    private OrthographicCamera camera;
    private BitmapFont font;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private GameManager gameManager;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        batch = new SpriteBatch();

        gameManager = new GameManager(new IGameSettings() {
            @Override
            public int getTileDimensions() {
                return TILE_DIMENSIONS;
            }

            @Override
            public int getHorizontalTileCount() {
                return TILE_HORIZONTAL_COUNT;
            }

            @Override
            public int getVerticalTileCount() {
                return TILE_VERTICAL_COUNT;
            }

            @Override
            public int getWidth() {
                return TILE_HORIZONTAL_COUNT * TILE_DIMENSIONS;
            }

            @Override
            public int getHeight() {
                return TILE_VERTICAL_COUNT * TILE_DIMENSIONS;
            }

            @Override
            public int getMinimalActorCount() {
                return MINIMAL_ACTOR_COUNT;
            }

            @Override
            public int getInitialRadius() {
                return STARTING_ACTOR_RADIUS;
            }

            @Override
            public int getInitialBroadPhaseBoxDimensions() {
                return 2 * TILE_DIMENSIONS;
            }


        }, new IGameRendering() {
            @Override
            public BitmapFont getBitmapFont() {
                return font;
            }

            @Override
            public SpriteBatch getSpriteBatch() {
                return batch;
            }

            @Override
            public ShapeRenderer getShapeRenderer() {
                return shapeRenderer;
            }
        });

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * VIEWPORT_SIZE, VIEWPORT_SIZE);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        Gdx.input.setInputProcessor(new OrthographicCameraController(camera));
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = (width / height) * VIEWPORT_SIZE;
        camera.viewportHeight = VIEWPORT_SIZE;
        camera.update();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameManager.executeCycle();
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}