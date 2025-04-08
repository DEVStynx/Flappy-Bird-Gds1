package de.school.game.audio;

import de.school.game.util.FileUtil;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Sound {
    Clip clip;
    public Sound(String name) {
        try {
            System.out.println(name);
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(FileUtil.getFileByResource("audio/"+name));
            clip.open(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public CompletableFuture<Void> play() {
        return CompletableFuture.runAsync(() -> {
            clip.setFramePosition(0);
            clip.loop(0);
            clip.start();
        });
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }

}
