package com.example.arkanoid.PowerUps;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameEngine;
import javafx.scene.image.Image;

public class HeartPowerUp extends PowerUp {
    private Image image;
    private static final int MAX_HEARTS = 10;

    public HeartPowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 3);
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerUpImages/powerup_heart.png"));
    }

    @Override
    public void applyEffect() {
        if (effectActive) return;
        if (GameEngine.getInstance().getLives() < MAX_HEARTS) {

            GameEngine.getInstance().setLives(GameEngine.getLives() + 1);

            effectActive = true;
        }
    }

    @Override
    public void removeEffect() {
        effectActive = false;
    }

    @Override
    public boolean isEffectExpired() {
        return false;
    }

    public Image getImage() {
        return image;
    }

    public static void resetHeartCounter() {
        GameEngine.setLives(0);
    }

    public static int getTotalHeartsCollected() {
        return GameEngine.getLives();
    }

    public static int getMaxHearts() {
        return MAX_HEARTS;
    }
}