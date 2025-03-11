package de.school.game.gui;

import de.school.game.Game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel {

    public int x = 0;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        g.drawImage(Game.player().getTexture(),Game.player().x,Game.player().y,null);


        //Don't put anything here
        g.dispose();
    }
}
