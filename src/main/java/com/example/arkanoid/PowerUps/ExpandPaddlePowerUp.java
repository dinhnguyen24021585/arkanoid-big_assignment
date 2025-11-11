package com.example.arkanoid.PowerUps;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameElements.Paddle;
import com.example.arkanoid.GameEngine;

import javafx.scene.image.Image;

public class ExpandPaddlePowerUp extends PowerUp {
    private Image image;
    private int currWidth;

    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 1);
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerUpImages/powerup_expand.png"));
    }

    @Override
    public void applyEffect() {
        if (GameEngine.getPaddle() == null || effectActive) return;

        this.currWidth = GameEngine.getPaddle().getWidth();


        int newWidth = (int) (currWidth * 1.5);
        int maxWidth = (int) (GameConst.WIDTH * 0.8);
        if (newWidth > maxWidth) newWidth = maxWidth;

        int centerX = GameEngine.getPaddle().getX() + (GameEngine.getPaddle().getWidth() / 2);
        GameEngine.getPaddle().setX(centerX - (newWidth / 2));
        GameEngine.getPaddle().setWidth(newWidth);

        startEffectTimer();

    }

    @Override
    public void removeEffect() {
        if (GameEngine.getPaddle() == null || !isEffectActive()) return;

        Paddle paddle = GameEngine.getPaddle();

        int centerX = GameEngine.getPaddle().getX() + (GameEngine.getPaddle().getWidth() / 2);
        GameEngine.getPaddle().setX(centerX - (currWidth / 2));
        GameEngine.getPaddle().setWidth(GameConst.PaddleWidth);

    }

    public Image getImage() {
        return image;
    }

    public boolean isEffectActive() {
        return effectActive;
    }
}
