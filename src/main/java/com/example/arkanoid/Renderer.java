package com.example.arkanoid;

import java.awt.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Renderer {
    private static Renderer instance;
    private GraphicsContext gc;
    private Renderer() {}

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
        }
    }

    public void renderAll(GameObject[] objects) {
        if (gc == null) return;
        for (GameObject obj : objects) {
            render(obj);
        }
    }
}