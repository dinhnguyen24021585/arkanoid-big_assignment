package com.example.arkanoid.PowerUps;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameEngine;
import javafx.scene.image.Image;

public class PortalPowerUp extends PowerUp {
    private Image image;

    public PortalPowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 7);
        this.duration = 30000;

        try {
            this.image = new Image(getClass().getResourceAsStream("/com/example/arkanoid/Image/powerUpImages/powerup_portal.png"));
        } catch (Exception e) {
            this.image = null;
        }
    }

    @Override
    public void applyEffect() {
        if (effectActive) return;

        effectActive = true;
        startEffectTimer();
        createRandomPortals();
    }

    @Override
    public void removeEffect() {
        if (!effectActive) return;

        GameEngine.disablePortals();
        effectActive = false;
        effectStartTime = 0;

        System.out.println("Portals deactivated!");
    }

    private void createRandomPortals() {
        int portalX, portalY;

        portalX = 100 + (int) (Math.random() * (GameConst.WIDTH - 200));
        portalY = 220 + (int) (Math.random() * 50);

        GameEngine.setPortals(portalX, portalY, 0, 0);
    }

    public Image getImage() {
        return image;
    }
}