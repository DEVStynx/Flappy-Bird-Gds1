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

    public void winGame() {
        MainMenu.windowLocation = Game.gameWindow().getLocation();


        System.out.println("Game Won!");
        Game.showGameWindow(false);
        setGamestate(Gamestate.MENU);
        Game.audioController().stopSound("background.wav");
        Game.audioController().playSound("win.wav");
        Game.player().deletePlayer();
        Game.gameClock().killGameThread();
        Game.mainMenu().showMenu();
    }

    public void loseGame() {
        Game.gameClock().killGameThread();
        Game.gameController().setGamestate(Gamestate.STARTING);
        Game.player().deletePlayer();
        Game.audioController().stopSound("background.wav");
        Game.audioController().playSound("death.wav");

        Game.loadLevel("/maps/map1");
        System.out.println("Game Lost!");

    }


    public enum Gamestate {
        //Menü Gamestate
        MENU,
        //Gamestate für das Warten auf den Tastendruck der Leertaste, zum starten des Spiels
        STARTING,
        //Gamestate für das Spiel
        RUNNING,
        //Gamestate für das Pausieren des Spiels
        PAUSED
    }
}
