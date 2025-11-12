package com.example.arkanoid.GameElements.Bricks.factories;

import com.example.arkanoid.GameElements.Bricks.Brick;
import com.example.arkanoid.GameElements.Bricks.StrongBrick;
import com.example.arkanoid.GameElements.Bricks.UnbreakableBrick;

public class UnbreakableBrickFactory implements BrickFactory {
    @Override
    public Brick createBrick(int x, int y, int type) {
        return new UnbreakableBrick(x,y);
    }
}
