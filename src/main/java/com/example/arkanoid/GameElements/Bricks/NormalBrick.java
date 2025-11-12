package com.example.arkanoid.GameElements.Bricks;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameElements.Sound;
import com.example.arkanoid.GameEngine;

public class NormalBrick extends Brick {
    public NormalBrick(int x, int y) {
        super(x, y, GameConst.BrickWidth, GameConst.BrickHeight, 1, 1);
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
