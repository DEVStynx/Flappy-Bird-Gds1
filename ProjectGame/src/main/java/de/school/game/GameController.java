package de.school.game;

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
        setGamestate(Gamestate.WON);
        System.out.println("Game Won!");
    }

    public void loseGame() {
        Game.gameClock().scoreManager.updateLevelTime(System.nanoTime());
        Game.gameClock().scoreManager.saveInFile("level0");
        setGamestate(Gamestate.LOST);
        System.out.println("Game Lost!");
        setGamestate(Gamestate.MENU);
        Game.player().deletePlayer();
        Game.gameClock().killGameThread();
        Game.mainMenu().showMenu();
    }


    public enum Gamestate {
        //Menü Gamestate
        MENU,
        //Gamestate für das Warten auf den Tastendruck der Leertaste, zum starten des Spiels
        STARTING,
        //Gamestate für das Spiel
        RUNNING,
        //Gamestate für das Gewinnen des Spiels
        WON,
        //Gamestate für das verlieren des Spiels
        LOST,
        //Gamestate für das Pausieren des Spiels
        PAUSED
    }
}
