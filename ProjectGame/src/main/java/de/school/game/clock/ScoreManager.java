package de.school.game.clock;

import de.school.game.Game;
import de.school.game.GameController;
import de.school.game.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreManager {

    private double timeInCurrentLevel;
    private long lastUpdate;

    //Dies soll nur gecallt werden, wenn der Spieler ein Level startet
    public ScoreManager() {
        lastUpdate = System.currentTimeMillis();
        timeInCurrentLevel = 0;
    }

    //Die Zeit die der Spieler für das aktuelle Level benötigt wird inkrementiert
    public void addTimeToCounter() {
        //Damit nicht die Zeit aktualisiert wird, wenn das Spiel aktualisiert ist
        if (Game.gameController().getGamestate().equals(GameController.Gamestate.PAUSED)) {
            lastUpdate = System.currentTimeMillis();
        }
        timeInCurrentLevel += System.currentTimeMillis() - lastUpdate;

        lastUpdate = System.currentTimeMillis();
    }

    public double getTimeInCurrentLevelMs() {
        return timeInCurrentLevel;
    }

    public double getTimeInCurrentLevelSec() {
        return getTimeInCurrentLevelMs() / 1000;
    }

    public void setLastUpdate() {
        lastUpdate = System.currentTimeMillis();
    }

    public void saveCurrentScore(String level) {
        File scoresDir = FileUtil.getFileByResource("scores/");
        File saveFile = new File(scoresDir, level + ".save");
        if (!saveFile.exists()) {
            try {
                boolean s = saveFile.createNewFile();
                System.out.println("creating new file: "+s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("ex2");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(saveFile))) {
            bufferedWriter.write(getTimeInCurrentLevelSec() + "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
