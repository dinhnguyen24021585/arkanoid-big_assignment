package com.example.arkanoid;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class ExplosiveBrick extends Brick {
    public ExplosiveBrick(int x, int y) {
        super(x, y, 80, 30, 1, -1);
        loadImage();
    }

    @Override
    protected void loadImage() {
        image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/brick-1.png"));
    }

    @Override
    public int takeHits() {
        if (!destroyed) {
            destroyed = true;
            System.out.println(1);
            explode(GameEngine.getBricks());
            return hitPoints;
        }
        return 0;
    }

    private void explode(ArrayList<Brick> bricks) {
        for (Brick b : bricks) {
            if (b == this || b.isDestroyed() || b instanceof UnbreakableBrick) continue;

            if (isNear(b)) {
                if (b instanceof ExplosiveBrick explosive) {
                    explosive.takeHits();
                } else {
                    b.takeHits();
                }
            }
        }
    }

    private boolean isNear(Brick b) {
        int dx = Math.abs(b.getX() - this.getX());
        int dy = Math.abs(b.getY() - this.getY());
        return dx <= 90 && dy <= 40 ;
    }
}
