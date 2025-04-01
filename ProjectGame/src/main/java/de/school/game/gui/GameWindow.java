package de.school.game.gui;

import de.school.game.Game;
import de.school.game.GameController;
import de.school.game.entity.TileObject;

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
        if (Game.gameController().getGamestate() == GameController.Gamestate.MENU)
            return;
        Graphics2D graphics2D = (Graphics2D) g;
        Game.worldTileManager().render(graphics2D);
        graphics2D.drawImage(Game.player().getTexture(),Game.player().x,Game.player().y,null);
        graphics2D.drawRect(Game.player().x,Game.player().y,Game.player().getHitbox().width,Game.player().getHitbox().height);
        for (TileObject tile : Game.gameCollisionManager().get_nearbyTiles()) {
            switch (tile.collisionId) {
                case 0:
                    g.setColor(Color.BLACK);
                    break;
                case 1:
                    g.setColor(Color.GRAY);
                    break;
                case 2:
                    g.setColor(Color.RED);
                    break;
                case 3:
                    g.setColor(Color.ORANGE);
                    break;
            }
            g.drawRect(tile.x,tile.y,tileSize,tileSize);
        }

        //Don't put anything here
        g.dispose();
    }
}
