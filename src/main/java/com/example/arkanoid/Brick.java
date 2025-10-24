package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public abstract class Brick  extends GameObject {
    protected int hitPoints;
    protected int type;
    protected boolean destroyed = false;
    protected Image image;

    public Brick(int x, int y, int width, int height, int hitPoints, int type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
        loadImage();
    }

    public Brick() {
        this(0, 0, GameConst.BrickWidth, GameConst.BrickHeight, 1, 1);
    }

    protected void loadImage() {
        String path = "/com/example/arkanoid/Image/brick" + type + ".png";
        image = new Image(getClass().getResourceAsStream(path));
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

    public abstract int takeHits();

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