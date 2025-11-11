package com.example.arkanoid.Bricks;

import com.example.arkanoid.GameElements.Brick;
import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameElements.Sound;

public class StrongBrick extends Brick {
    public StrongBrick(int x, int y, int hits, int type) {
        super(x, y, GameConst.BrickWidth, GameConst.BrickHeight, hits, type);
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
