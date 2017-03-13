package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WordCloud implements ApplicationListener {

	static final float VIEWPORT_SIZE = 500;

	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private BitmapFont font;
	private SpriteBatch batch;

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

		{
			map = new TiledMap();
			MapLayers layers = map.getLayers();
			TiledMapTileLayer layer = new TiledMapTileLayer(1000, 1000, 32, 32);
			generateBackgroundMap(layer);
			layers.add(layer);
		}

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
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
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

	private void generateBackgroundMap(TiledMapTileLayer layer) {
		Texture tiles = new Texture(Gdx.files.internal("dg_grounds32.gif"));
		TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);
		NavigableMap<Integer, NavigableMap<Integer, TextureRegion>> environmentDistribution = new TreeMap<>();
		NavigableMap<Integer, Integer> stats = new TreeMap<>();
		stats.put(0, 0);
		stats.put(35, 0);
		stats.put(60, 0);
		stats.put(80, 0);
		stats.put(95, 0);


		NavigableMap<Integer, TextureRegion> grassTilesDistribution = new TreeMap<>();
		grassTilesDistribution.put(0, splitTiles[1][0]); // mainland 45%
		grassTilesDistribution.put(45, splitTiles[6][0]); // forest 35%
		grassTilesDistribution.put(80, splitTiles[6][0]); // mountains 20%
		environmentDistribution.put(0, grassTilesDistribution); // 35%

		NavigableMap<Integer, TextureRegion> waterTilesDistribution = new TreeMap<>();
		waterTilesDistribution.put(0, splitTiles[1][6]); // shallow 40%
		waterTilesDistribution.put(40, splitTiles[8][0]); // medium 40%
		waterTilesDistribution.put(80, splitTiles[2][0]); // deep 20%
		environmentDistribution.put(35, waterTilesDistribution); // 25%

		NavigableMap<Integer, TextureRegion> desertTilesDistribution = new TreeMap<>();
		desertTilesDistribution.put(0, splitTiles[1][3]); // mainland 85%
		desertTilesDistribution.put(85, splitTiles[7][6]); // forest 5%
		desertTilesDistribution.put(90, splitTiles[13][6]); // mountains 10%
		environmentDistribution.put(60, desertTilesDistribution); // 20%

		NavigableMap<Integer, TextureRegion> snowTilesDistribution = new TreeMap<>();
		snowTilesDistribution.put(0, splitTiles[9][3]); // mainland 60%
		snowTilesDistribution.put(60, splitTiles[10][0]); // forest 20%
		snowTilesDistribution.put(80, splitTiles[9][6]); // mountains 20%
		environmentDistribution.put(80, snowTilesDistribution); // 15%

		NavigableMap<Integer, TextureRegion> marshTilesDistribution = new TreeMap<>();
		marshTilesDistribution.put(0, splitTiles[2][3]); // mainland 60%
		marshTilesDistribution.put(60, splitTiles[7][0]); // forest 40%
		environmentDistribution.put(95, marshTilesDistribution); // 15%


		int layerWidth = layer.getWidth();
		int layerHeight = layer.getHeight();
		ConcurrentHashMap<CoordinateKey, TextureRegion> map = new ConcurrentHashMap<>();
		ConcurrentLinkedQueue<CoordinateKey> coordinates = new ConcurrentLinkedQueue<>();
		List<CoordinateKey> coordinateKeys = new ArrayList<>();
		IntStream.rangeClosed(0, layerWidth)
				.boxed()
				.forEach(x -> IntStream.rangeClosed(0, layerHeight)
						.boxed()
						.forEach(y -> coordinateKeys.add(new CoordinateKey(x, y))));
		Collections.shuffle(coordinateKeys);
		coordinateKeys.forEach(coordinates::add);

		while (!coordinates.isEmpty()) {
			CoordinateKey coordinateKey = coordinates.poll();
			map.put(coordinateKey, getTile(coordinateKey, map, environmentDistribution, stats));
		}

		map.entrySet().forEach(entry -> {
			TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
			cell.setTile(new StaticTiledMapTile(entry.getValue()));
			layer.setCell(entry.getKey().x, entry.getKey().y, cell);
		});

		System.out.println(stats);
	}

	private TextureRegion getTile(CoordinateKey coordinateKey,
								  ConcurrentHashMap<CoordinateKey, TextureRegion> map,
								  NavigableMap<Integer, NavigableMap<Integer, TextureRegion>> tileDistribution,
								  NavigableMap<Integer, Integer> stats) {
		int random =  ThreadLocalRandom.current().nextInt(0, 100);
		stats.put(stats.floorEntry(random).getKey(), stats.floorEntry(random).getValue() + 1);
		NavigableMap<Integer, TextureRegion> landscapeDistribution = tileDistribution.floorEntry(random).getValue();
		random =  ThreadLocalRandom.current().nextInt(0, 100);
		return landscapeDistribution.floorEntry(random).getValue();
	}

	private class CoordinateKey implements Comparable<CoordinateKey> {
		int x;
		int y;

		public CoordinateKey(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			CoordinateKey that = (CoordinateKey) o;

			if (x != that.x) return false;
			return y == that.y;

		}

		@Override
		public int hashCode() {
			int result = x;
			result = 31 * result + y;
			return result;
		}

		@Override
		public int compareTo(CoordinateKey o) {
			if (this.x == o.x) {
				return (this.y < o.y) ? -1 : (this.y == o.y) ? 0 : 1;
			} else {
				return (this.x < o.x) ? -1 : 1;
			}
		}
	}
}