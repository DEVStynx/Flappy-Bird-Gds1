package de.school.game.entity;

import java.awt.image.BufferedImage;
import java.io.InvalidClassException;

public class TileObject extends RenderableObject{
    public int collisionId;

    public TileObject(int x, int y, int collisionId) {
        super(x, y);
        this.collisionId = collisionId;
    }



    @Override
    public BufferedImage getTexture() {
        return null;
    }
}
