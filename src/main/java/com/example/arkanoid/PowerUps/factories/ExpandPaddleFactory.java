package com.example.arkanoid.PowerUps.factories;

import com.example.arkanoid.PowerUps.ExpandPaddlePowerUp;
import com.example.arkanoid.PowerUps.PowerUp;

public class ExpandPaddleFactory implements PowerUpFactories{
    @Override
    public PowerUp createPowerUp(int x, int y) {
        return new ExpandPaddlePowerUp(x,y);
    }
}
