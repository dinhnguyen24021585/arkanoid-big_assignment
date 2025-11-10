package com.example.arkanoid;

import javax.swing.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.geometry.Point2D;

import java.util.LinkedList;

public class Ball extends MovableObject {
    private int speed;
    private int directionX;
    private int directionY;
    private boolean ballMoving = false;
    private Image image;
    private final int MAX_TRAIL_LENGTH = 10;
    private LinkedList<Point2D> trailPoints;

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

        this.trailPoints = new LinkedList<>();

        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/ball.png"));
    }

    public int getMAX_TRAIL_LENGTH() {
        return MAX_TRAIL_LENGTH;
    }

    public LinkedList<Point2D> getTrailPoints() {
        return trailPoints;
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
        if (this.ballMoving && !ballMoving) {
            this.trailPoints.clear();
        }
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
        if (getY() == 0 || getY() + GameConst.BallRadius + getDy() >= GameConst.HEIGHT) {
            reverseY();
        }
        if (getX() == 0 || getX() + GameConst.BallRadius + getDx() >= GameConst.WIDTH) {
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

    private void checkPortalTeleport() {
        if (!GameEngine.isPortalsActive()) return;

        int ballCenterX = getX() + getWidth()/2;
        int ballCenterY = getY() + getHeight()/2;

        int portalX = GameEngine.getPortal1X() + 40;
        int portalY = GameEngine.getPortal1Y() + 40;

        if (Math.abs(ballCenterX - portalX) < 50 &&
                Math.abs(ballCenterY - portalY) < 50) {

            teleportToRandomConfusingPosition();

            System.out.println("CONFUSION TELEPORT!");
        }
    }

    private void teleportToRandomConfusingPosition() {
        int newX, newY;
        int attempt = 0;

        do {
            int zone = (int)(Math.random() * 6);
            switch (zone) {
                case 0 -> {
                    newX = 10;
                    newY = 10;
                }
                case 1 -> {
                    newX = GameConst.WIDTH - getWidth() - 10;
                    newY = 10;
                }
                case 2 -> {
                    newX = 10;
                    newY = GameConst.HEIGHT - 200;
                }
                case 3 -> {
                    newX = GameConst.WIDTH - getWidth() - 10;
                    newY = GameConst.HEIGHT - 200;
                }
                case 4 -> {
                    newX = GameConst.WIDTH / 2 - getWidth()/2;
                    newY = 5;
                }
                case 5 -> {
                    newX = Math.max( (int)(Math.random() * (GameConst.WIDTH - getWidth())), 0);
                    newY = GameConst.HEIGHT - 150;
                }
                default -> {
                    newX = getX();
                    newY = getY();
                }
            }
            attempt++;
        } while (!isSafePosition(newX + getWidth()/2, newY + getHeight()/2) && attempt < 10);

        setX(newX);
        setY(newY);

        if (Math.random() < 0.5) reverseX();
        if (Math.random() < 0.5) reverseY();

        System.out.println("Ball teleported to: " + newX + ", " + newY);
    }

    private boolean isSafePosition(int x, int y) {
        for (Brick brick : GameEngine.getBricks()) {
            if (!brick.isDestroyed() && brick instanceof UnbreakableBrick) {
                if (x >= brick.getX() && x <= brick.getX() + brick.getWidth() &&
                        y >= brick.getY() && y <= brick.getY() + brick.getHeight()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void findSafeMirrorPosition(int targetX, int targetY) {
        int safeX = targetX;
        int safeY = targetY;

        int[] offsets = {0, 50, -50, 100, -100, 150, -150};

        for (int offsetX : offsets) {
            for (int offsetY : offsets) {
                int testX = targetX + offsetX;
                int testY = targetY + offsetY;

                if (testX >= 0 && testX <= GameConst.WIDTH &&
                        testY >= 0 && testY <= GameConst.HEIGHT) {

                    if (isSafePosition(testX, testY)) {
                        setX(testX - getWidth()/2);
                        setY(testY - getHeight()/2);
                        return;
                    }
                }
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
        if (ballMoving) {
            move();

            checkPortalTeleport();

            trailPoints.addFirst(new Point2D(getX() + getWidth() / 2, getY() + getHeight() / 2));

            while (trailPoints.size() > MAX_TRAIL_LENGTH) {
                trailPoints.removeLast();
            }
        }
    }

    @Override
    public void render() {
        Renderer.getInstance().render(this);
    }
}