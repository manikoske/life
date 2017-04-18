package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * manikoske on 15. 4. 2017.
 */
public class Tests {

    private IGameManager gameManager;

    @Before
    public void setUp() {

        gameManager = new IGameManager() {
            @Override
            public void update() {

            }

            @Override
            public void addLayers(TiledMap map) {

            }

            @Override
            public int getWidth() {
                return 3;
            }

            @Override
            public int getHeight() {
                return 4;
            }

            @Override
            public GameUtils getUtils() {
                return new GameUtils(gameManager);
            }

            @Override
            public GameState getState() {
                return new InitialGameStateBuilder(gameManager) {
                    @Override
                    protected BackgroundType[][] createBackground(IGameManager gameManager) {
                        BackgroundType[][] backgroundTypes = new BackgroundType[getWidth()][getHeight()];
                        backgroundTypes[0][0] = BackgroundType.DESERT;
                        backgroundTypes[0][1] = BackgroundType.DESERT;
                        backgroundTypes[0][2] = BackgroundType.DESERT;
                        backgroundTypes[0][3] = BackgroundType.DESERT;
                        backgroundTypes[1][0] = BackgroundType.DESERT;
                        backgroundTypes[1][1] = BackgroundType.DESERT;
                        backgroundTypes[1][2] = BackgroundType.DESERT;
                        backgroundTypes[1][3] = BackgroundType.DESERT;
                        backgroundTypes[2][0] = BackgroundType.DESERT;
                        backgroundTypes[2][1] = BackgroundType.DESERT;
                        backgroundTypes[2][2] = BackgroundType.SWAMP;
                        backgroundTypes[2][3] = BackgroundType.SWAMP;
                        return backgroundTypes;
                    }
                }.build();
            }

            @Override
            public IGameResources getResources() {
                return null;
            }
        };


    }

    @Test
    public void test() {
        Assert.assertNotNull(gameManager);
    }
}