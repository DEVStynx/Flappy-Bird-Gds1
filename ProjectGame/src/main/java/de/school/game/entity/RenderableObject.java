package de.school.game.entity;

import de.school.game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Main Entity Class for the:
 * <p>{@link de.school.game.entity.player.PlayerEntity Player}</p>
 * <p>{@link TileObject Tiles}</p>
 */
public abstract class RenderableObject {

    public int x;
    public int y;

    protected Rectangle hitbox;

    public RenderableObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(new Dimension(Game.gameWindow().tileSize, Game.gameWindow().tileSize));
    }

    /**
     * Used for Collisions
     * @return the {@link Rectangle Hitbox}
     */
    public Rectangle getHitbox() {
        Rectangle locatedhitbox =  new Rectangle(this.hitbox);
        locatedhitbox.setLocation(this.x, this.y);
        return locatedhitbox;
    }

    public abstract BufferedImage getTexture();



}
