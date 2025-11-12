package com.example.arkanoid.PowerUps.factories;

import com.example.arkanoid.PowerUps.PowerUp;
import com.example.arkanoid.PowerUps.ShootingPowerUp;

public class ShootingFactory implements PowerUpFactories{

    @Override
    public PowerUp createPowerUp(int x, int y) {
        return new ShootingPowerUp(x,y);
    }
}
