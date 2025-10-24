package com.example.arkanoid;

import javax.swing.*;
//import java.awt.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public class Ball extends MovableObject {
    private int speed;
    private int directionX;
    private int directionY;
    private boolean ballMoving = false;
    private Image image;

    public Ball(int x, int y, int width, int height, int speed, int directionX, int directionY) {
        super();
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;

        setDx(directionX * speed);
        setDy(directionY * speed);

        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/ball.png"));
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirectionX() {
        return this.directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return this.directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public boolean isBallMoving() {
        return ballMoving;
    }

    public void setBallMoving(boolean ballMoving) {
        this.ballMoving = ballMoving;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void reverseX() {
        directionX *= -1;
        setDx(directionX * speed);
    }

    public void reverseY() {
        directionY *= -1;
        setDy(directionY * speed);
    }

    public void bounceOff(GameObject other) {
        if (getY() + getHeight() - getDy() <= other.getY() || getY() - getDy() >= other.getY() + other.getHeight()){
            reverseY();
        } else if (getX() + getWidth() - getDx() <= other.getX() || getX() - getDx() >= other.getX() + other.getWidth()) {
            reverseX();
        }
    }

    public void boundBorder() {
        if (getY() == 0 || getY() + GameConst.BallRadius == GameConst.HEIGHT) {
            reverseY();
        }
        if (getX() == 0 || getX() + GameConst.BallRadius == GameConst.WIDTH) {
            reverseX();
        }
    }

    public void checkCollision(GameObject other) {
        if (getX() < other.getX() + other.getWidth() && getX() + getWidth() > other.getX()
                && getY() < other.getY() + other.getHeight() && getY() + getHeight() > other.getY()) {
            bounceOff(other);

            if (other instanceof Brick brick) {
                if (!(other instanceof UnbreakableBrick)) brick.takeHits();
            }

        }
    }

    @Override
    public void move() {
        if (getX() + getDx() >= 0 && getX() + getDx() <= GameConst.WIDTH) {
            setX(getX() + getDx());
        } else {
            setX(0);
        }
        if (getY() + getDy() >= 0 && getY() + getDy() <= GameConst.HEIGHT) {
            setY(getY() + getDy());
        } else {
            setY(0);
        }
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
