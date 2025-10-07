package com.example.arkanoid;

import java.util.ArrayList;

public class GameEngine {
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks = new ArrayList<Brick>();
    private ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    private int score;
    private int lives;
    private int gameState;
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

    public void startGame() {

    }

    public void updateGame() {

    }

    public void handleInput() {

    }

    public void checkCollision() {

    }

    public void gameOver() {

    }
}
