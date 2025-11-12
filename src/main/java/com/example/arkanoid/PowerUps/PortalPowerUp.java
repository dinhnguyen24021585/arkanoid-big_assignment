package com.example.arkanoid.PowerUps;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameEngine;
import javafx.scene.image.Image;

public class PortalPowerUp extends PowerUp {
    private Image image;
    private static int portal1X, portal1Y;
    private static boolean portalsActive = false;

    public PortalPowerUp(int x, int y) {
        super(x, y, GameConst.PowerWidth, GameConst.PowerHeight, 7);
        this.duration = 15000;

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

        disablePortals();
        effectActive = false;
        effectStartTime = 0;
    }

    private void createRandomPortals() {
        int portalX, portalY;

        portalX = 100 + (int) (Math.random() * (GameConst.WIDTH - 200));
        portalY = 220 + (int) (Math.random() * 50);

        setPortals(portalX, portalY);
    }

    public static void setPortals(int x1, int y1) {
        portal1X = x1;
        portal1Y = y1;
        portalsActive = true;
    }

    public static void disablePortals() {
        portalsActive = false;
    }

    public static boolean isPortalsActive() {
        return portalsActive;
    }

    public static int getPortal1X() {
        return portal1X;
    }

    public static int getPortal1Y() {
        return portal1Y;
    }

    public Image getImage() {
        return image;
    }
}