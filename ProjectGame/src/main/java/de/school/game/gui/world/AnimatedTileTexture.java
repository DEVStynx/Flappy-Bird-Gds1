package de.school.game.gui.world;

import de.school.game.gui.Animation;
import de.school.game.util.FileUtil;

import java.awt.image.BufferedImage;
import java.io.File;

public class AnimatedTileTexture extends TileTexture{
    public Animation animation;
    public AnimatedTileTexture(String texture,int duration) {
        super(null);
        animation = Animation.loadByDir(FileUtil.getFileByResource(texture),duration,true);
        animation.resumeAnimation();
    }

    @Override
    public BufferedImage getTexture() {
        return animation.getCurrentTexture();
    }
}
