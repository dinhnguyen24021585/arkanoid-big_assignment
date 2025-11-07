package com.example.arkanoid;

public class NormalBrick extends  Brick {
    public NormalBrick(int x, int y) {
        super(x, y, GameConst.BrickWidth, GameConst.BrickHeight, 1, 1);
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
