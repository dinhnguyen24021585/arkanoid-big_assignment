package com.example.arkanoid;

public class Paddle extends MovableObject {

    private int speed;
    private int currentPowerUp;

    public Paddle(int speed,int currentPowerUp){
        this.speed = speed;
        this.currentPowerUp = currentPowerUp;
    }

    public Paddle(){}

    public int  getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getCurrentPowerUp() {
        return currentPowerUp;
    }
    public void setCurrentPowerUp(int currentPowerUp) {
        this.currentPowerUp = currentPowerUp;
    }

    public void moveLeft() {

    }

    public void moveRight() {

    }

    public void applyPowerUp() {

    }

}
