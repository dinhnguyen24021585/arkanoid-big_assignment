package com.example.arkanoid;

public class PowerUp extends GameObject{
    private int type;
    private int duration;
    public PowerUp(int type, int duration) {
        this.type = type;
        this.duration = duration;
    }
    public PowerUp() {}

    public int getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void applyEffect() {

    }

    public void removeEffect() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

}
