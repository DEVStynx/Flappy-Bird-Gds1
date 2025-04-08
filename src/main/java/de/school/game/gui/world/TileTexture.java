package de.school.game.gui.world;

import java.awt.image.BufferedImage;

public class TileTexture {
    public BufferedImage texture;

    public TileTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public BufferedImage getTexture() {
        return this.texture;
    }
}
