package de.school.game.audio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Handler/Controller for Gamesounds:
 * <p>Store all sounds in a Registry, which can be accessed by a {@link String key}</p>
 */
public class AudioController {
    public Map<String,Sound> gamesounds;

    public AudioController() {
        gamesounds = new HashMap<>();
    }

    /**
     * Loads all Sounds from the audio package
     * and stores the in the registry
     * @throws FileNotFoundException
     */
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

    /**
     * Play the Sound Asynchronously
     * @param sound The Sound to play
     */
    public void playSound(String sound) {
        Sound soundclip = gamesounds.get(sound);
        soundclip.play(false);
    }

    /**
     * Play the Sound Asynchronously and specify wether it should be looped
     * @param sound The {@link Sound} to play
     * @param loop Should the sound be looped?
     */
    public void playSound(String sound, boolean loop) {
        Sound soundclip = gamesounds.get(sound);
        soundclip.play(loop);
    }

    /**
     * Stop the sound
     * @param sound The Sound Key
     */
    public void stopSound(String sound) {
        gamesounds.get(sound).stop();
    }


}
