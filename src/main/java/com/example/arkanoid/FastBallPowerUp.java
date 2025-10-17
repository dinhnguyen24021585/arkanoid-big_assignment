package com.example.arkanoid;

import javafx.scene.image.Image;

public class FastBallPowerUp extends PowerUp {
    private Image image;
    private Integer originalSpeed = null;

    public FastBallPowerUp(int x, int y) {
        super(x, y, 30, 30, 2);
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerup_fast.png"));
    }

    @Override
    public void applyEffect(GameEngine gameEngine) {
        if (gameEngine == null || gameEngine.getBall() == null || effectApplied) return;

        Ball ball = gameEngine.getBall();
        if (originalSpeed == null) {
            this.originalSpeed = ball.getSpeed();
        }

        ball.setSpeed(originalSpeed * 2);
        ball.setDx(ball.getDirectionX() * ball.getSpeed());
        ball.setDy(ball.getDirectionY() * ball.getSpeed());

        startEffectTimer();
    }

    @Override
    public void removeEffect(GameEngine gameEngine) {
        if (gameEngine == null || gameEngine.getBall() == null || !isEffectActive()) return;

        Ball ball = gameEngine.getBall();
        if (originalSpeed != null) {
            ball.setSpeed(originalSpeed);
            ball.setDx(ball.getDirectionX() * originalSpeed);
            ball.setDy(ball.getDirectionY() * originalSpeed);
        }
    }

    public Image getImage() {
        return image;
    }

    public boolean isEffectActive() {
        return effectActive;
    }
}