package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class Life implements ApplicationListener {

    static final float VIEWPORT_SIZE = 500;

    private TiledMap map;
    private TiledMapRenderer renderer;
    private OrthographicCamera camera;
    private BitmapFont font;
    private SpriteBatch batch;

    private GameManager gameManager;

    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer actorLayer;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * VIEWPORT_SIZE, VIEWPORT_SIZE);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        Gdx.input.setInputProcessor(new OrthographicCameraController(camera));

        font = new BitmapFont();
        batch = new SpriteBatch();

        gameManager = new GameManager();
        map = new TiledMap();
        MapLayers layers = map.getLayers();
        backgroundLayer =
                new TiledMapTileLayer(GameManager.WIDTH, GameManager.HEIGHT, GameManager.TILE_DIMENSIONS, GameManager.TILE_DIMENSIONS);
        actorLayer =
                new TiledMapTileLayer(GameManager.WIDTH, GameManager.HEIGHT, GameManager.TILE_DIMENSIONS, GameManager.TILE_DIMENSIONS);

        layers.add(backgroundLayer);
        layers.add(actorLayer);

        renderer = new OrthogonalTiledMapRenderer(map);
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
        batch.enableBlending();
        camera.update();
        renderer.setView(camera);
        gameManager.executeCycle();
        gameManager.updateBackgroundLayer(backgroundLayer);
        gameManager.updateActorLayer(actorLayer);
        renderer.render();
        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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