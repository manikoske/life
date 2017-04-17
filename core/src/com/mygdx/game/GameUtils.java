package com.mygdx.game;


/**
 * manikoske on 15. 4. 2017.
 */
public class GameUtils {

    IGameManager gameManager;

    protected GameUtils(IGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public int toPosition(int x, int y) {
        return x * gameManager.getHeight() + y;
    }

    public int getX(int position) {
        return position / gameManager.getHeight();
    }

    public int getY(int position) {
        return position % gameManager.getHeight();
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < gameManager.getWidth() && y >= 0 && y < gameManager.getHeight();
    }

    public boolean isValidCoordinate(int position) {
        return (position >= 0 && position < gameManager.getWidth() * gameManager.getHeight());
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
