package com.example.arkanoid.PowerUps;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PowerUpManager {
    private static PowerUpManager instance;
    private Map<Class<? extends PowerUp>, PowerUp> activePowerUps;
    private Map<Class<? extends PowerUp>, Long> powerUpEndTimes;

    private PowerUpManager() {
        activePowerUps = new ConcurrentHashMap<>();
        powerUpEndTimes = new ConcurrentHashMap<>();
    }

    public static PowerUpManager getInstance() {
        if (instance == null) {
            instance = new PowerUpManager();
        }
        return instance;
    }

    public boolean canActivate(PowerUp powerUp) {
        Class<? extends PowerUp> powerUpClass = powerUp.getClass();

        if (activePowerUps.containsKey(powerUpClass)) {
            PowerUp existing = activePowerUps.get(powerUpClass);
            if (existing instanceof ShootingPowerUp && powerUp instanceof ShootingPowerUp) {
                ((ShootingPowerUp) existing).extendDuration(5000);
                return false;
            }
        }
        return true;
    }

    public void activatePowerUp(PowerUp powerUp) {
        Class<? extends PowerUp> powerUpClass = powerUp.getClass();

        powerUp.applyEffect();

        activePowerUps.put(powerUpClass, powerUp);

        // Nếu có duration, lưu thời gian kết thúc
        if (powerUp.getDuration() > 0) {
            long endTime = System.currentTimeMillis() + powerUp.getDuration();
            powerUpEndTimes.put(powerUpClass, endTime);
        }

        System.out.println("Power-up activated: " + powerUpClass.getSimpleName());
    }

    public void deactivatePowerUp(Class<? extends PowerUp> powerUpClass) {
        PowerUp powerUp = activePowerUps.get(powerUpClass);
        if (powerUp != null) {
            powerUp.removeEffect();
            activePowerUps.remove(powerUpClass);
            powerUpEndTimes.remove(powerUpClass);
            System.out.println("Power-up deactivated: " + powerUpClass.getSimpleName());
        }
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        List<Class<? extends PowerUp>> toRemove = new ArrayList<>();
        for (Map.Entry<Class<? extends PowerUp>, Long> entry : powerUpEndTimes.entrySet()) {
            if (currentTime >= entry.getValue()) {
                toRemove.add(entry.getKey());
            }
        }

        for (Class<? extends PowerUp> powerUpClass : toRemove) {
            deactivatePowerUp(powerUpClass);
        }
    }

    public boolean isPowerUpActive(Class<? extends PowerUp> powerUpClass) {
        return activePowerUps.containsKey(powerUpClass);
    }

    public void clearAllPowerUps() {
        for (PowerUp powerUp : activePowerUps.values()) {
            powerUp.removeEffect();
        }
        activePowerUps.clear();
        powerUpEndTimes.clear();
    }

    public <T extends PowerUp> T getActivePowerUp(Class<T> powerUpClass) {
        return powerUpClass.cast(activePowerUps.get(powerUpClass));
    }
}