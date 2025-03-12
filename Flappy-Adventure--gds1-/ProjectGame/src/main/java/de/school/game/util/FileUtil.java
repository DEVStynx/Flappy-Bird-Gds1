package de.school.game.util;

import de.school.game.entity.player.PlayerEntity;
import de.school.game.util.rendering.FileOpeningException;

import java.io.File;
import java.net.URL;

public class FileUtil {
    public static File getFileByResource(String resource) {
        URL url = PlayerEntity.class.getClassLoader().getResource(resource);
        File file = new File(url.getPath());
        if (url == null|| file == null) {
            throw new FileOpeningException("The Given resource isn't available");
        }
        return file;
    }
}
