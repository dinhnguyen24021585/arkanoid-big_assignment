package com.example.arkanoid;

public class Ball extends MovableObject {
    private int speed;
    private int directionX;
    private int directionY;

    public Ball(int speed, int directionX, int directionY){
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public void bounceOff(GameObject other){

    }

    public void checkCollision(GameObject other){

    }
}
