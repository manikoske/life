package com.mygdx.game;

/**
 * Created by manikoske on 17. 3. 2017.
 */
public class Connection {

    int occurent;
    int opposite;
    int undefinedWeight;
    int definedWeight;

    public Connection(int occurent, int opposite, int undefinedWeight, int definedWeight) {
        this.occurent = occurent;
        this.opposite = opposite;
        this.undefinedWeight = undefinedWeight;
        this.definedWeight = definedWeight;
    }
}
