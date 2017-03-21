package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.*;

import static com.mygdx.game.MapGenerator.MapTile.UNDEFINED;

/**
 * Created by manikoske on 17. 3. 2017.
 */
public class MapGenerator {

    private static final TextureRegion[][] splitTiles =
            TextureRegion.split(new Texture(Gdx.files.internal("dg_grounds32.gif")), 32, 32);

    public enum MapTile {

        GRASS(50, splitTiles[1][0]),
        WATER(30, splitTiles[2][2]),
        DESERT(15, splitTiles[1][4]),
        //        SNOW(20, splitTiles[9][3]),
        MARSH(5, splitTiles[2][3]),
        UNDEFINED(-1, null);

        private TextureRegion texture;
        private int probability;

        MapTile(int probability, TextureRegion texture) {
            this.texture = texture;
            this.probability = probability;
        }

        public TextureRegion getTexture() {
            return texture;
        }

        public int getProbability() {
            return probability;
        }
    }

    public MapTile generateMapTile(int random, List<MapTile> neighbours) {
        /**
         *   0 | 1 | 2
         *   3 | ? | 4
         *   5 | 6 | 7
         */
        return getRandomTileByDistribution(random, distributeProbability(neighbours));
    }

    public Map<MapTile, Percentage> distributeProbability(List<MapTile> neighbours) {
        Map<MapTile, Percentage> resultingDistribution = new HashMap<>();
        Map<MapTile, Integer> weights = new HashMap<>();
        int definedCount = 0;
        boolean allUndefined = true;
        for (MapTile neighbour : neighbours) {
            if (neighbour != UNDEFINED) {
                allUndefined = false;
                definedCount++;
                weights.compute(neighbour, (mapTile, integer) -> (integer == null) ? 1 : integer + 1);
            }
        }
        if (allUndefined) {
            return getDefaultDistribution();
        } else {
            int scale = weights.keySet().stream()
                    .filter(mapTile -> mapTile != UNDEFINED)
                    .map(MapTile::getProbability)
                    .reduce(0, (i1, i2) -> i1 + i2);
            for (Map.Entry<MapTile, Integer> entry : weights.entrySet()) {
                resultingDistribution.put(entry.getKey(),
                        (new Percentage(entry.getValue(), definedCount)
                                .add(new Percentage(entry.getKey().getProbability(), scale)))
                                .multiply(new Percentage(1, 2)));
            }
        }
        return resultingDistribution;
    }

    private Map<MapTile, Percentage> distributeProbability1(List<MapTile> neighbours) {
        Map<MapTile, Percentage> resultingDistribution = new HashMap<>();

        List<Connection> connections =
                Arrays.asList(new Connection(3, 0, 1, 50),
                        new Connection(4, 2, 1, 50),
                        new Connection(5, 3, 1, 50),
                        new Connection(7, 4, 1, 50),
                        new Connection(1, 0, 1, 50),
                        new Connection(2, 1, 1, 50),
                        new Connection(6, 5, 1, 50),
                        new Connection(7, 6, 1, 50),
                        new Connection(6, 1, 1, 100),
                        new Connection(7, 0, 1, 100),
                        new Connection(5, 2, 1, 100),
                        new Connection(4, 3, 1, 100));

        Map<MapTile, Integer> tileSums = new HashMap<>();
        int index = 0;
        int definedCount = 0;
        for (MapTile neighbour : neighbours) {
            tileSums.computeIfAbsent(neighbour, mapTile -> 0);
            if (index == 0 || index == 2 || index == 5 || index == 7) {
                if (neighbour == UNDEFINED) {
                    tileSums.computeIfPresent(neighbour, (mapTile, integer) -> integer + 1);
                } else {
                    tileSums.computeIfPresent(neighbour, (mapTile, integer) -> integer + 100);
                    definedCount++;
                }
            } else {
                if (neighbour == UNDEFINED) {
                    tileSums.computeIfPresent(neighbour, (mapTile, integer) -> integer + 1);
                } else {
                    tileSums.computeIfPresent(neighbour, (mapTile, integer) -> integer + 100);
                    definedCount++;
                }
            }
            MapTile opposite;
            for (Connection connection : connections) {
                if (index == connection.occurent) {
                    opposite = neighbours.get(connection.opposite);
                    if (opposite == neighbour) {
                        if (neighbour == UNDEFINED) {
                            tileSums.computeIfPresent(neighbour, (mapTile, integer) -> integer + connection.undefinedWeight);
                        } else {
                            tileSums.computeIfPresent(neighbour, (mapTile, integer) -> integer + connection.definedWeight);
                        }
                    }
                }
            }
            index++;
        }

        int definedSum = tileSums.entrySet().stream()
                .filter(entry -> entry.getKey() != UNDEFINED)
                .map(Map.Entry::getValue)
                .reduce(0, (integer1, integer2) -> integer1 + integer2);
        int allSum = tileSums.entrySet().stream()
                .map(Map.Entry::getValue)
                .reduce(0, (integer1, integer2) -> integer1 + integer2);
        int undefinedSum = allSum - definedSum;

        if (undefinedSum == allSum) {
            for (MapTile mapTile : MapTile.values()) {
                if (mapTile != UNDEFINED) {
                    resultingDistribution.put(mapTile,
                            new Percentage(mapTile.probability, 100).multiply(new Percentage(undefinedSum, allSum)));
                }
            }
        }

        int scale = tileSums.keySet().stream()
                .filter(item -> item != UNDEFINED)
                .map(MapTile::getProbability)
                .reduce(0, (integer1, integer2) -> integer1 + integer2);

        if (scale > 0) {
            for (Map.Entry<MapTile, Integer> entry : tileSums.entrySet()) {
                if (entry.getKey() != UNDEFINED) {
                    Percentage definedPercentage = ((new Percentage(entry.getValue() * definedCount, definedSum)
                            .add(new Percentage(entry.getKey().probability, scale)))
                            .multiply(new Percentage(1, definedCount + 1)))
                            .multiply(new Percentage(definedSum, allSum));
                    resultingDistribution.putIfAbsent(entry.getKey(), new Percentage(0, 1));
                    resultingDistribution.computeIfPresent(entry.getKey(), (mapTile, percentage) -> percentage.add(definedPercentage));
                }
            }
        }
        return resultingDistribution;
    }

    private Map<MapTile, Percentage> getDefaultDistribution() {
        Map<MapTile, Percentage> resultingDistribution = new HashMap<>();
        for (MapTile mapTile : MapTile.values()) {
            if (mapTile != UNDEFINED) {
                resultingDistribution.put(mapTile, new Percentage(mapTile.probability));
            }
        }
        return resultingDistribution;
    }

    private MapTile getRandomTileByDistribution(int random, Map<MapTile, Percentage> computedDistribution) {
        Percentage check = computedDistribution.entrySet().stream()
                .map(Map.Entry::getValue)
                .reduce(new Percentage(0, 1), Percentage::add);

        assert check.equals(new Percentage(1, 1));

        int probabilityCounter = 0;
        NavigableMap<Integer, MapTile> finalDistribution = new TreeMap<>();
        for (Map.Entry<MapTile, Percentage> entry : computedDistribution.entrySet()) {
            finalDistribution.put(probabilityCounter, entry.getKey());
            probabilityCounter += entry.getValue().getRoundedRatio();
        }
        return finalDistribution.floorEntry(random).getValue();
    }


}
