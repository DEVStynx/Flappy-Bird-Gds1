package de.school.game.gui.world;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage texture;

    public Tile(BufferedImage texture) {
        this.texture = texture;
    }

    public BufferedImage getTexture() {
        return this.texture;
    }
}
