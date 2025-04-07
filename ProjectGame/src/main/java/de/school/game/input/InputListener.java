package de.school.game.input;

import de.school.game.Game;
import de.school.game.GameController;
import de.school.game.entity.player.PlayerDirection;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputListener extends KeyAdapter {


    @Override
    public void keyPressed(KeyEvent e) {
        //Tastendruck für das Starten des Spiels
        if (Game.gameController().getGamestate() == GameController.Gamestate.STARTING) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                Game.gameController().setGamestate(GameController.Gamestate.RUNNING);

            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.gameController().setGamestate(Game.gameController().getGamestate() == GameController.Gamestate.RUNNING ? GameController.Gamestate.PAUSED  : GameController.Gamestate.RUNNING);
        }

        //Tastendruck für das Springen
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            Game.player().y -= Game.player().jumpPower * 10;
            Game.player().gravitySpeed = 1f;


            //Game.player().jumpPlayer();
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (Game.player().direction == PlayerDirection.LEFT) {
                Game.player().direction = PlayerDirection.RIGHT;
            } else {
                Game.player().direction = PlayerDirection.LEFT;
            }
        }
    }


}
