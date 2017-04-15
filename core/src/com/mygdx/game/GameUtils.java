package com.mygdx.game;


/**
 * manikoske on 15. 4. 2017.
 */
public class GameUtils {

    private static GameUtils instance;
    private final int width;
    private final int height;

    protected GameUtils(int width, int height) {
        this.width = width;
        this.height = height;
        GameUtils.instance = this;
    }

    public static GameUtils get() {
        return GameUtils.instance;
    }

    public int toPosition(int x, int y) {
        return x * height + y;
    }

    public int getX(int position) {
        return position / height;
    }

    public int getY(int position) {
        return position % height;
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isValidCoordinate(int position) {
        return (position >= 0 && position < width * height);
    }

    public int getTopLeftPosition(int position) {
        int x = getX(position) - 1;
        int y = getY(position) - 1;
        if (isValidCoordinate(x, y)) {
            return toPosition(x, y);
        } else {
            return -1;
        }
    }

    public int getTopMidPosition(int position) {
        int x = getX(position);
        int y = getY(position) - 1;
        if (isValidCoordinate(x, y)) {
            return toPosition(x, y);
        } else {
            return -1;
        }
    }

    public int getTopRightPosition(int position) {
        int x = getX(position) + 1;
        int y = getY(position) - 1;
        if (isValidCoordinate(x, y)) {
            return toPosition(x, y);
        } else {
            return -1;
        }
    }

    public int getMidLeftPosition(int position) {
        int x = getX(position) - 1;
        int y = getY(position);
        if (isValidCoordinate(x, y)) {
            return toPosition(x, y);
        } else {
            return -1;
        }
    }

    public int getMidRightPosition(int position) {
        int x = getX(position) + 1;
        int y = getY(position);
        if (isValidCoordinate(x, y)) {
            return toPosition(x, y);
        } else {
            return -1;
        }
    }

    public int getBottomLeftPosition(int position) {
        int x = getX(position) - 1;
        int y = getY(position) + 1;
        if (isValidCoordinate(x, y)) {
            return toPosition(x, y);
        } else {
            return -1;
        }
    }

    public int getBottomMidPosition(int position) {
        int x = getX(position);
        int y = getY(position) + 1;
        if (isValidCoordinate(x, y)) {
            return toPosition(x, y);
        } else {
            return -1;
        }
    }

    public int getBottomRightPosition(int position) {
        int x = getX(position) + 1;
        int y = getY(position) + 1;
        if (isValidCoordinate(x, y)) {
            return toPosition(x, y);
        } else {
            return -1;
        }
    }
}
