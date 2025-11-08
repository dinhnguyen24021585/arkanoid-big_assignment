package com.example.arkanoid;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
    private static MediaPlayer backgroundMusicPlayer;

    private static double bgmVolume = 0.5;
    private static double sfxVolume = 0.5;

    public static void initializeBGM(String fileName) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }

        String path = Sound.class.getResource("/com/example/arkanoid/Sound/" + fileName).toString();

        Media media = new Media(path);
        backgroundMusicPlayer = new MediaPlayer(media);

        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        backgroundMusicPlayer.setVolume(bgmVolume);
    }

    public static void playBGM() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
    }

    public static void stopBGM() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    public static void playSFX(String fileName) {
        String path = Sound.class.getResource("/com/example/arkanoid/Sound/" + fileName).toString();

        AudioClip clip = new AudioClip(path);

        clip.setVolume(sfxVolume);
        clip.play();
    }

    public static void setBgmVolume(double volume) {
        bgmVolume = volume;
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(bgmVolume);
        }
    }

    public static void setSfxVolume(double volume) {
        sfxVolume = volume;
    }

    public static double getBgmVolume() {
        return bgmVolume;
    }

    public static double getSfxVolume() {
        return sfxVolume;
    }
}