package com.example.arkanoid.PowerUps;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameElements.Ball;
import com.example.arkanoid.GameEngine;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class BalancedMultiballPowerUp extends PowerUp {
    private Image image;
    private static final int MAX_TOTAL_BALLS = 5; // Tối đa 5 ball

    public BalancedMultiballPowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 4);
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerUpImages/powerup_multiball.png"));
    }

    @Override
    public void applyEffect() {
        ArrayList<Ball> currentBalls = getAllBalls();
        int ballsNeeded = MAX_TOTAL_BALLS - currentBalls.size();

        if (ballsNeeded <= 0) {
            return;
        }

        int ballsToCreate = Math.min(2, ballsNeeded);
        createNewBalls(ballsToCreate);
    }


    private ArrayList<Ball> getAllBalls() {
        ArrayList<Ball> allBalls = new ArrayList<>();
        allBalls.add(GameEngine.getInstance().getBall());
        allBalls.addAll(GameEngine.getInstance().getExtraBalls());
        return allBalls;
    }

    private void createNewBalls(int count) {
        int centerX = 400;
        int centerY = 300;

        for (int i = 0; i < count; i++) {
            Ball newBall = new Ball(
                    centerX + (i * 30 - 15),
                    centerY,
                    GameConst.BallRadius,
                    GameConst.BallRadius,
                    GameConst.DefaultSpeed,
                    (i % 2 == 0) ? 1 : -1,
                    -1
            );
            newBall.setBallMoving(true);
            GameEngine.getInstance().getExtraBalls().add(newBall);
        }
    }

    @Override
    public void removeEffect() {
    }

    @Override
    public boolean isEffectExpired() {
        return false; // khong co timeout
    }

    public Image getImage() {
        return image;
    }
}