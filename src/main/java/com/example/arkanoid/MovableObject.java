package com.example.arkanoid;

public abstract class MovableObject {
    private int dx;
    private int dy;

    public MovableObject(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public MovableObject(){}

    public int getDx() {
        return this.dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return this.dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void move(){}

}
