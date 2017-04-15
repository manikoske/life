package com.mygdx.game;

/**
 * manikoske on 17. 3. 2017.
 */
public enum BackgroundType {

    WATER_SEA_DEEP(.1f, .5f, .5f),
    WATER_SEA_MEDIUM(.2f, 0.5f, 0.5f),
    WATER_SEA_SHALLOW(.35f, 0.5f, 0.5f),

    WATER_LAKE(.5f, 0.5f, 0.5f),
    WATER_HIGHLAND(.8f, 0.9f, 0.2f),

    DESERT(.4f, .3f, .7f),
    WASTELAND(.5f, .15f, .75f),
    GRASSLAND_TREE_DEAD(.80f, .15f, .15f),

    GRASSLAND1(.65f, .65f, .65f),
    GRASSLAND2(.75f, .3f, .3f),
    GRASSLAND3(.35f, .35f, .35f),
    GRASSLAND_HILL(.65f, .25f, .25f),

    SWAMP(.35f, .75f, .75f),
    SWAMP_TREE(.4f, .8f, .8f),

    GRASSLAND_TREE_OAK(.78f, .65f, .6f),
    GRASSLAND_TREE_BIRCH(.77f, .65f, .6f),

    GRASSLAND_TREE_PINE(.8f, .65f, .4f),
    SNOW_TREE_PINE(.8f, .65f, .3f),

    DESERT_TREE_PALM(.4f, .13f, .85f),
    DESERT_TREE_CACTUS(.4f, .15f, .80f),

    SNOW_MOUNTAIN(.8f, .7f, .2f),
    WASTELAND_MOUNTAIN(.9f, .05f, .05f),
    DESERT_MOUNTAIN(.8f, .1f, .8f),
    GRASSLAND_MOUNTAIN(.85f, .35f, .35f),
    ;

    private float elevation;
    private float humidity;
    private float temperature;

    BackgroundType(float elevation, float humidity, float temperature) {
        this.elevation = elevation;
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public double getDistance(float elevation, float humidity, float temperature) {
        return Math.sqrt(((this.elevation - elevation) * (this.elevation - elevation)) +
                ((this.humidity - humidity) * (this.humidity - humidity)) +
                ((this.temperature - temperature) * (this.temperature - temperature)));
    }
}
