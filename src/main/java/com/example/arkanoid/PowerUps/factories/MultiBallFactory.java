package com.example.arkanoid.PowerUps.factories;

import com.example.arkanoid.PowerUps.BalancedMultiballPowerUp;
import com.example.arkanoid.PowerUps.PowerUp;

public class MultiBallFactory implements PowerUpFactories {
    @Override
    public PowerUp createPowerUp(int x, int y) {
        return new BalancedMultiballPowerUp(x,y);
    }
}
