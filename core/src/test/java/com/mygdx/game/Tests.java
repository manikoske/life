package com.mygdx.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * manikoske on 15. 4. 2017.
 */
public class Tests {

    private GameState state;
    private GameUtils utils;

    @Before
    public void setUp() {

    }

    @Test
    public void test() {
        utils = new GameUtils(3, 4);
        state = new GameState(3, 4);
        state.getTile(0, 0).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(0, 1).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(0, 2).setBackgroundType(BackgroundType.DESERT);
        state.getTile(0, 3).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(1, 0).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(1, 1).setBackgroundType(BackgroundType.GRASSLAND2);
        state.getTile(1, 2).setBackgroundType(BackgroundType.DESERT);
        state.getTile(1, 3).setBackgroundType(BackgroundType.GRASSLAND2);
        state.getTile(2, 0).setBackgroundType(BackgroundType.GRASSLAND2);
        state.getTile(2, 1).setBackgroundType(BackgroundType.DESERT);
        state.getTile(2, 2).setBackgroundType(BackgroundType.GRASSLAND2);
        state.getTile(2, 3).setBackgroundType(BackgroundType.GRASSLAND2);
        state.createAreas();
        Assert.assertNotNull(state);
    }

    @Test
    public void test2() {
        utils = new GameUtils(3, 3);
        state = new GameState(3, 3);
        state.getTile(0, 0).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(0, 1).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(0, 2).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(1, 0).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(1, 1).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(1, 2).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(2, 0).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(2, 1).setBackgroundType(BackgroundType.SWAMP);
        state.getTile(2, 2).setBackgroundType(BackgroundType.GRASSLAND2);
        state.createAreas();
        Assert.assertNotNull(state);
    }

}