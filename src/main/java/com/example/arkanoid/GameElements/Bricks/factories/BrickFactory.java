package com.example.arkanoid.GameElements.Bricks.factories;

import com.example.arkanoid.GameElements.Bricks.Brick;

public interface BrickFactory {
   public abstract Brick createBrick(int x, int y, int type);
}
