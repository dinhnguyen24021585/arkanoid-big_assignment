package com.example.arkanoid;

import javafx.scene.image.Image;

public class ExpandPaddlePowerUp extends PowerUp {
    private Image image;
    private Integer originalWidth = null;

    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, 30, 30, 1);
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerup_expand.png"));
    }

    @Override
    public void applyEffect(GameEngine gameEngine) {
        if (gameEngine == null || gameEngine.getPaddle() == null || effectApplied) return;

        Paddle paddle = gameEngine.getPaddle();
        if (originalWidth == null) {
            this.originalWidth = paddle.getWidth();
        }

        int newWidth = (int) (originalWidth * 1.5);
        int maxWidth = (int) (800 * 0.8);
        if (newWidth > maxWidth) newWidth = maxWidth;

        int centerX = paddle.getX() + (paddle.getWidth() / 2);
        paddle.setX(centerX - (newWidth / 2));
        paddle.setWidth(newWidth);

        startEffectTimer();

    }

    @Override
    public void removeEffect(GameEngine gameEngine) {
        if (gameEngine == null || gameEngine.getPaddle() == null || !isEffectActive()) return;

        Paddle paddle = gameEngine.getPaddle();
        if (originalWidth != null) {
            int centerX = paddle.getX() + (paddle.getWidth() / 2);
            paddle.setX(centerX - (originalWidth / 2));
            paddle.setWidth(originalWidth);
        }
    }

    public Image getImage() {
        return image;
    }

    public boolean isEffectActive() {
        return effectActive;
    }
}
