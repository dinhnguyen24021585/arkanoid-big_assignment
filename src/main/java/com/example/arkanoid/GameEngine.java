package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class GameEngine {
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks = new ArrayList<Brick>();
    private ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    private int score;
    private int lives;
    private int gameState;

    public GameEngine() {
    }

    public GameEngine(Paddle paddle, Ball ball) {
        this.paddle = paddle;
        this.ball = ball;
    }

    public GameEngine(Paddle paddle, Ball ball, ArrayList<Brick> bricks, ArrayList<PowerUp> powerUps, int score, int lives, int gameState) {
        this.paddle = paddle;
        this.ball = ball;
        this.bricks = bricks;
        this.powerUps = powerUps;
        this.score = score;
        this.lives = lives;
        this.gameState = gameState;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
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

    public void startGame() throws IOException {
        Paddle paddle = new Paddle(300, 500, 200, 50);
        setPaddle(paddle);
        this.paddle.render();

        Ball ball = new Ball(375, 450, 50, 50, 5, 1, 1);
        setBall(ball);
        this.ball.render();


        ArrayList<Brick> bricks = new ArrayList<Brick>();
        Level level = new Level(1);
        setBricks(bricks);
        level.loadLevel(bricks);
        this.bricks.forEach(b -> b.render());

    }

    public void updateGame() {
        if (this.paddle.isPaddleSliding()) {
            this.paddle.update();
            this.paddle.render();
        }
        if (this.ball.isBallMoving()) {
            this.ball.update();
            this.ball.render();
        }

        ArrayList<Brick> bricktoRemove = new ArrayList<Brick>();
        this.bricks.forEach(b -> b.update());
        this.bricks.forEach(b -> {
            if (!b.isDestroyed()) b.render();
            else {
                bricktoRemove.add(b);
            }
        });
        this.bricks.removeAll(bricktoRemove);
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
        return this.ball.getY() >= 550;
    }
}
