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

    private long lastWindowUpdate;
    private long currentWindowUpdate;

    public GameWindow() {
        this.setPreferredSize(new Dimension(screenWidth,ScreenHeight));
        this.setDoubleBuffered(true);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        lastWindowUpdate = System.nanoTime();
        //Wir wollen das Spiel nicht rendern, wenn wir im Menü sind
        if (Game.gameController().getGamestate() == GameController.Gamestate.MENU)
            return;




        Graphics2D graphics2D = (Graphics2D) g;




        Game.worldTileManager().render(graphics2D);
        graphics2D.drawImage(Game.player().getTexture(),Game.player().x,Game.player().y,null);
        //Hier wird für Debugging-Zwecke ein Rechteck um den Spieler gezeichnet, um zu sehen, wo sich der Spieler befindet und welche Kollisionen er hat.
        if (Game.gameController().debug) {
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
        }

        currentWindowUpdate = System.nanoTime();
        //Zum beheben von Performance-Probleme, wenn das Spiel nicht läuft
        //System.out.println("renderTime: "+(currentWindowUpdate - lastWindowUpdate) / 1000000 + "ms");
        lastWindowUpdate = currentWindowUpdate;

        //Hier wird der Text gerendert, der den Spieler darauf hinweist die Leertaste zu drücken, um das Spiel zu starten.
        if (Game.gameController().getGamestate().equals(GameController.Gamestate.STARTING)) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Arial", Font.PLAIN, 20));
            graphics2D.drawString("Press Space to start the game", screenWidth / 2 -100, ScreenHeight / 2 -100);
        }

        //Don't put anything here
        g.dispose();
    }
}
