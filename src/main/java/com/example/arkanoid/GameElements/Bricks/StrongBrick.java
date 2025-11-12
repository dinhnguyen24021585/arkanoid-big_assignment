package com.example.arkanoid.GameElements.Bricks;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameElements.Sound;
import com.example.arkanoid.GameEngine;

public class StrongBrick extends Brick {
    public StrongBrick(int x, int y, int hits, int type) {
        super(x, y, GameConst.BrickWidth, GameConst.BrickHeight, hits, type);
        GameEngine.getInstance().getLevel().setNumOfBricksToLvlUp(GameEngine.getInstance().getLevel().getNumOfBricksToLvlUp() + 1);
    }

    @Override
    public int takeHits() {
        if (!destroyed) {
            Sound.playSFX("Hit.wav");
            type--;
            if (type <= 0) {
                destroyed = true;
                Sound.playSFX("Break.wav");
                return hitPoints;
            } else {
                loadImage();
            }
        }
        return 0;
    }
}
