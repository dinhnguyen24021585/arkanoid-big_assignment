package com.example.arkanoid.PowerUps;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameElements.Ball;
import com.example.arkanoid.GameEngine;
import javafx.scene.image.Image;

public class FastBallPowerUp extends PowerUp {
    private Image image;
    private int currSpeed;

    public FastBallPowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 2);
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerUpImages/powerup_fast.png"));
    }

    @Override
    public void applyEffect() {
        if (GameEngine.getBall() == null || effectActive) return;

        Ball ball = GameEngine.getBall();
        this.currSpeed = ball.getSpeed();

        // MAIN BALL
        ball.setSpeed(currSpeed * 2);
        ball.setDx(ball.getDirectionX() * ball.getSpeed());
        ball.setDy(ball.getDirectionY() * ball.getSpeed());

        // THÊM EXTRA BALLS
        for (Ball extraBall : GameEngine.getExtraBalls()) {
            extraBall.setSpeed(currSpeed * 2);
            extraBall.setDx(extraBall.getDirectionX() * extraBall.getSpeed());
            extraBall.setDy(extraBall.getDirectionY() * extraBall.getSpeed());
        }

        startEffectTimer();
        effectActive = true;
    }

    @Override
    public void removeEffect() {
        if (!isEffectActive()) return;

        Ball ball = GameEngine.getBall();
        if (ball != null) {
            ball.setSpeed(GameConst.DefaultSpeed);
            ball.setDx(ball.getDirectionX() * GameConst.DefaultSpeed);
            ball.setDy(ball.getDirectionY() * GameConst.DefaultSpeed);
        }

        for (Ball extraBall : GameEngine.getExtraBalls()) {
            extraBall.setSpeed(GameConst.DefaultSpeed);
            extraBall.setDx(extraBall.getDirectionX() * GameConst.DefaultSpeed);
            extraBall.setDy(extraBall.getDirectionY() * GameConst.DefaultSpeed);
        }
        effectActive = false;
    }

    public Image getImage() {
        return image;
    }

    public boolean isEffectActive() {
        return effectActive;
    }
}