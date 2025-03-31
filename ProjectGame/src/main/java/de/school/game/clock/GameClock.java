package de.school.game.clock;

import de.school.game.Game;
import de.school.game.GameController;
import de.school.game.gui.GameWindow;

public class GameClock implements Runnable{
    public int FPS;
    public Thread GameThread;
    public double DeltaTime;
    public double UpdateIntervall;
    public boolean update;

    public GameClock(int pFPS) {
        FPS = pFPS;
        GameThread = new Thread(this);
        update = false;

    }
    public void startGameThread() {
        if (GameThread != null) {
            GameThread.start();
        }
    }

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

    public void update() {

        Game.gameWindow().repaint();
        if (Game.player() != null) {
            Game.player().handleMovement();
            if (Game.gameCollisionManager() != null)
                Game.gameCollisionManager().get_nearbyTiles();
        }

    }
}
