package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class BallTest extends Application {

    @Override
    public void start(Stage stage) {
        // 1️⃣ Canvas
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 2️⃣ Tạo bóng và paddle
        Ball ball = new Ball(200, 200, 30, 30, 5, 1, 1);
        Paddle paddle = new Paddle(350, 550, 100, 20, 7);

        // 3️⃣ Renderer
        Renderer renderer = Renderer.getInstance();
        renderer.setGraphicsContext(gc);

        // 4️⃣ Lưới gạch
        int rows = 5;
        int cols = 10;
        Brick[][] bricks = new Brick[rows][cols];
        int startX = 60;
        int startY = 50;
        int brickWidth = 60;
        int brickHeight = 25;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int type = (row % 3) + 1;
                Brick brick = new Brick(
                        startX + col * (brickWidth + 5),
                        startY + row * (brickHeight + 5),
                        brickWidth,
                        brickHeight,
                        1,
                        type
                );
                bricks[row][col] = brick;
            }
        }

        // 5️⃣ Cài đặt điều khiển chuột
        Scene scene = new Scene(new Group(canvas));

        scene.setOnMouseMoved(e -> {
            int mouseX = (int) e.getX();
            paddle.setX(mouseX - paddle.getWidth() / 2);

            // Giới hạn trong màn hình
            if (paddle.getX() < 0) paddle.setX(0);
            if (paddle.getX() + paddle.getWidth() > 800) {
                paddle.setX(800 - paddle.getWidth());
            }
        });

        // 6️⃣ Game loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, 800, 600);

                // Di chuyển bóng
                ball.move();

                // Va chạm tường
                if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= 800) {
                    ball.reverseX();
                }
                if (ball.getY() <= 0) {
                    ball.reverseY();
                }

                // Va chạm paddle
                if (ball.getY() + ball.getHeight() >= paddle.getY() &&
                        ball.getX() + ball.getWidth() >= paddle.getX() &&
                        ball.getX() <= paddle.getX() + paddle.getWidth()) {
                    ball.reverseY();
                }

                // Va chạm gạch
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        Brick brick = bricks[r][c];
                        if (!brick.isDestroyed() &&
                                ball.getX() < brick.getX() + brick.getWidth() &&
                                ball.getX() + ball.getWidth() > brick.getX() &&
                                ball.getY() < brick.getY() + brick.getHeight() &&
                                ball.getY() + ball.getHeight() > brick.getY()) {
                            brick.takeHits();
                            ball.reverseY();
                        }
                    }
                }

                // Vẽ tất cả
                ball.render();
                paddle.render();
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        bricks[r][c].render();
                    }
                }
            }
        }.start();

        // 7️⃣ Hiển thị cửa sổ
        stage.setScene(scene);
        stage.setTitle("Ball + Paddle + Brick (Mouse Control)");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
