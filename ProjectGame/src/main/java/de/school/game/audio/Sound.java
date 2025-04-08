package de.school.game.audio;

import de.school.game.util.FileUtil;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    Clip clip;
    public Sound(String name) {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(FileUtil.getFileByResource("/audio/"+name));
            clip.open(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
