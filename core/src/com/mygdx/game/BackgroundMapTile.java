package com.mygdx.game;

/**
 * manikoske on 17. 3. 2017.
 */
public enum BackgroundMapTile {

    WATER_SEA_DEEP(.1f, .5f, .5f, 2, 2),
    WATER_SEA_MEDIUM(.2f, 0.5f, 0.5f, 2, 1),
    WATER_SEA_SHALLOW(.35f, 0.5f, 0.5f, 2, 0),
    WATER_LAKE(.5f, 0.5f, 0.5f, 2, 0),
    WATER_HIGHLAND(.8f, 0.9f, 0.2f, 12, 0),
    DESERT(.4f, .3f, .7f, 1, 3),
    WASTELAND(.5f, .15f, .75f, 3, 0),
    GRASSLAND1(.65f, .65f, .65f, 1, 2),
    GRASSLAND2(.50f, .50f, .50f, 1, 1),
    GRASSLAND3(.35f, .35f, .35f, 1, 0),
    RAIN_FOREST(.35f, .75f, .75f, 2, 3),
    GRASSLAND_HILL(.65f, .25f, .25f, 10, 3),
    GRASSLAND_TREE_OAK(.78f, .65f, .6f, 6, 0),
    GRASSLAND_TREE_BIRCH(.77f, .65f, .6f, 6, 3),
    GRASSLAND_TREE_PINE(.8f, .65f, .4f, 6, 6),
    GRASSLAND_TREE_DEAD(.80f, .15f, .15f, 15, 8),
    DESERT_TREE_PALM(.4f, .13f, .85f, 7, 6),
    DESERT_TREE_CACTUS(.4f, .15f, .80f, 16, 6),
    SNOW_TREE_PINE(.8f, .65f, .3f, 10, 0),
    RAIN_FOREST_TREE(.4f, .8f, .8f, 7, 0),
    SNOW_MOUNTAIN(.8f, .7f, .2f, 9, 6),
    WASTELAND_MOUNTAIN(.9f, .05f, .05f, 9, 0),
    DESERT_MOUNTAIN(.8f, .1f, .8f, 13, 6),
    GRASSLAND_MOUNTAIN(.85f, .35f, .35f, 13, 0),
    TAIGA(.75f, .3f, .3f, 4, 6);

    private float elevation;
    private float humidity;
    private float temperature;
    private int textureIndexX;
    private int textureIndexY;

    BackgroundMapTile(float elevation, float humidity, float temperature, int textureIndexX, int textureIndexY) {
        this.elevation = elevation;
        this.humidity = humidity;
        this.temperature = temperature;
        this.textureIndexX = textureIndexX;
        this.textureIndexY = textureIndexY;
    }

    public int getTextureIndexX() {
        return textureIndexX;
    }

    public int getTextureIndexY() {
        return textureIndexY;
    }

    public double getDistance(float elevation, float humidity, float temperature) {
        return Math.sqrt(((this.elevation - elevation) * (this.elevation - elevation)) +
                ((this.humidity - humidity) * (this.humidity - humidity)) +
                ((this.temperature - temperature) * (this.temperature - temperature)));
    }
}
