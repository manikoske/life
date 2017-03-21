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

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mygdx.game.PerlinNoiseGenerator.generatePerlinNoise;

public class Life implements ApplicationListener {

	static final float VIEWPORT_SIZE = 500;

	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private BitmapFont font;
	private SpriteBatch batch;

	TiledMapTileLayer backgroundLayer;

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
			backgroundLayer = new TiledMapTileLayer(1000, 1000, 32, 32);
			generateBackgroundLayer();
			layers.add(backgroundLayer);
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

	private void generateBackgroundLayer() {

		TextureRegion[][] splitTiles =
				TextureRegion.split(new Texture(Gdx.files.internal("dg_grounds32.gif")), 32, 32);

		float[][] elevationNoise =
				PerlinNoiseGenerator.generatePerlinNoise(backgroundLayer.getWidth(), backgroundLayer.getHeight(), 7);
		float[][] moistureNoise =
				PerlinNoiseGenerator.generatePerlinNoise(backgroundLayer.getWidth(), backgroundLayer.getHeight(), 7);
		float maxMoisture = -1;
		float minMoisture = 1;
		float maxElevation = -1;
		float minElevation = 1;

		TiledMapTileLayer.Cell cell;
		for (int x = 0; x < backgroundLayer.getWidth(); x++) {
			for (int y = 0; y < backgroundLayer.getHeight(); y++) {
				cell = new TiledMapTileLayer.Cell();
				float elevation = elevationNoise[x][y];
				float moisture = moistureNoise[x][y];

				if (elevation > maxElevation) maxElevation = elevation;
				if (moisture > maxMoisture) maxMoisture = moisture;
				if (elevation > minElevation) minElevation = elevation;
				if (moisture > minMoisture) minMoisture = moisture;

				if (elevation > .74) {
					cell.setTile(new StaticTiledMapTile(splitTiles[13][0]));
				} else if (elevation > .73) {
					cell.setTile(new StaticTiledMapTile(splitTiles[10][3]));
				} else if (elevation > 0.35) {
					cell.setTile(new StaticTiledMapTile(splitTiles[1][0]));
				} else {
					cell.setTile(new StaticTiledMapTile(splitTiles[2][2]));
				}
				backgroundLayer.setCell(x, y, cell);
			}
		}


	}

