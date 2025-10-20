package com.example.arkanoid;

public abstract class PowerUp extends GameObject {
    protected boolean active;
    protected int duration;
    protected int type;
    protected boolean effectApplied = false;
    protected long effectStartTime = 0;
    protected boolean effectActive = false;

    public PowerUp(int x, int y, int width, int height, int type) {
        super(x, y, width, height);
        this.active = true;
        this.duration = 10000;
        this.type = type;
        this.effectApplied = false;
        this.effectActive = false;
    }

    public abstract void applyEffect();
    public abstract void removeEffect();

    public boolean isEffectExpired() {
        if (effectStartTime == 0) return false;
        return (System.currentTimeMillis() - effectStartTime) >= duration;
    }

    public void startEffectTimer() {
        this.effectStartTime = System.currentTimeMillis();
        this.effectApplied = true;
        this.effectActive = true;
        this.active = false;
    }

    public boolean checkPaddleCollision(Paddle paddle) {
        if (!active || paddle == null) return false;
        return paddle.checkCollision(paddle);
    }

    public void activate(GameEngine gameEngine) {
        if (effectApplied) return;

        applyEffect();
        startEffectTimer();
    }

    public void deactivate(GameEngine gameEngine) {
        if (!effectActive) return;

        removeEffect();
        effectActive = false;
        effectApplied = false;
        effectStartTime = 0;
    }

    public void updatePowerUp(Paddle paddle, GameEngine gameEngine) {
        if (active) {
            setY(getY() + 2);

            if (checkPaddleCollision(paddle)) {
                activate(gameEngine);
            }

            if (getY() > 600) {
                active = false;
            }
        } else if (effectApplied && effectActive) {
            if (isEffectExpired()) {
                deactivate(gameEngine);
            }
        }
    }

    public boolean isActive() {
        return active || effectActive;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDuration() {
        return duration;
    }

    public int getType() {
        return type;
    }

    public boolean isEffectApplied() {
        return effectApplied;
    }

    public boolean isEffectActive() {
        return effectActive;
    }

    @Override
    public void update() {
        if (active) {
            setY(getY() + 2);
        }
    }

    @Override
    public void render() {
        if (active) {
            Renderer.getInstance().render(this);
        }
    }
}