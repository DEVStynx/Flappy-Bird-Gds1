package de.school.game.clock;

import de.school.game.Game;
import de.school.game.GameController;

/**
 * The GameClock Class for Game-Updates
 */
public class GameClock implements Runnable{
    public int FPS;
    public Thread GameThread;
    public double DeltaTime;
    public double UpdateIntervall;
    public boolean update;
    public ScoreManager scoreManager;

    /**
     * The GameClock Constructor
     * @param pFPS Frames-per-second
     */
    public GameClock(int pFPS) {
        FPS = pFPS;
        GameThread = new Thread(this);
        update = false;
        System.out.println("Starting GameClock with " + FPS + " FPS");
        scoreManager = new ScoreManager();
    }

    /**
     * Starts the Game Clock/Updater
     */
    public void startGameThread() {
        if (GameThread != null) {
            GameThread.start();

        }
    }

    /**
     * Stops the Game Clock/Updater
     */
    public void killGameThread() {
        if (GameThread != null) {
            GameThread.interrupt();
            GameThread = null;
        }
    }

    /**
     * Calculates the deltatime and checks if the Game can update
     * <p>Then calls the update Method</p>
     */
    @Override
    public void run() {
        //Da wir hier mit Nanosekunden arbeiten
        UpdateIntervall = 1000000000 / FPS;
        DeltaTime = 0.0D;
        long lastUpdate = System.nanoTime();
        long currentUpdate;

        while (GameThread != null && GameThread.isAlive()) {
            currentUpdate = System.nanoTime();

            DeltaTime += (currentUpdate -lastUpdate) / UpdateIntervall;
            lastUpdate = currentUpdate;

            if (DeltaTime >= 1) {
                DeltaTime--;
                if (Game.gameController().getGamestate().equals(GameController.Gamestate.RUNNING))
                    update();
                update = true;
            } else {
                update = false;
            }
        }

    }

    /**
     * The Main update Method if the Game is updating
     */
    public void update() {
        //Wenn der Spieler in einem Menü ist kein Game-Update ausführen
        //If the player is in a menu, don't update the Game
        if (Game.gameController().getGamestate() != GameController.Gamestate.RUNNING)
            return;

        //Game-Update wird ausgeführt
        //Update the game screen
        Game.gameWindow().repaint();


        if (Game.player() != null) {
            Game.player().handleMovement();
            if (Game.gameCollisionManager() != null)
                Game.gameCollisionManager().get_nearbyTiles();
        }

    }
}
