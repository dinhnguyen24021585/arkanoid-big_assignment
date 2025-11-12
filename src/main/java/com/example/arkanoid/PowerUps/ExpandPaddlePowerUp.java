package com.example.arkanoid.PowerUps;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameElements.Paddle;
import com.example.arkanoid.GameEngine;
import javafx.scene.image.Image;

public class ExpandPaddlePowerUp extends PowerUp {
    private Image image;
    private static final int MAX_PADDLE_WIDTH = GameConst.WIDTH / 2;

    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 1);
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerUpImages/powerup_expand.png"));
        this.duration = 8000;
    }

    @Override
    public void applyEffect() {
        if (effectActive) {
            resetExpandTimer();
            return;
        }

        effectActive = true;
        startEffectTimer();
        applyPaddleExpand();
    }

    @Override
    public void removeEffect() {
        if (!effectActive) return;

        restorePaddleSize();
        effectActive = false;
        effectStartTime = 0;
    }

    private void applyPaddleExpand() {
        Paddle paddle = GameEngine.getInstance().getPaddle();
        if (paddle == null) return;

        int currentWidth = paddle.getWidth();
        int newWidth = (int) (currentWidth * 1.5);

        if (newWidth > MAX_PADDLE_WIDTH) {
            newWidth = MAX_PADDLE_WIDTH;
        }

        updatePaddleSize(paddle, newWidth);
    }

    private void updatePaddleSize(Paddle paddle, int newWidth) {
        int mouseX = getCurrentMouseX();
        int newX = mouseX - (newWidth / 2);
        newX = enforceBoundaries(newX, newWidth);

        paddle.setWidth(newWidth);
        paddle.setX(newX);
    }

    private int enforceBoundaries(int x, int width) {
        if (x < 0) {
            x = 0;
        }

        if (x + width > GameConst.WIDTH) {
            x = GameConst.WIDTH - width;
        }
        return x;
    }

    private int getCurrentMouseX() {
        Paddle paddle = GameEngine.getInstance().getPaddle();
        if (paddle != null) {
            return paddle.getX() + (paddle.getWidth() / 2);
        }
        return GameConst.WIDTH / 2;
    }

    private void restorePaddleSize() {
        Paddle paddle = GameEngine.getInstance().getPaddle();
        if (paddle != null) {
            int mouseX = getCurrentMouseX();
            int newX = mouseX - (GameConst.PaddleWidth / 2);

            newX = enforceBoundaries(newX, GameConst.PaddleWidth);

            paddle.setWidth(GameConst.PaddleWidth);
            paddle.setX(newX);
        }
    }

    private void resetExpandTimer() {
        effectStartTime = System.currentTimeMillis();
    }

    public Image getImage() {
        return image;
    }
}