package com.example.arkanoid.GameElements.Bricks.factories;

import com.example.arkanoid.GameElements.Bricks.Brick;
import com.example.arkanoid.GameElements.Bricks.NormalBrick;

public class NormalBrickFactory implements BrickFactory {

    @Override
    public Brick createBrick(int x, int y, int type) {
        return new NormalBrick(x,y);
    }
}
