package com.example.arkanoid.Bricks;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameElements.Brick;
import com.example.arkanoid.GameElements.Sound;


public class UnbreakableBrick extends Brick {
    public UnbreakableBrick(int x, int y) {
        super(x, y, GameConst.BrickWidth, GameConst.BrickHeight, 0, Integer.MAX_VALUE);
    }

    @Override
    public int takeHits() {
        Sound.playSFX("Hit.wav");
        return 0;
    }
}
