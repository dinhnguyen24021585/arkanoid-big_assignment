package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public class Brick  extends GameObject {
    private int hitPoints;
    private int type;
    private boolean destroyed = false;
    private Image image;

    public Brick(int x, int y, int width, int height, int hitPoints, int type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
        loadImage();
    }

    public Brick() {
        super(0, 0, 60, 20);
        this.hitPoints = 1;
        this.type = 1;
    }

    private void loadImage() {
        switch (type) {
            case 1:
                image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/normal brick.png"));
                break;
            case 2:
                image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/normal brick.png"));
                break;
            case 3:
                image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/normal brick.png"));
                break;
            default:
                image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/normal brick.png"));
                break;
        }
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Image getImage() {
        return image;
    }

    public void takeHits() {
        if (!destroyed) {
            hitPoints--;
            if (hitPoints <= 0) {
                destroyed = true;
            }
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        if (!destroyed) {
            Renderer.getInstance().render(this);
        }
    }
}