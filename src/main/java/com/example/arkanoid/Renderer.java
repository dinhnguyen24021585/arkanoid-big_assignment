package com.example.arkanoid;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Renderer {
    private static Renderer instance;
    private GraphicsContext gc;
    private Image Bg = null;
    private int currentLevel = -1;

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
                }
            }
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