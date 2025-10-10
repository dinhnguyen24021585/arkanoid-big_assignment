package com.example.arkanoid;

public abstract class PowerUp extends GameObject {
    protected boolean active;
    protected int duration;

    public PowerUp(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.active = true;
        this.duration = 5000;
    }

    public abstract void applyEffect(Paddle paddle);

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void update() {
        setY(getY() + 2);
    }

    @Override
    public void render() {
        if (active) {
            Renderer.getInstance().render(this);
        }
    }
}