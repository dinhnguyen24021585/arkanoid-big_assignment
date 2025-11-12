package com.example.arkanoid.PowerUps.factories;

import com.example.arkanoid.PowerUps.PowerUp;
import com.example.arkanoid.PowerUps.ReverseControlPowerUp;

public class ReverseFactory implements PowerUpFactories{
    @Override
    public PowerUp createPowerUp(int x, int y) {
        return new ReverseControlPowerUp(x,y);
    }
}
