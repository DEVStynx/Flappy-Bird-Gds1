package de.school.game.audio;

import de.school.game.util.FileUtil;

import javax.sound.sampled.*;
import java.util.concurrent.CompletableFuture;

/**
 * A Gamesound Class
 */
public class Sound {
    Clip clip;

    /**
     * The constructor to load the sound of the package
     * @param name
     */
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

    /**
     * Play the Song asynchronously
     * @param loop Should the song be looped?
     * @return a {@link CompletableFuture<Void>} to play the sound asynchronously easy
     */
    public CompletableFuture<Void> play(boolean loop) {
        return CompletableFuture.runAsync(() -> {
            clip.setFramePosition(0);
            clip.loop(0);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        });
    }

    /**
     * Stop the current Sound
     */
    public void stop() {
        clip.stop();
    }

}
