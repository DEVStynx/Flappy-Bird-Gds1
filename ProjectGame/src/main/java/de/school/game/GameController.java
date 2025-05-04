package de.school.game;

import de.school.game.gui.menu.MainMenu;

public class GameController {

    private Gamestate gamestate;
    public boolean debug;

    public GameController(boolean isDebugging) {
        gamestate = Gamestate.RUNNING;
        debug = isDebugging;
    }

    public void setGamestate(Gamestate gamestate) {
        this.gamestate = gamestate;
    }
    public Gamestate getGamestate() {
        return gamestate;
    }

    /**
     * The Standard Method to Win the Game
     */
    public void winGame() {
        Game.gameClock().scoreManager.saveCurrentScore(Game.getCurrentLevel());
        System.out.println("Game Won!");
        setGamestate(Gamestate.WON);
        Game.audioController().stopSound("background.wav");
        Game.audioController().playSound("win.wav");
        Game.gameWindow().repaint();
    }

    /**
     * The Standard Method to Lose the Game
     */

    public void loseGame() {
        System.out.println("Current Level: "+Game.getCurrentLevel());
        Game.gameClock().killGameThread();
        Game.gameController().setGamestate(Gamestate.STARTING);
        Game.player().deletePlayer();
        Game.audioController().stopSound("background.wav");
        Game.audioController().playSound("death.wav");
        Game.loadLevel("/maps/"+Game.getCurrentLevel());
        System.out.println("Game Lost!");
        Game.gameWindow().repaint();
    }

    /**
     * The different gamestates as an enum
     */

    public enum Gamestate {
        //Menü Gamestate
        MENU,
        //Gamestate für das Warten auf den Tastendruck der Leertaste, zum starten des Spiels
        STARTING,
        //Gamestate für das Spiel
        RUNNING,
        //Gamestate für das Pausieren des Spiels
        PAUSED,
        //Gamestate für das Gewinnen des Spiels
        WON
    }
}
