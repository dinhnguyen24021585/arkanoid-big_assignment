package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public class Paddle extends MovableObject {
    private int currentPowerUp;
    private boolean isPaddleSliding = false;
    private Image image;

    private final int SCREEN_WIDTH = 800;

    public Paddle(int x, int y, int width, int height) {
        super(0, 0);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        this.currentPowerUp = 0;
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/paddle.png"));
    }

    public int getCurrentPowerUp() {
        return currentPowerUp;
    }

    public void setCurrentPowerUp(int currentPowerUp) {
        this.currentPowerUp = currentPowerUp;
    }

    public boolean isPaddleSliding() {
        return isPaddleSliding;
    }

    public void setPaddleSliding(boolean paddleSliding) {
        isPaddleSliding = paddleSliding;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean checkCollision(GameObject other) {
        if (getX() < other.getX() + other.getWidth() && getX() + getWidth() > other.getX()
                && getY() < other.getY() + other.getHeight() && getY() + getHeight() > other.getY()) {
            return true;
        }
        return false;
    }

    public void applyPowerUp() {

    }



    @Override
    public void move() {
        setX(getX());
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render() {
        Renderer.getInstance().render(this);
    }
}
