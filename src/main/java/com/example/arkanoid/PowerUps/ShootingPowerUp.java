package com.example.arkanoid.PowerUps;

import com.example.arkanoid.GameElements.Bricks.UnbreakableBrick;
import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameElements.Bricks.Brick;
import com.example.arkanoid.GameElements.Bullet;
import com.example.arkanoid.GameElements.Paddle;
import com.example.arkanoid.GameElements.Sound;
import com.example.arkanoid.GameEngine;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class ShootingPowerUp extends PowerUp {
    private Image image;
    private ArrayList<Bullet> bullets;
    private static final int BASE_DURATION = 5000;
    private static final int SHOOT_INTERVAL = 200;
    private long lastShootTime = 0;
    private int totalDuration = BASE_DURATION;

    public ShootingPowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 5);
        this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerUpImages/powerup_shooting.png"));
        this.bullets = new ArrayList<>();
        this.duration = BASE_DURATION;
        this.totalDuration = BASE_DURATION;
    }

    @Override
    public void applyEffect() {
        ShootingPowerUp currentShooting = GameEngine.getShootingPowerUp();

        if (currentShooting != null && currentShooting.isEffectActive()) {
            currentShooting.extendDuration(BASE_DURATION);
            return;
        }

        if (effectActive) return;

        effectActive = true;
        startEffectTimer();
        bullets.clear();
        lastShootTime = System.currentTimeMillis();
        totalDuration = BASE_DURATION;

        GameEngine.setShootingPowerUp(this);
    }

    public void extendDuration(int additionalTime) {
        this.totalDuration += additionalTime;
        this.duration = totalDuration;

        if (effectStartTime > 0) {
            effectStartTime = System.currentTimeMillis();
        }
    }

    @Override
    public void removeEffect() {
        if (!effectActive) return;

        bullets.clear();
        effectActive = false;
        effectStartTime = 0;
        lastShootTime = 0;
        totalDuration = BASE_DURATION;

        if (GameEngine.getShootingPowerUp() == this) {
            GameEngine.setShootingPowerUp(null);
        }
    }

    @Override
    public boolean isEffectExpired() {
        if (effectStartTime == 0) return false;
        return System.currentTimeMillis() - effectStartTime >= totalDuration;
    }

    public void autoShoot() {
        if (!effectActive || isEffectExpired()) return;

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastShootTime >= SHOOT_INTERVAL) {
            Paddle paddle = GameEngine.getPaddle();
            if (paddle != null) {
                createBulletsFromBothSides(paddle);

                if (currentTime - lastShootTime >= 300) {
                    Sound.playSFX("shoot.wav");
                }

                lastShootTime = currentTime;
            }
        }
    }

    private void createBulletsFromBothSides(Paddle paddle) {
        int leftBulletX = paddle.getX();
        int leftBulletY = paddle.getY() - 15;

        int rightBulletX = paddle.getX() + paddle.getWidth() - 10;
        int rightBulletY = paddle.getY() - 15;

        Bullet leftBullet = new Bullet(leftBulletX, leftBulletY, 10, 15, -7);
        Bullet rightBullet = new Bullet(rightBulletX, rightBulletY, 10, 15, -7);

        bullets.add(leftBullet);
        bullets.add(rightBullet);
    }

    public void updateBullets() {
        if (!effectActive) return;

        autoShoot();

        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Brick> bricksToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            bullet.update();

            for (Brick brick : GameEngine.getBricks()) {
                if (!brick.isDestroyed() && bullet.checkCollision(brick)) {
                    if (!(brick instanceof UnbreakableBrick)) {
                        brick.setDestroyed(true);
                        bricksToRemove.add(brick);
                        bulletsToRemove.add(bullet);

                        GameEngine.setScore(GameEngine.getScore() + brick.getHitPoints());
                        GameEngine.getLevel().setNumOfBricksToLvlUp(
                                GameEngine.getLevel().getNumOfBricksToLvlUp() - 1
                        );

                        Sound.playSFX("Break.wav");
                    } else {
                        bulletsToRemove.add(bullet);
                    }
                    break;
                }
            }

            if (bullet.getY() + bullet.getHeight() < 0) {
                bulletsToRemove.add(bullet);
            }
        }

        GameEngine.getBricks().removeAll(bricksToRemove);

        bullets.removeAll(bulletsToRemove);

        if (isEffectExpired()) {
            removeEffect();
        }
    }

    public void renderBullets() {
        if (!effectActive) return;

        for (Bullet bullet : bullets) {
            bullet.render();
        }
    }

    public Image getImage() {
        return image;
    }

    public boolean isEffectActive() {
        return effectActive && !isEffectExpired();
    }

    public int getTotalDuration() {
        return totalDuration;
    }
}