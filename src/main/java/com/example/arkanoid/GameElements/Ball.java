package com.example.arkanoid.GameElements;

import com.example.arkanoid.*;
import com.example.arkanoid.GameElements.Bricks.Brick;
import com.example.arkanoid.GameElements.Bricks.UnbreakableBrick;
import com.example.arkanoid.GeneralObject.MovableObject;
import com.example.arkanoid.GeneralObject.GameObject;
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
    private long lastTeleportTime = 0;

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

        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/gameElementImages/ball.png"));
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
        if (!GameEngine.getInstance().isPortalsActive()) return;

        int ballCenterX = getX() + getWidth()/2;
        int ballCenterY = getY() + getHeight()/2;

        int portalCenterX = GameEngine.getInstance().getPortal1X() + 40;
        int portalCenterY = GameEngine.getInstance().getPortal1Y() + 40;

        double distance = Math.sqrt(
                Math.pow(ballCenterX - portalCenterX, 2) +
                        Math.pow(ballCenterY - portalCenterY, 2)
        );

        if (distance < 35 && System.currentTimeMillis() - lastTeleportTime > 1000) {
            teleportToRandomConfusingPosition();
            lastTeleportTime = System.currentTimeMillis();
            System.out.println("PORTAL TELEPORT!");
        }
    }

    private void teleportToRandomConfusingPosition() {
        int[][] safePositions = {
                {10, 10},
                {GameConst.WIDTH - getWidth() - 10, 10},
                {10, GameConst.HEIGHT - 150},
                {GameConst.WIDTH - getWidth() - 10, GameConst.HEIGHT - 150}
        };

        int randomIndex = (int)(Math.random() * safePositions.length);
        int newX = safePositions[randomIndex][0];
        int newY = safePositions[randomIndex][1];

        setX(newX);
        setY(newY);

        if (Math.random() < 0.5) reverseX();
        if (Math.random() < 0.5) reverseY();

        System.out.println("Ball teleported to: " + newX + ", " + newY);
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
