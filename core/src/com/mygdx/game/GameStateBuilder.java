package com.mygdx.game;

import java.util.*;
import java.util.stream.Collectors;

/**
 * manikoske on 16. 4. 2017.
 */
public class GameStateBuilder {

    private IGameManager gameManager;
    private BackgroundCreationStrategy backgroundCreationStrategy;
    private AreaCreationStrategy areaCreationStrategy;
    private AreaNamingStrategy areaNamingStrategy;
    private ActorCreationStrategy actorCreationStrategy;
    private GameTileCreationStrategy gameTileCreationStrategy;

    public GameStateBuilder(IGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public GameStateBuilder setBackgroundCreationStrategy(BackgroundCreationStrategy backgroundCreationStrategy) {
        this.backgroundCreationStrategy = backgroundCreationStrategy;
        return this;
    }

    public GameStateBuilder setAreaCreationStrategy(AreaCreationStrategy areaCreationStrategy) {
        this.areaCreationStrategy = areaCreationStrategy;
        return this;
    }

    public GameStateBuilder setAreaNamingStrategy(AreaNamingStrategy areaNamingStrategy) {
        this.areaNamingStrategy = areaNamingStrategy;
        return this;
    }

    public GameStateBuilder setActorCreationStrategy(ActorCreationStrategy actorCreationStrategy) {
        this.actorCreationStrategy = actorCreationStrategy;
        return this;
    }

    public GameStateBuilder setGameTileCreationStrategy(GameTileCreationStrategy gameTileCreationStrategy) {
        this.gameTileCreationStrategy = gameTileCreationStrategy;
        return this;
    }

    public GameState build() {
        BackgroundType[][] backgroundTypes;
        if (backgroundCreationStrategy == null) {
            backgroundTypes = new BackgroundCreationStrategy(){}.create(gameManager);
        } else {
            backgroundTypes = backgroundCreationStrategy.create(gameManager);
        }
        List<List<Integer>> positionsOfAreas;
        if (areaCreationStrategy == null) {
            positionsOfAreas = new AreaCreationStrategy(){}.create(gameManager, backgroundTypes);
        } else {
            positionsOfAreas = areaCreationStrategy.create(gameManager, backgroundTypes);
        }
        List<Area> areas;
        if (areaNamingStrategy == null) {
            areas = new AreaNamingStrategy(){}.name(gameManager, positionsOfAreas, backgroundTypes);
        } else {
            areas = areaNamingStrategy.name(gameManager, positionsOfAreas, backgroundTypes);
        }
        List<Actor> actors;
        if (actorCreationStrategy == null) {
            actors = new ActorCreationStrategy(){}.create(gameManager);
        } else {
            actors = actorCreationStrategy.create(gameManager);
        }
        GameTile[][] gameTiles;
        if (gameTileCreationStrategy == null) {
            gameTiles = new GameTileCreationStrategy(){}.create(gameManager, backgroundTypes, areas, actors);
        } else {
            gameTiles = gameTileCreationStrategy.create(gameManager, backgroundTypes, areas, actors);
        }
        return new GameState(gameManager, gameTiles, areas, actors);
    }

    public interface GameTileCreationStrategy {
        default GameTile[][]create(IGameManager gameManager,
                                   BackgroundType[][] backgroundTypes,
                                   List<Area> areas,
                                   List<Actor> actors) {
            GameTile[][] gameTiles = new GameTile[gameManager.getWidth()][gameManager.getHeight()];
            GameTile.GameTileBuilder gameTileBuilder;
            for (int x = 0; x < gameManager.getWidth(); x++) {
                for (int y = 0; y < gameManager.getHeight(); y++) {
                    gameTileBuilder = new GameTile.GameTileBuilder(backgroundTypes[x][y]);
                    int currentPosition = gameManager.getUtils().toPosition(x, y);
                    actors.stream()
                            .filter(actor -> actor.getCurrentPosition() == currentPosition).findAny()
                            .ifPresent(gameTileBuilder::setActor);
                    areas.stream()
                            .filter(area -> area.getIncludedTilePositions().contains(currentPosition)).findAny()
                            .ifPresent(gameTileBuilder::setArea);
                    gameTiles[x][y] = gameTileBuilder.build();
                }
            }
            return gameTiles;
        }
    }

    public interface ActorCreationStrategy {
        default List<Actor> create(IGameManager gameManager) {
            List<Actor> actors = new LinkedList<>();
            actors.add(new Actor(0, ActorType.HUMAN));
            return actors;
        }
    }

    public interface AreaNamingStrategy {
        default List<Area> name(IGameManager gameManager, List<List<Integer>> positionsOfAreas, BackgroundType[][] backgroundTypes) {
            List<Area> namedAreas = new ArrayList<>();
            for (List<Integer> area : positionsOfAreas) {
                area.stream().findAny().ifPresent(position -> {
                    int x = gameManager.getUtils().getX(position);
                    int y = gameManager.getUtils().getY(position);
                    namedAreas.add(new Area(AreaType.getAreaType(backgroundTypes[x][y]), "uh", area));
                });
            }
            return namedAreas;
        }
    }

    public interface AreaCreationStrategy {
        default List<List<Integer>> create(IGameManager gameManager, BackgroundType[][] backgroundTypes) {
            int tileCount = gameManager.getWidth() * gameManager.getHeight();
            int threshold;
            if (tileCount < 10) {
                threshold = 1;
            } else if (tileCount < 100) {
                threshold = 2;
            } else if (tileCount < 1000) {
                threshold = 5;
            } else if (tileCount < 10000) {
                threshold = 10;
            } else if (tileCount < 100000) {
                threshold = 50;
            } else if (tileCount < 1000000) {
                threshold = 100;
            } else {
                threshold = 150;
            }
            Set<Integer> visited = new HashSet<>();
            Deque<Integer> sameType = new ArrayDeque<>();
            Deque<Integer> allTypes = new ArrayDeque<>();
            allTypes.push(0);
            Deque<List<Integer>> areas = new LinkedList<>();

            while (!sameType.isEmpty() || !allTypes.isEmpty()) {
                int centerPosition;
                if (!sameType.isEmpty()) {
                    centerPosition = sameType.pop();
                    if (!visited.contains(centerPosition)) {
                        areas.peekLast().add(centerPosition);
                    } else {
                        continue;
                    }
                } else {
                    centerPosition = allTypes.pop();
                    if (!visited.contains(centerPosition)) {
                        List<Integer> newArea = new ArrayList<>();
                        newArea.add(centerPosition);
                        areas.add(newArea);
                    } else {
                        continue;
                    }
                }
                visited.add(centerPosition);
                BackgroundType centerBackgroundType =
                        backgroundTypes[gameManager.getUtils().getX(centerPosition)][gameManager.getUtils().getY(centerPosition)];
                BackgroundType relativeBackgroundType;
                AreaType areaType;
                List<Integer> neighboringCoordinates = new LinkedList<>();
                neighboringCoordinates.add(gameManager.getUtils().getTopMidPosition(centerPosition));
                neighboringCoordinates.add(gameManager.getUtils().getBottomMidPosition(centerPosition));
                neighboringCoordinates.add(gameManager.getUtils().getMidLeftPosition(centerPosition));
                neighboringCoordinates.add(gameManager.getUtils().getMidRightPosition(centerPosition));

                for (Integer neighboringCoordinate : neighboringCoordinates) {
                    if (gameManager.getUtils().isValidCoordinate(neighboringCoordinate) && !visited.contains(neighboringCoordinate)) {
                        relativeBackgroundType =
                                backgroundTypes[gameManager.getUtils().getX(neighboringCoordinate)][gameManager.getUtils().getY(neighboringCoordinate)];
                        areaType = AreaType.getCommonBackgroundAreaType(relativeBackgroundType, centerBackgroundType);
                        if (areaType != AreaType.UNDEFINED) {
                            sameType.push(neighboringCoordinate);
                        } else {
                            allTypes.push(neighboringCoordinate);
                        }
                    }
                }
            }
            return areas.stream().filter(positions -> positions.size() >= threshold).collect(Collectors.toList());
        }
    }

    public interface BackgroundCreationStrategy {
        default BackgroundType[][] create(IGameManager gameManager) {
            BackgroundType[][] result = new BackgroundType[gameManager.getWidth()][gameManager.getHeight()];

            float[][] elevationNoise =
                    PerlinNoiseGenerator.generatePerlinNoise(gameManager.getWidth(), gameManager.getHeight(), 7);
            float[][] humidityNoise =
                    PerlinNoiseGenerator.generatePerlinNoise(gameManager.getWidth(), gameManager.getHeight(), 6);
            float[][] temperatureNoise =
                    PerlinNoiseGenerator.generatePerlinNoise(gameManager.getWidth(), gameManager.getHeight(), 5);

            BackgroundType nearest;
            double minDistance;
            double currentDistance;
            for (int x = 0; x < gameManager.getWidth(); x++) {
                for (int y = 0; y < gameManager.getHeight(); y++) {
                    nearest = BackgroundType.GRASSLAND1;
                    minDistance = 100.0;
                    for (BackgroundType backgroundType : BackgroundType.values()) {
                        currentDistance =
                                backgroundType.getDistance(elevationNoise[x][y], humidityNoise[x][y], temperatureNoise[x][y]);
                        if (currentDistance < minDistance) {
                            minDistance = currentDistance;
                            nearest = backgroundType;
                        }
                    }
                    result[x][y] = nearest;
                }
            }
            return result;
        }
    }
}
