package com.example.arkanoid;

public class StrongBrick extends Brick {
    public StrongBrick(int x, int y, int hits, int type) {
        super(x, y, GameConst.BrickWidth, GameConst.BrickHeight, hits, type);
    }


    @Override
    public int takeHits() {
        if (!destroyed) {
            type--;
            if (type <= 0) {
                destroyed = true;
                return hitPoints;
            } else {
                loadImage();
            }
        }
        return 0;
    }
}
