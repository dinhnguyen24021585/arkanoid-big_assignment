package com.example.arkanoid.GameElements.Bricks.factories;

import com.example.arkanoid.GameElements.Bricks.Brick;
import com.example.arkanoid.GameElements.Bricks.StrongBrick;

public class StrongBrickFactory implements BrickFactory{

    @Override
    public Brick createBrick(int x, int y, int type) {
        return new StrongBrick(x,y,type,type);
    }
}
