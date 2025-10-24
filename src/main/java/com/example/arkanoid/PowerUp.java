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
        this.active = false;
        this.duration = 3000;
        this.type = type;
        this.effectActive = false;
    }

    public abstract void applyEffect();

    public abstract void removeEffect();

    public boolean isEffectExpired() {
        if (effectStartTime == 0) return false;
        return Math.abs(System.currentTimeMillis() - effectStartTime) >= duration;
    }

    public void startEffectTimer() {
        this.effectStartTime = System.currentTimeMillis();
        this.active = false;
    }

    public boolean checkPaddleCollision(Paddle paddle) {
        if (!active || paddle == null) return false;

        if (getX() < paddle.getX() + paddle.getWidth() && getX() + getWidth() > paddle.getX()
                && getY() < paddle.getY() + paddle.getHeight() && getY() + getHeight() > paddle.getY()) {
            return true;
        }
        return false;
    }

    public void activate() {
        applyEffect();
        effectActive = true;
    }

    public void deactivate() {
        if (!effectActive) return;
        removeEffect();
        effectActive = false;
        effectStartTime = 0;
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

    public void setDuration(int duration) {
        this.duration = duration;
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

            if (checkPaddleCollision(GameEngine.getPaddle())) {
                activate();
            }

            if (getY() > 600) {
                active = false;
            }
        }
        if (effectActive) {
            if (isEffectExpired()) {
                deactivate();

            }
        }
    }

    @Override
    public void render() {
        if (active) {
            Renderer.getInstance().render(this);
        }
    }
}