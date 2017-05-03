package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;


/**
 * manikoske on 15. 4. 2017.
 */
public class Tests {

    private IGameManager gameManager;

    @Before
    public void setUp() {
        gameManager = new IGameManager() {
            @Override
            public void executeCycle() {

            }

            @Override
            public GameUtils getUtils() {
                return null;
            }

            @Override
            public GameState getState() {
                return new InitialGameStateBuilder(this) {
                    @Override
                    protected List<Actor> createActors(IGameManager gameManager) {
                        return Collections.singletonList(new Actor(17f, 21f, 20, ""));
                    }
                }.build();
            }

            @Override
            public IGameSettings getSettings() {
                return new IGameSettings() {
                    @Override
                    public int getTileDimensions() {
                        return 10;
                    }

                    @Override
                    public int getHorizontalTileCount() {
                        return 5;
                    }

                    @Override
                    public int getVerticalTileCount() {
                        return 5;
                    }

                    @Override
                    public int getWidth() {
                        return 50;
                    }

                    @Override
                    public int getHeight() {
                        return 50;
                    }

                    @Override
                    public int getMinimalActorCount() {
                        return 0;
                    }

                    @Override
                    public int getInitialRadius() {
                        return 0;
                    }

                    @Override
                    public int getInitialBroadPhaseBoxDimensions() {
                        return 20;
                    }
                };
            }

            @Override
            public IGameRendering getRendering() {
                return null;
            }
        };
    }

    @Test
    public void test() {
        Assert.assertNotNull(gameManager);
        gameManager.getState();
    }
}