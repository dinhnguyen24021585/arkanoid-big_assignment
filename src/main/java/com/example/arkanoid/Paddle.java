package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public class Paddle extends MovableObject {
    private int speed;
    private int currentPowerUp;
    private Image image;

    private final int SCREEN_WIDTH = 800;

    public Paddle(int x, int y, int width, int height, int speed) {
        super(0, 0);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        this.speed = speed;
        this.currentPowerUp = 0;
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/paddle.png"));
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCurrentPowerUp() {
        return currentPowerUp;
    }

    public void setCurrentPowerUp(int currentPowerUp) {
        this.currentPowerUp = currentPowerUp;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void moveLeft() {
        setX(getX() - speed);
        if (getX() < 0) setX(0);
    }

    public void moveRight() {
        setX(getX() + speed);
        if (getX() + getWidth() > SCREEN_WIDTH) {
            setX(SCREEN_WIDTH - getWidth());
        }
    }

    public void applyPowerUp() {

    }

    @Override
    public void move() {
        setX(getX() + getDx());
        setY(getY() + getDy());
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
