package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GameEngine {
    private static Paddle paddle = new Paddle(GameConst.DefaultPaddle_X, GameConst.DefaultPaddle_Y,
            GameConst.PaddleWidth, GameConst.PaddleHeight);
    private static Ball ball = new Ball(GameConst.DefaultBall_X, GameConst.DefaultBall_Y, GameConst.BallRadius,
            GameConst.BallRadius, GameConst.DefaultSpeed, GameConst.DefaultDir_X, GameConst.DefaultDir_Y);
    private static ArrayList<Brick> bricks = new ArrayList<Brick>();
    private static ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    private static Level level = new Level(1);
    private static int score = 0;
    private static int lives;
    private static int gameState;
    private static AnimationTimer gameLoop;
    private static ShootingPowerUp shootingPowerUp = null;

    private static ArrayList<Ball> extraBalls = new ArrayList<>();

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

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameEngine.score = score;
    }

    public static int getLives() {
        return lives;
    }

    public static void setLives(int lives_) {
        lives = lives_;
    }

    public static int getGameState() {
        return gameState;
    }

    public static void setGameState(int gameState) {
        GameEngine.gameState = gameState;
    }

    public static ArrayList<Ball> getExtraBalls() {
        return extraBalls;
    }

    public static ShootingPowerUp getShootingPowerUp() {
        return shootingPowerUp;
    }

    public static void setShootingPowerUp(ShootingPowerUp powerUp) {
        GameEngine.shootingPowerUp = powerUp;
    }

    public static void addExtraBalls(ArrayList<Ball> balls) {
        extraBalls.addAll(balls);
    }

    public static void removeExtraBall(Ball ball) {
        extraBalls.remove(ball);
    }

    public static void startGame() throws IOException, URISyntaxException {
        Renderer.getInstance().renderBackground();
        GameEngine.paddle.render();
        GameEngine.ball.render();
        HeartPowerUp.resetHeartCounter();

        paddle.setX(GameConst.DefaultPaddle_X);
        paddle.setY(GameConst.DefaultPaddle_Y);
        paddle.setWidth(GameConst.PaddleWidth);

        ball.setX(GameConst.DefaultBall_X);
        ball.setY(GameConst.DefaultBall_Y);
        ball.setSpeed(GameConst.DefaultSpeed);

        paddle.setPaddleSliding(false);
        ball.setBallMoving(false);

        GameEngine.paddle.render();
        GameEngine.ball.render();


        HeartPowerUp.resetHeartCounter();

        GameEngine.getBricks().clear();
        GameEngine.getPowerUps().clear();
        extraBalls.clear();

        GameEngine.level.setLvl(level.getLvl());
        GameEngine.getLevel().setNumOfBricksToLvlUp(0);
        level.loadLevel(GameEngine.getBricks(), GameEngine.getPowerUps());

        bricks.forEach(b -> b.render());
        powerUps.forEach(p -> p.render());

        shootingPowerUp = null;
        setLives(GameConst.DefaultLives);
        setScore(0);
       // setGameState(1);
    }

    public static void updateGame() throws IOException, URISyntaxException, InterruptedException  {
        Renderer.getInstance().renderBackground();

        if (paddle.isPaddleSliding()) {
            paddle.update();
        }
        paddle.render();

        if (ball.isBallMoving()) {
            ball.update();
        }
        ball.render();

        if (shootingPowerUp != null) {
            shootingPowerUp.updateBullets();

            if (!shootingPowerUp.isEffectActive()) {
                shootingPowerUp = null;
            }
        }

        for (Ball extraBall : extraBalls) {
            if (extraBall.isBallMoving()) {
                extraBall.update();
            }
            extraBall.render();
        }

        ArrayList<PowerUp> powerUpsToRemove = new ArrayList<PowerUp>();
        powerUps.forEach(powerUp -> {
            powerUp.update();
            powerUp.render();

            if (powerUp.isEffectExpired() || (powerUp instanceof HeartPowerUp
                    && powerUp.checkPaddleCollision(paddle))) {
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
                if (powerUp.getX() - brick.getX() == 25 && brick.getY() == powerUp.getY()) {
                    powerUp.setActive(true);
                }
            });
        });

        bricks.removeAll(bricktoRemove);

        levelUp();
    }

    public static void handleInput(Canvas gameCanvas) {
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

    public static void checkCollision() {
        ball.checkCollision(paddle);
        bricks.forEach(b -> ball.checkCollision(b));
        ball.boundBorder();

        for (Ball extraBall : extraBalls) {
            extraBall.checkCollision(paddle);
            bricks.forEach(b -> extraBall.checkCollision(b));
            extraBall.boundBorder();
        }

        powerUps.forEach(powerUp -> powerUp.checkPaddleCollision(GameEngine.paddle));
        if (shootingPowerUp != null && shootingPowerUp.isEffectActive()) {
            shootingPowerUp.renderBullets();
        }
    }

    public static void levelUp() throws IOException, URISyntaxException {
        if (getLevel().getNumOfBricksToLvlUp() == 0) {
            level.setLvl(getLevel().getLvl() + 1);

            extraBalls.clear();
            GameEngine.getBricks().clear();
            GameEngine.getPowerUps().clear();

            level.loadLevel(GameEngine.getBricks(), GameEngine.getPowerUps());

            setGameState(1);

            paddle.setX(GameConst.DefaultPaddle_X);
            paddle.setY(GameConst.DefaultPaddle_Y);
            paddle.setWidth(GameConst.PaddleWidth);
            ball.setX(GameConst.DefaultBall_X);
            ball.setY(GameConst.DefaultBall_Y);
            ball.setSpeed(GameConst.DefaultSpeed);
            paddle.setPaddleSliding(false);
            ball.setBallMoving(false);

            Renderer.getInstance().renderBackground();
            bricks.forEach(b -> b.render());
            powerUps.forEach(p -> p.render());
            paddle.render();
            ball.render();
            shootingPowerUp = null;

            System.out.println("LEVEL UP! Now level: " + level.getLvl());
        }
    }

    public static boolean gameOver() {
        // Main ball lost
        if (ball.getY() >= 525 && ball.isBallMoving()) {
            ball.setBallMoving(false);
            ball.setY(600);
        }

        ArrayList<Ball> lostExtraBalls = new ArrayList<>();
        for (Ball extraBall : extraBalls) {
            if (extraBall.getY() >= 525 && extraBall.isBallMoving()) {
                lostExtraBalls.add(extraBall);
                System.out.println("Extra ball lost");
            }
        }
        extraBalls.removeAll(lostExtraBalls);

        boolean hasGameStarted = ball.isBallMoving() || !extraBalls.isEmpty() || getGameState() == 0;
        boolean allBallsLost = ball.getY() >= 525 && extraBalls.isEmpty();

        boolean shouldGameOver = hasGameStarted && allBallsLost;

        if (shouldGameOver && getLives() > 0) {
            setLives(getLives() - 1);
            System.out.println("All balls lost! Lives: " + getLives());

            paddle.setX(GameConst.DefaultPaddle_X);
            paddle.setY(GameConst.DefaultPaddle_Y);
            paddle.setWidth(GameConst.PaddleWidth);

            ball.setX(GameConst.DefaultBall_X);
            ball.setY(GameConst.DefaultBall_Y);
            ball.setSpeed(GameConst.DefaultSpeed);

            paddle.setPaddleSliding(false);
            ball.setBallMoving(false);

            extraBalls.clear();
            shootingPowerUp = null;

            Renderer.getInstance().renderBackground();
            bricks.forEach(b -> {
                if (!b.isDestroyed()) b.render();
            });
            powerUps.forEach(p -> p.render());
            paddle.render();
            ball.render();

            gameState = 1;
        }

        if (getLives() == 0) {
            setScore(0);
            paddle.setWidth(GameConst.PaddleWidth);
            ball.setSpeed(GameConst.DefaultSpeed);
            extraBalls.clear();
            shootingPowerUp = null;
            level.setNumOfBricksToLvlUp(0);
            return true;
        }

        return false;
    }

    public static void saveStateToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("save_state.txt"))) {
            writer.println("level=" + level.getLvl());
            writer.println("score=" + score);
            writer.println("lives=" + lives);

            writer.println("paddle=" + paddle.getX() + "," + paddle.getY() + "," + paddle.isPaddleSliding());
            writer.println("ball=" + ball.getX() + "," + ball.getY() + "," + ball.isBallMoving());

            for (Brick b : bricks) {
                writer.println("brick=" + b.getClass().getSimpleName() + "," +
                        b.getX() + "," + b.getY() + "," + b.isDestroyed() + "," +
                        b.getHitPoints() + "," + b.getType());
            }

            for (PowerUp p : powerUps) {
                writer.println("powerup=" + p.getClass().getSimpleName() + ","
                        + p.getX() + "," + p.getY() + "," + p.isActive());
            }

            for (Ball ball : extraBalls) {
                writer.println("extraball=" + ball.getX() + "," + ball.getY() + "," +
                        ball.isBallMoving() + "," + ball.getDirectionX() + "," +
                        ball.getDirectionY());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadStateFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("save_state.txt"))) {
            bricks.clear();
            powerUps.clear();
            extraBalls.clear();
            GameEngine.getLevel().setNumOfBricksToLvlUp(0);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length < 2) continue;
                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "level" -> level.setLvl(Integer.parseInt(value));
                    case "score" -> score = Integer.parseInt(value);
                    case "lives" -> lives = Integer.parseInt(value);

                    case "paddle" -> {
                        String[] vals = value.split(",");
                        paddle.setX((int) Double.parseDouble(vals[0]));
                        paddle.setY((int) Double.parseDouble(vals[1]));
                        paddle.setPaddleSliding(Boolean.parseBoolean(vals[2]));
                    }

                    case "ball" -> {
                        String[] vals = value.split(",");
                        ball.setX((int) Double.parseDouble(vals[0]));
                        ball.setY((int) Double.parseDouble(vals[1]));
                        ball.setBallMoving(Boolean.parseBoolean(vals[2]));
                    }

                    case "brick" -> {
                        String[] vals = value.split(",");
                        String type = vals[0];
                        int x = Integer.parseInt(vals[1]);
                        int y = Integer.parseInt(vals[2]);
                        boolean destroyed = Boolean.parseBoolean(vals[3]);
                        int hits = Integer.parseInt(vals[4]);
                        int brickType = Integer.parseInt(vals[5]);

                        Brick b = switch (type) {
                            case "NormalBrick" -> new NormalBrick(x, y);
                            case "StrongBrick" -> new StrongBrick(x, y, hits, brickType);
                            case "UnbreakableBrick" -> new UnbreakableBrick(x, y);
                            case "ExplosiveBrick" -> new ExplosiveBrick(x, y);
                            default -> null;
                        };

                        if (!(b instanceof UnbreakableBrick)) {
                            GameEngine.getLevel().setNumOfBricksToLvlUp(getLevel().getNumOfBricksToLvlUp() + 1);
                        }

                        if (b != null) {
                            b.setDestroyed(destroyed);
                            bricks.add(b);
                        }

                    }

                    case "powerup" -> {
                        String[] vals = value.split(",");
                        String type = vals[0];
                        int x = Integer.parseInt(vals[1]);
                        int y = Integer.parseInt(vals[2]);
                        boolean active = Boolean.parseBoolean(vals[3]);
                        PowerUp p = null;
                        switch (type) {
                            case "HeartPowerUp" -> p = new HeartPowerUp(x, y);
                            case "ExpandPaddlePowerUp" -> p = new ExpandPaddlePowerUp(x, y);
                            case "FastBallPowerUp" -> p = new FastBallPowerUp(x, y);
                            case "BalancedMultiballPowerUp" -> p = new BalancedMultiballPowerUp(x, y);
                        }
                        if (p != null) {
                            p.setActive(active);
                            powerUps.add(p);
                        }
                    }

                    case "extraball" -> {
                        String[] vals = value.split(",");
                        Ball extraBall = new Ball(
                                Integer.parseInt(vals[0]),
                                Integer.parseInt(vals[1]),
                                GameConst.BallRadius,
                                GameConst.BallRadius,
                                GameConst.DefaultSpeed,
                                Integer.parseInt(vals[3]),
                                Integer.parseInt(vals[4])
                        );
                        extraBall.setBallMoving(Boolean.parseBoolean(vals[2]));
                        extraBalls.add(extraBall);
                    }
                }
            }

            Renderer.getInstance().renderBackground();
            bricks.forEach(b -> {
                if (!b.isDestroyed()) b.render();
            });
            powerUps.forEach(p -> p.render());
            paddle.render();
            ball.render();
            extraBalls.forEach(Ball::render);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
