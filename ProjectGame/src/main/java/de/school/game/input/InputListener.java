package de.school.game.input;

import de.school.game.Game;
import de.school.game.GameController;
import de.school.game.entity.player.PlayerDirection;
import de.school.game.gui.menu.MainMenu;
import de.school.game.gui.menu.Menu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * receives Keyboard input
 * extends {@link KeyAdapter} instead of the {@link java.awt.event.KeyListener KeyListener} to avoid unnecessary implemented Methods
 */
public class InputListener extends KeyAdapter {


    @Override
    public void keyPressed(KeyEvent e) {
        //Keyboardinput to pause the game with escape
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.gameController().setGamestate(Game.gameController().getGamestate() == GameController.Gamestate.RUNNING ? GameController.Gamestate.PAUSED  : GameController.Gamestate.RUNNING);
            if (Game.gameController().getGamestate().equals(GameController.Gamestate.RUNNING)) {
                Game.gameClock().scoreManager.setLastUpdate();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //Keyboardinput to start the Game
            if (Game.gameController().getGamestate() == GameController.Gamestate.STARTING) {
                Game.gameController().setGamestate(GameController.Gamestate.RUNNING);
                return;
            }
            if (Game.gameController().getGamestate() == GameController.Gamestate.WON) {
                Game.gameController().setGamestate(GameController.Gamestate.MENU);

                //Anzeigen des Startmenüs
                Menu.windowLocation = Game.gameWindow().getLocation();
                Game.showGameWindow(false);
                Game.player().deletePlayer();
                Game.gameClock().killGameThread();
                Game.mainMenu().showMenu();
                Game.mainMenu().setLocation(Menu.windowLocation);
            }
            //Keyboardinput to let the player jump
            Game.player().jump();
        }
        //Keyboardinput to turn the players position
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (Game.player().direction == PlayerDirection.LEFT) {
                Game.player().direction = PlayerDirection.RIGHT;
            } else {
                Game.player().direction = PlayerDirection.LEFT;
            }
        }
    }


}
