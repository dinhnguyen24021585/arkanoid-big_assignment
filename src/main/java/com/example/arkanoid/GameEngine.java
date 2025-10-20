package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GameEngine {
    private static Paddle paddle;
    private static Ball ball;
    private static ArrayList<Brick> bricks = new ArrayList<Brick>();
    private static ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    private int score;
    private int lives;
    private int gameState;

    public GameEngine() {
    }

    public GameEngine(Paddle paddle, Ball ball) {
        GameEngine.paddle = paddle;
        GameEngine.ball = ball;
    }

    public GameEngine(Paddle paddle, Ball ball, ArrayList<Brick> bricks, ArrayList<PowerUp> powerUps, int score, int lives, int gameState) {
        GameEngine.paddle = paddle;
        GameEngine.ball = ball;
        GameEngine.bricks = bricks;
        GameEngine.powerUps = powerUps;
        this.score = score;
        this.lives = lives;
        this.gameState = gameState;
    }

    public static Paddle getPaddle() {
        return paddle;
    }

    public static void setPaddle(Paddle paddle) {
        GameEngine.paddle = paddle;
    }

    public static Ball getBall() {
        return ball;
    }

    public static void setBall(Ball ball) {
        GameEngine.ball = ball;
    }

    public static ArrayList<Brick> getBricks() {
        return GameEngine.bricks;
    }

    public static void setBricks(ArrayList<Brick> bricks) {
        GameEngine.bricks = bricks;
    }

    public static ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public static void setPowerUps(ArrayList<PowerUp> powerUps) {
        GameEngine.powerUps = powerUps;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public void startGame() throws IOException, URISyntaxException {
        Paddle paddle = new Paddle(300, 500, 200, 50);
        setPaddle(paddle);
        GameEngine.paddle.render();

        Ball ball = new Ball(375, 450, 50, 50, 5, 1, 1);
        setBall(ball);
        GameEngine.ball.render();

        Level level = new Level(1);
        level.loadLevel(GameEngine.getBricks());
        bricks.forEach(b -> b.render());

    }

    public void updateGame() {
        if (paddle.isPaddleSliding()) {
            paddle.update();
            paddle.render();
        }
        if (ball.isBallMoving()) {
            ball.update();
            ball.render();
        }

        ArrayList<Brick> bricktoRemove = new ArrayList<Brick>();
        bricks.forEach(b -> b.update());
        bricks.forEach(b -> {
            if (!b.isDestroyed()) b.render();
            else {
                bricktoRemove.add(b);
            }
        });
        bricks.removeAll(bricktoRemove);
    }

    public void handleInput(Canvas gameCanvas) {
        gameCanvas.setOnMouseClicked(MouseEvent -> {
            ball.setBallMoving(true);
            paddle.setPaddleSliding(true);
        });
        gameCanvas.setOnMouseMoved(MouseEvent -> {
            if (MouseEvent.getX() - 100 >= 0 && MouseEvent.getX() + 100 < gameCanvas.getWidth()) {
                if (paddle.isPaddleSliding()) paddle.setX((int) MouseEvent.getX() - 100);
            }
        });
    }

    public void checkCollision() {
        ball.checkCollision(paddle);
        bricks.forEach(b -> ball.checkCollision(b));
        ball.boundBorder();
    }

    public boolean gameOver() {
        return ball.getY() >= 550;
    }
}
