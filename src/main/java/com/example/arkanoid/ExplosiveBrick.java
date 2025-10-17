package com.example.arkanoid;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class ExplosiveBrick extends Brick {
    public ExplosiveBrick(int x, int y) {
        super(x, y, 60, 20, 1, 1);
        loadImage();
    }

    @Override
    protected void loadImage() {
        image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/ExplosiveBrick.png"));
    }

    public int takeHit(ArrayList<Brick> allBricks) {
        if (!destroyed) {
            destroyed = true;
            explode(allBricks);
            return hitPoints;
        }
        return 0;
    }

    private void explode(ArrayList<Brick> allBricks) {
        for (Brick b : allBricks) {
            if (b == this || b.isDestroyed() || b instanceof UnbreakableBrick) continue;

            if (isNear(b)) {
                if (b instanceof ExplosiveBrick explosive) {
                    explosive.takeHit(allBricks);
                } else {
                    b.setType(b.getType() - 1);
                    if (b.getType() <= 0) {
                        b.destroyed = true;
                    } else {
                        b.loadImage();
                    }
                }
            }
        }
    }

    private boolean isNear(Brick b) {
        int dx = Math.abs(b.getX() - this.getX());
        int dy = Math.abs(b.getY() - this.getY());
        return dx <= 60 && dy <= 20 && !(dx == 0 && dy == 0);
    }
}
