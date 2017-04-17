package com.mygdx.game;

import java.util.Arrays;
import java.util.Collection;


/**
 * manikoske on 15. 4. 2017.
 */
public enum AreaType {

    UNDEFINED,
    SEA(BackgroundType.WATER_SEA_DEEP, BackgroundType.WATER_SEA_MEDIUM, BackgroundType.WATER_SEA_SHALLOW),
    LAKE(BackgroundType.WATER_LAKE, BackgroundType.WATER_HIGHLAND),
    DESERT(BackgroundType.DESERT, BackgroundType.WASTELAND, BackgroundType.GRASSLAND_TREE_DEAD, BackgroundType.DESERT_MOUNTAIN),
    GRASSLAND_HUMID(BackgroundType.GRASSLAND3),
    GRASSLAND_SUNNY(BackgroundType.GRASSLAND1),
    GRASSLAND_MILD(BackgroundType.GRASSLAND2),
    GRASSLAND_HILLS(BackgroundType.GRASSLAND_HILL),
    MARSHLANDS(BackgroundType.SWAMP, BackgroundType.SWAMP_TREE),
    LEAF_WOOD(BackgroundType.GRASSLAND_TREE_OAK, BackgroundType.GRASSLAND_TREE_BIRCH),
    PINE_WOOD(BackgroundType.GRASSLAND_TREE_PINE, BackgroundType.SNOW_TREE_PINE),
    OASIS(BackgroundType.DESERT_TREE_PALM, BackgroundType.DESERT_TREE_CACTUS),
    MOUNTAINS_SNOW(BackgroundType.SNOW_MOUNTAIN),
    MOUNTAINS_WASTELAND(BackgroundType.WASTELAND_MOUNTAIN),
    MOUNTAINS_GRASSLAND(BackgroundType.GRASSLAND_MOUNTAIN),
    ;

    private Collection<BackgroundType> commonTypes;

    AreaType(BackgroundType... commonTypes) {
        this.commonTypes = Arrays.asList(commonTypes);
    }

    public Collection<BackgroundType> getCommonTypes() {
        return commonTypes;
    }

    public static AreaType getAreaType(BackgroundType type) {
        for (AreaType areaType : values()) {
            if (areaType.getCommonTypes().contains(type)) return areaType;
        }
        return UNDEFINED;
    }

    public static AreaType getCommonBackgroundAreaType(BackgroundType type1, BackgroundType type2) {
        for (AreaType areaType : values()) {
            if (areaType.getCommonTypes().contains(type1) &&
                    areaType.getCommonTypes().contains(type2)) {
                return areaType;
            }
        }
        return UNDEFINED;
    }

}
