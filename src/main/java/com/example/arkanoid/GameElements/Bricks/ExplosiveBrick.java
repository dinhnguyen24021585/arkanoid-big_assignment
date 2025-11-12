package com.example.arkanoid.GameElements.Bricks;


import java.util.ArrayList;

import com.example.arkanoid.*;
import com.example.arkanoid.GameElements.Sound;

public class ExplosiveBrick extends Brick {
    public ExplosiveBrick(int x, int y) {
        super(x, y, GameConst.BrickWidth, GameConst.BrickHeight, 1, -1);
        GameEngine.getLevel().setNumOfBricksToLvlUp(GameEngine.getLevel().getNumOfBricksToLvlUp() + 1);
    }

    @Override
    public int takeHits() {
        if (!destroyed) {
            destroyed = true;
            Sound.playSFX("Explosive.wav");
            explode(GameEngine.getBricks());
            return hitPoints;
        }
        return 0;
    }

    private void explode(ArrayList<Brick> bricks) {
        for (Brick b : bricks) {
            if (b == this || b.isDestroyed() || b instanceof UnbreakableBrick) continue;

            if (isNear(b)) {
                if (b instanceof ExplosiveBrick explosive) {
                    explosive.takeHits();
                } else {
                    b.takeHits();
                }
            }
        }
    }

    private boolean isNear(Brick b) {
        int dx = Math.abs(b.getX() - this.getX());
        int dy = Math.abs(b.getY() - this.getY());
        return dx <= 90 && dy <= 40 ;
    }
}
