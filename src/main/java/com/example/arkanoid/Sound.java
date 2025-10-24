package com.example.arkanoid;

import javafx.scene.media.AudioClip;

public class Sound {
    public static void play(String fileName) {
        String path = Sound.class.getResource("/com/example/arkanoid/Sound/" + fileName).toString();
        AudioClip clip = new AudioClip(path);
        clip.play();
    }
}
