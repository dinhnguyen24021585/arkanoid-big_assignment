package com.example.arkanoid;

import javafx.scene.image.Image;

public class FastBallPowerUp extends PowerUp {
    private Image image;
    private int currSpeed;

    public FastBallPowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 2);
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerup_fast.png"));
    }

    @Override
    public void applyEffect() {
        if (GameEngine.getBall() == null || effectActive) return;

        Ball ball = GameEngine.getBall();
        this.currSpeed = ball.getSpeed();

        ball.setSpeed(currSpeed * 2);
        ball.setDx(ball.getDirectionX() * ball.getSpeed());
        ball.setDy(ball.getDirectionY() * ball.getSpeed());

        startEffectTimer();
    }

    @Override
    public void removeEffect() {
        if (GameEngine.getBall() == null || !isEffectActive()) return;

        Ball ball = GameEngine.getBall();
        ball.setSpeed(GameConst.DefaultSpeed);
        ball.setDx(ball.getDirectionX() * GameConst.DefaultSpeed);
        ball.setDy(ball.getDirectionY() * GameConst.DefaultSpeed);
    }

    public Image getImage() {
        return image;
    }

    public boolean isEffectActive() {
        return effectActive;
    }
}
