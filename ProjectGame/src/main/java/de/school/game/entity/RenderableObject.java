package de.school.game.entity;

import de.school.game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class RenderableObject {

    public int x;
    public int y;

    private Rectangle hitbox;

    public RenderableObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(new Dimension(Game.gameWindow().tileSize, Game.gameWindow().tileSize));
    }

    public Rectangle getHitbox() {
        Rectangle locatedhitbox =  new Rectangle(this.hitbox);
        locatedhitbox.setLocation(this.x, this.y);
        return locatedhitbox;
    }

    public abstract BufferedImage getTexture();



}
