package com.example.arkanoid;

public class Brick  extends GameObject {
    private int hitPoints;
    private int type;

    public Brick(int hitPoints, int type) {
        this.hitPoints = hitPoints;
        this.type = type;
    }

    public Brick() {}



    public int getHitPoints() {
        return hitPoints;
    }

    public int getType() {
        return type;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void takeHits() {

    }

    public void isDestroyed() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }
}
