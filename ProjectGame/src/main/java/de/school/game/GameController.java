package de.school.game;

public class GameController {

    private Gamestate gamestate;

    public GameController() {
        gamestate = Gamestate.RUNNING;
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
        setGamestate(Gamestate.LOST);
        System.out.println("Game Lost!");
    }


    public enum Gamestate {
        MENU,
        RUNNING,
        WON,
        LOST,
        PAUSED
    }
}
