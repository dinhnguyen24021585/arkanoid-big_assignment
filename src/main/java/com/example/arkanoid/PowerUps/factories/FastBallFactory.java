package com.example.arkanoid.PowerUps.factories;

import com.example.arkanoid.PowerUps.FastBallPowerUp;
import com.example.arkanoid.PowerUps.PowerUp;

public class FastBallFactory implements PowerUpFactories{
    @Override
    public PowerUp createPowerUp(int x, int y) {
        return new FastBallPowerUp(x,y);
    }
}
