package com.example.arkanoid;

import javafx.scene.image.Image;

public class ReverseControlPowerUp extends PowerUp {
    private Image image;

    public ReverseControlPowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 6);
        this.duration = 10000;
        try {
            this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerup_reverse.jpg"));
        } catch (Exception e) {
            this.image = null;
        }
    }

    @Override
    public void applyEffect() {
        if (effectActive) return;

        effectActive = true;
        startEffectTimer();
        GameEngine.setReverseControls(true);
    }

    @Override
    public void removeEffect() {
        if (!effectActive) return;

        GameEngine.setReverseControls(false);
        effectActive = false;
        effectStartTime = 0;
    }

    public Image getImage() {
        return image;
    }
}