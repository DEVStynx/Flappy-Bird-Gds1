package de.school.game.entity;

import java.awt.image.BufferedImage;

public abstract class RenderableObject {

    public int x;
    public int y;

    public RenderableObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract BufferedImage getTexture();



}
