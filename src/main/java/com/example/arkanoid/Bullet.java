package com.example.arkanoid;

import javafx.scene.image.Image;

public class Bullet extends GameObject {
    private int speedY;
    private Image image;

    public Bullet(int x, int y, int width, int height, int speedY) {
        super(x, y, width, height);
        this.speedY = speedY;

        try {
            this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/bullet.png"));
        } catch (Exception e) {
            this.image = null;
        }
    }

    @Override
    public void update() {
        setY(getY() + speedY);
    }

    @Override
    public void render() {
        Renderer.getInstance().render(this);
    }

    public boolean checkCollision(GameObject other) {
        return getX() < other.getX() + other.getWidth() &&
                getX() + getWidth() > other.getX() &&
                getY() < other.getY() + other.getHeight() &&
                getY() + getHeight() > other.getY();
    }

    public Image getImage() {
        return image;
    }
}