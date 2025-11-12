package com.example.arkanoid.PowerUps.factories;

import com.example.arkanoid.PowerUps.HeartPowerUp;
import com.example.arkanoid.PowerUps.PowerUp;

public class HeartFactory implements PowerUpFactories{
    @Override
    public PowerUp createPowerUp(int x, int y) {
        return new HeartPowerUp(x,y);
    }
}
