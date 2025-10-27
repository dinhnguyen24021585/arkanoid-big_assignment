package com.example.arkanoid;

import javafx.scene.image.Image;

public class HeartPowerUp extends PowerUp {
    private Image image;
    private static final int MAX_HEARTS = 10;

    public HeartPowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 3);
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerup_heart.png"));
    }

    @Override
    public void applyEffect() {
        if (effectActive) return;
        System.out.println(2);
        if (GameEngine.getLives() < MAX_HEARTS) {

            GameEngine.setLives(GameEngine.getLives() + 1);

            //effectApplied = true;
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
