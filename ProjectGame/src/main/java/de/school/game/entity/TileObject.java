package de.school.game.entity;

import de.school.game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Object of Tiles for:
 * <p>Collision management</p>
 * <p>Rendering</p>
 */
public class TileObject extends RenderableObject{
    public int collisionId;
    private int width;
    private int height;

    public TileObject(int x, int y, int collisionId) {
        super(x, y);
        this.collisionId = collisionId;
        this.width = Game.gameWindow().tileSize;
        this.height = Game.gameWindow().tileSize;
    }
    public TileObject(int x, int y, int collisionId, int width, int height) {
        this(x,y,collisionId);
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x,y,width,height);
    }

    @Override
    public BufferedImage getTexture() {
        return null;
    }
}
