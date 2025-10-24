package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GameEngine {
    private static Paddle paddle = new Paddle(GameConst.DefaultPaddle_X, GameConst.DefaultPaddle_Y,
            GameConst.PaddleWidth, GameConst.PaddleHeight);;
    private static Ball ball = new Ball(GameConst.DefaultBall_X, GameConst.DefaultBall_Y, GameConst.BallRadius,
            GameConst.BallRadius, GameConst.DefaultSpeed, GameConst.DefaultDir_X, GameConst.DefaultDir_Y);
    private static ArrayList<Brick> bricks = new ArrayList<Brick>();
    private static ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    private static Level level =  new Level(1);
    private int score = 0;
    private static int lives;
    private int gameState;


    public GameEngine() {
    }

    public GameEngine(Paddle paddle, Ball ball) {
        GameEngine.paddle = paddle;
        GameEngine.ball = ball;
    }

    public GameEngine(Paddle paddle, Ball ball, ArrayList<Brick> bricks,
                      ArrayList<PowerUp> powerUps, int score, int lives, int gameState, Level level) {
        GameEngine.paddle = paddle;
        GameEngine.ball = ball;
        GameEngine.bricks = bricks;
        GameEngine.powerUps = powerUps;
        GameEngine.level = level;
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

    public static Level getLevel() {
        return level;
    }
    public static void setLevel(Level level) {
        GameEngine.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static int getLives() {
        return lives;
    }

    public static void setLives(int lives_) {
        lives = lives_;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public void startGame() throws IOException, URISyntaxException {
        GameEngine.paddle.render();

        GameEngine.ball.render();
        HeartPowerUp.resetHeartCounter();

        GameEngine.paddle.render();

        GameEngine.getBricks().clear();
        GameEngine.getPowerUps().clear();

        GameEngine.level.setLvl(level.getLvl());
        level.loadLevel(GameEngine.getBricks(), GameEngine.getPowerUps());
        bricks.forEach(b -> b.render());
        powerUps.forEach(p -> p.render());

        setLives(GameConst.DefaultLives);
        setGameState(0);
    }

    public void updateGame() throws IOException, URISyntaxException, InterruptedException {
        if (paddle.isPaddleSliding()) {
            if (gameState == 0) paddle.update();
            paddle.render();
        }
        if (ball.isBallMoving()) {
            if (gameState == 0) ball.update();
            ball.render();
        }

        ArrayList<PowerUp> powerUpsToRemove = new ArrayList<PowerUp>();
        powerUps.forEach(powerUp -> {
            powerUp.update();
            powerUp.render();

            if (powerUp instanceof HeartPowerUp && powerUp.checkPaddleCollision(GameEngine.paddle)) {
                HeartPowerUp heart = (HeartPowerUp) powerUp;
                GameEngine.setLives(GameEngine.getLives() + 1);

                powerUpsToRemove.add(powerUp);
            }

            if (powerUp.isEffectExpired() || gameOver() ||
                    (powerUp instanceof HeartPowerUp && powerUp.isEffectApplied())) {
                powerUpsToRemove.add(powerUp);
            }
        });
        powerUps.removeAll(powerUpsToRemove);

        ArrayList<Brick> bricktoRemove = new ArrayList<Brick>();
        bricks.forEach(b -> {
            b.update();
            if (!b.isDestroyed()) b.render();
            else {
                bricktoRemove.add(b);
                setScore(getScore() + b.getHitPoints());
                level.setNumOfBricksToLvlUp(getLevel().getNumOfBricksToLvlUp() - 1);
            }
        });

        bricktoRemove.forEach(brick -> {
            powerUps.forEach(powerUp -> {
                if (powerUp.getX() - brick.getX() == 25  && brick.getY() == powerUp.getY()) {
                    powerUp.setActive(true);
                }
            });
        });

        bricks.removeAll(bricktoRemove);


        levelUp();
    }

    public void handleInput(Canvas gameCanvas) {
        gameCanvas.setOnMouseClicked(MouseEvent -> {
            ball.setBallMoving(true);
            paddle.setPaddleSliding(true);
            gameState = 0;
        });
        gameCanvas.setOnMouseMoved(MouseEvent -> {
            if (MouseEvent.getX() - paddle.getWidth() / 2 >= 0
                    && MouseEvent.getX() + paddle.getWidth() / 2 < gameCanvas.getWidth() && gameState == 0) {
                if (paddle.isPaddleSliding()) paddle.setX((int) MouseEvent.getX() - GameConst.PaddleWidth / 2);
            }
        });
    }

    public void checkCollision() {
        ball.checkCollision(paddle);
        bricks.forEach(b -> ball.checkCollision(b));
        ball.boundBorder();
        powerUps.forEach(powerUp -> powerUp.checkPaddleCollision(GameEngine.paddle));
    }

    public void levelUp() throws IOException, URISyntaxException {
        if (getLevel().getNumOfBricksToLvlUp() == 0) {
            level.setLvl(getLevel().getLvl() + 1);

            GameEngine.getBricks().clear();
            GameEngine.getPowerUps().clear();

            level.loadLevel(GameEngine.getBricks(), GameEngine.getPowerUps());
            bricks.forEach(b -> b.render());
            powerUps.forEach(p -> p.render());

            setLives(GameConst.DefaultLives);
            setGameState(0);
            setScore(0);
        }
    }

    public boolean gameOver() {
        if (ball.getY() >= 525 && ball.isBallMoving()) {
            setLives(getLives() - 1);

            paddle.setX(GameConst.DefaultPaddle_X);
            paddle.setY(GameConst.DefaultPaddle_Y);
            paddle.setWidth(GameConst.PaddleWidth);

            ball.setX(GameConst.DefaultBall_X);
            ball.setY(GameConst.DefaultBall_Y);
            ball.setSpeed(GameConst.DefaultSpeed);

            gameState = 1;
        }

        if (getLives() == 0) {
            setScore(0);
            paddle.setWidth(GameConst.PaddleWidth);
            ball.setSpeed(GameConst.DefaultSpeed);

            return true;
        }
        return false;
    }
}