	private void generateBackgroundLayer1() {
		ConcurrentHashMap<CoordinateKey, MapGenerator.MapTile> generatedMap = new ConcurrentHashMap<>();
		MapGenerator environmentDistribution = new MapGenerator();

		int layerWidth = backgroundLayer.getWidth();
		int layerHeight = backgroundLayer.getHeight();
		int numberOfWorkers = 16;
		int batchSize = (layerHeight * layerWidth) / numberOfWorkers;

		List<List<CoordinateKey>> shuffle = new ArrayList<>(numberOfWorkers);
		List<CoordinateKey> current = new ArrayList<>();
		IntStream.range(0, layerWidth)
				.boxed()
				.forEach(x -> IntStream.range(0, layerHeight)
						.boxed()
						.forEach(y -> {
							int random = ThreadLocalRandom.current().nextInt(0, 100);
							current.add(new CoordinateKey(x, y));
							if (!current.isEmpty() && random > 1) {
								shuffle.add(new ArrayList<>(current));
								current.clear();
							}
						}));
//		Collections.shuffle(coordinates);

//		environmentDistribution.distributeProbability(Arrays.asList(MapGenerator.MapTile.UNDEFINED,
//				MapGenerator.MapTile.GRASS,
//				MapGenerator.MapTile.UNDEFINED,
//				MapGenerator.MapTile.GRASS,
//				MapGenerator.MapTile.WATER,
//				MapGenerator.MapTile.DESERT,
//				MapGenerator.MapTile.GRASS,
//				MapGenerator.MapTile.WATER));

		Collections.shuffle(shuffle);
		List<Queue<CoordinateKey>> workQueues = new ArrayList<>();
		for (int i = 0; i < numberOfWorkers; i++) {
			workQueues.add(new LinkedList<>());
		}
		for (int i = 0; i < shuffle.size(); i++) {
			workQueues.get(i % numberOfWorkers).addAll(shuffle.get(i));
		}

//		Queue<CoordinateKey> workingQueue;
//		int position = 0;
//		for (int i = 0; i < numberOfWorkers; i++) {
//			workingQueue = new LinkedList<>();
//			workingQueue.addAll(coordinates.stream().skip(position).limit(batchSize).collect(Collectors.toList()));
//			position += batchSize;
//			workQueues.add(workingQueue);
//		}
		List<CompletableFuture> workers = new ArrayList<>();
		for (int i = 0; i < numberOfWorkers; i++) {
			Queue<CoordinateKey> localWorkingQueue = workQueues.get(i);
			workers.add(CompletableFuture.runAsync(() -> {
				while (!localWorkingQueue.isEmpty()) {
					CoordinateKey coordinateKey = localWorkingQueue.poll();
					int random =  ThreadLocalRandom.current().nextInt(0, 100);
					generatedMap.put(coordinateKey, environmentDistribution.generateMapTile(random, getNeighbours(coordinateKey, generatedMap, layerWidth, layerHeight)));
				}
			}));
		}
		CompletableFuture allDone = CompletableFuture.allOf(workers.toArray(new CompletableFuture[workers.size()]));
		try {
			allDone.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		generatedMap.entrySet().forEach(entry -> {
			TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
			cell.setTile(new StaticTiledMapTile(entry.getValue().getTexture()));
			backgroundLayer.setCell(entry.getKey().x, entry.getKey().y, cell);
		});
	}

	private List<MapGenerator.MapTile> getNeighbours(CoordinateKey coordinateKey,
													 ConcurrentHashMap<CoordinateKey, MapGenerator.MapTile> generatedMap,
													 int layerWidth,
													 int layerHeight) {
		List<MapGenerator.MapTile> neighbours = new ArrayList<>();
		if (coordinateKey.x > 0 && coordinateKey.y > 0) {
			neighbours.add(generatedMap.getOrDefault(new CoordinateKey(coordinateKey.x - 1, coordinateKey.y - 1), MapGenerator.MapTile.UNDEFINED));
		} else {
			neighbours.add(MapGenerator.MapTile.UNDEFINED);
		}
		if (coordinateKey.y > 0) {
			neighbours.add(generatedMap.getOrDefault(new CoordinateKey(coordinateKey.x, coordinateKey.y - 1), MapGenerator.MapTile.UNDEFINED));
		} else {
			neighbours.add(MapGenerator.MapTile.UNDEFINED);
		}
		if (coordinateKey.x < layerWidth && coordinateKey.y > 0) {
			neighbours.add(generatedMap.getOrDefault(new CoordinateKey(coordinateKey.x + 1, coordinateKey.y - 1), MapGenerator.MapTile.UNDEFINED));
		} else {
			neighbours.add(MapGenerator.MapTile.UNDEFINED);
		}
		if (coordinateKey.x > 0) {
			neighbours.add(generatedMap.getOrDefault(new CoordinateKey(coordinateKey.x - 1, coordinateKey.y), MapGenerator.MapTile.UNDEFINED));
		} else {
			neighbours.add(MapGenerator.MapTile.UNDEFINED);
		}
		if (coordinateKey.x < layerWidth) {
			neighbours.add(generatedMap.getOrDefault(new CoordinateKey(coordinateKey.x + 1, coordinateKey.y), MapGenerator.MapTile.UNDEFINED));
		} else {
			neighbours.add(MapGenerator.MapTile.UNDEFINED);
		}
		if (coordinateKey.x > 0 && coordinateKey.y < layerHeight) {
			neighbours.add(generatedMap.getOrDefault(new CoordinateKey(coordinateKey.x - 1, coordinateKey.y + 1), MapGenerator.MapTile.UNDEFINED));
		} else {
			neighbours.add(MapGenerator.MapTile.UNDEFINED);
		}
		if (coordinateKey.y < layerHeight) {
			neighbours.add(generatedMap.getOrDefault(new CoordinateKey(coordinateKey.x, coordinateKey.y + 1), MapGenerator.MapTile.UNDEFINED));
		} else {
			neighbours.add(MapGenerator.MapTile.UNDEFINED);
		}
		if (coordinateKey.x < layerWidth && coordinateKey.y < layerHeight) {
			neighbours.add(generatedMap.getOrDefault(new CoordinateKey(coordinateKey.x + 1, coordinateKey.y + 1), MapGenerator.MapTile.UNDEFINED));
		} else {
			neighbours.add(MapGenerator.MapTile.UNDEFINED);
		}
		return neighbours;
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