package com.example.arkanoid;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Renderer {
    private static Renderer instance;
    private GraphicsContext gc;
    private Image Bg = null;
    private int currentLevel = -1;
    private final static Color COLOR_FAR = Color.rgb(150, 200, 255);
    private final static Color COLOR_NEAR = Color.rgb(240, 255, 255);

    private Renderer() {
    }

    public static Renderer getInstance() {
        if (instance == null) instance = new Renderer();
        return instance;
    }

    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }

    public void render(GameObject obj) {
        if (gc == null) return;

        if (obj instanceof Paddle paddle) {
            gc.drawImage(
                    paddle.getImage(),
                    paddle.getX(),
                    paddle.getY(),
                    paddle.getWidth(),
                    paddle.getHeight()
            );
        } else if (obj instanceof Ball ball) {
            double initialRadius = ball.getWidth() / 2.0;
            int index = 0;
            Color originalFill = (Color) gc.getFill();

            for (Point2D point : ball.getTrailPoints()) {
                double ratio = (double) index / ball.getMAX_TRAIL_LENGTH();
                double opacity = 1.0 - ratio * 0.5;
                double radius = initialRadius * (1.0 - ratio * 0.6);
                Color baseColor = COLOR_NEAR.interpolate(COLOR_FAR, ratio);
                Color trailColor = baseColor.deriveColor(0, 1.0, 1.0, opacity);

                gc.setFill(trailColor);

                double x = point.getX() - radius;
                double y = point.getY() - radius;
                gc.fillOval(x, y, radius * 2, radius * 2);

                index++;
            }
            gc.setFill(originalFill);

            gc.drawImage(
                    ball.getImage(),
                    ball.getX(),
                    ball.getY(),
                    ball.getWidth(),
                    ball.getHeight()
            );
        } else if (obj instanceof Brick brick) {
            if (!brick.isDestroyed()) {
                gc.drawImage(
                        brick.getImage(),
                        brick.getX(),
                        brick.getY(),
                        brick.getWidth(),
                        brick.getHeight()
                );
            }
        } else if (obj instanceof PowerUp powerUp) {
            if (powerUp.isActive()) {
                if (powerUp instanceof ExpandPaddlePowerUp expandPowerUp) {
                    gc.drawImage(
                            expandPowerUp.getImage(),
                            powerUp.getX(),
                            powerUp.getY(),
                            powerUp.getWidth(),
                            powerUp.getHeight()
                    );
                } else if (powerUp instanceof FastBallPowerUp fastBallPowerUp) {
                    gc.drawImage(
                            fastBallPowerUp.getImage(),
                            powerUp.getX(),
                            powerUp.getY(),
                            powerUp.getWidth(),
                            powerUp.getHeight()
                    );
                } else if (powerUp instanceof HeartPowerUp heartPowerUp) {
                    gc.drawImage(
                            heartPowerUp.getImage(),
                            powerUp.getX(),
                            powerUp.getY(),
                            powerUp.getWidth(),
                            powerUp.getHeight()
                    );
                } else if (powerUp instanceof BalancedMultiballPowerUp balancedMultiball) {
                    gc.drawImage(
                            balancedMultiball.getImage(),
                            powerUp.getX(),
                            powerUp.getY(),
                            powerUp.getWidth(),
                            powerUp.getHeight()
                    );
                } else if (powerUp instanceof ShootingPowerUp shootingPowerUp) {
                    gc.drawImage(
                            shootingPowerUp.getImage(),
                            powerUp.getX(),
                            powerUp.getY(),
                            powerUp.getWidth(),
                            powerUp.getHeight()
                    );
                }
            }
        }
    }

    public void render(Bullet bullet) {
        if (gc == null || bullet == null) return;

        if (bullet.getImage() != null) {
            gc.drawImage(
                    bullet.getImage(),
                    bullet.getX(),
                    bullet.getY(),
                    bullet.getWidth(),
                    bullet.getHeight()
            );
        } else {
            gc.setFill(Color.YELLOW);
            gc.fillRect(
                    bullet.getX(),
                    bullet.getY(),
                    bullet.getWidth(),
                    bullet.getHeight()
            );
        }
    }

    public void renderBackground() {
        if (gc == null || GameEngine.getLevel() == null) return;

        if (Bg == null || !Objects.equals(currentLevel, GameEngine.getLevel().getLvl())) {
            Bg = new Image(getClass().getResourceAsStream(
                    "/com/example/arkanoid/Image/background" + GameEngine.getLevel().getLvl() + ".png"));
            currentLevel = GameEngine.getLevel().getLvl();
        }

        gc.drawImage(Bg, 0, 0, GameConst.WIDTH, GameConst.HEIGHT);
    }

    public void renderAll(GameObject[] objects) {
        if (gc == null) return;
        for (GameObject obj : objects) {
            render(obj);
        }
    }
}