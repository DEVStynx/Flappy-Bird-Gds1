package de.school.game.audio;

import de.school.game.util.FileUtil;

import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AudioController {
    public Map<String,Sound> gamesounds;

    public AudioController() {
        gamesounds = new HashMap<>();
    }
    public void loadByDir() throws FileNotFoundException {
        File audioDirectory = new File(ClassLoader.getSystemResource("audio").getPath());
        for (File file : audioDirectory.listFiles()) {
            Sound sound = new Sound(file.getName());
            gamesounds.put(file.getName(),sound);
            System.out.println("Found and registered Sound : "+file.getName());
        }
        if (!audioDirectory.exists()) {
            throw new FileNotFoundException("Couldn't find audio folder!");
        }
    }

    public void playSound(String sound) {
        Sound soundclip = gamesounds.get(sound);
        soundclip.play();
    }
    public void stopSound(String sound) {
        gamesounds.get(sound).stop();
    }

    public void loop(String sound) {
        gamesounds.get(sound).loop();
    }
}
