package de.school.game.util.rendering;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RenderUtil {
    /**
     * Utility Method to quickly load Images
     * @param image The Image Path -> FileUtil.getFileByResource()
     * @return BufferedImage
     */
    public static BufferedImage loadTexture(File image) {
        try {
            return ImageIO.read(image);
        } catch (IOException e) {
            throw new FileOpeningException();
        }

    }
}
