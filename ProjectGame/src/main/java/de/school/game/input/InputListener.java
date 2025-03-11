package de.school.game.input;

import de.school.game.Game;
import de.school.game.entity.player.PlayerDirection;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputListener implements java.awt.event.KeyListener, MouseListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println();
            Game.player().y += 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (Game.player().direction == PlayerDirection.LEFT) {
                Game.player().direction = PlayerDirection.RIGHT;
            } else {
                Game.player().direction = PlayerDirection.LEFT;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    //Mouse Input
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
