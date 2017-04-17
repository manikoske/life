package com.mygdx.game;

import java.util.List;

/**
 * manikoske on 17. 4. 2017.
 */
public class Area {

    private AreaType type;
    private String name;
    private List<Integer> includedTilePositions;

    public Area(AreaType type, String name, List<Integer> includedTilePositions) {
        this.type = type;
        this.name = name;
        this.includedTilePositions = includedTilePositions;
    }

    public AreaType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getIncludedTilePositions() {
        return includedTilePositions;
    }
}
