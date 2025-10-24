package com.example.arkanoid;

public class UnbreakableBrick extends Brick {
    public UnbreakableBrick(int x, int y) {
        super(x, y, GameConst.BrickWidth, GameConst.BrickHeight, 0, Integer.MAX_VALUE);
    }

    @Override
    public int takeHits() {
        return 0;
    }
}
