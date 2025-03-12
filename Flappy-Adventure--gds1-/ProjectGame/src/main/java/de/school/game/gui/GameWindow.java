package de.school.game.gui;

import de.school.game.Game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel {

    public final int originalTilesize = 16;//16x16
    public final int renderScale = 3;

    public final int tileSize = originalTilesize * renderScale;//48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRows = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int ScreenHeight = tileSize * maxScreenRows;

    public GameWindow() {
        this.setPreferredSize(new Dimension(screenWidth,ScreenHeight));
        this.setDoubleBuffered(true);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        Game.worldTileManager().render(graphics2D);
        g.drawImage(Game.player().getTexture(),Game.player().x,Game.player().y,null);


        //Don't put anything here
        g.dispose();
    }
}
