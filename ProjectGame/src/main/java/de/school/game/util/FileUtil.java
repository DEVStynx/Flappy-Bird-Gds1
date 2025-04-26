package de.school.game.util;

import de.school.game.entity.player.PlayerEntity;
import de.school.game.util.rendering.FileOpeningException;

import java.io.File;
import java.net.URL;

public class FileUtil {
    /**
     * Utility Method to quickly get File Objects out of a String resource path
     * @param resource
     * @return
     */
    public static File getFileByResource(String resource) {
        // Entferne führenden Slash
        resource = resource.startsWith("/") ? resource.substring(1) : resource;

        URL url = FileUtil.class.getClassLoader().getResource(resource);
        if (url == null) {
            throw new FileOpeningException("Resource nicht gefunden: " + resource);
        }

        File file = new File(url.getPath());
        if (!file.exists()) {
            throw new FileOpeningException("Datei existiert nicht: " + file.getPath());
        }

        return file;
    }
}
