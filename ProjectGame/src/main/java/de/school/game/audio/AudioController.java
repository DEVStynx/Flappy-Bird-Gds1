package de.school.game.audio;

import javax.sound.sampled.Clip;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AudioController {
    public Map<String,Clip> gamesounds;

    public AudioController() {
        gamesounds = new HashMap<>();
    }

    public CompletableFuture<Void> playSound(String sound) {
        return CompletableFuture.runAsync(() -> {
            Clip soundclip = gamesounds.get(sound);
        });
    }
}
