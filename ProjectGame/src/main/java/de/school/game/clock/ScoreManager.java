package de.school.game.clock;

import de.school.game.Game;
import de.school.game.GameController;
import de.school.game.util.FileUtil;

import java.io.*;

/**
 * Klasse für die Zeitmessung
 */
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

    /**
     * Methode zum Speichern der Bestzeit
     * @param level Das jetzige Level
     */
    public void saveCurrentScore(String level) {

        File scoresDir = FileUtil.getFileByResource("scores/");
        File saveFile = new File(scoresDir, level + ".save");
        double lastScore = readBestScore(level);
        //Wir müssen den neuen Score nicht speichern, wenn der alte besser war
        if (lastScore < getTimeInCurrentLevelSec()) {
            return;
        }

        if (!saveFile.exists()) {
            try {
                boolean s = saveFile.createNewFile();
                System.out.println("creating new file: "+s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(saveFile))) {
            bufferedWriter.write(getTimeInCurrentLevelSec() + "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Gibt die jetzige Bestzeit für das jewilige Level zurück.
     * @implNote Wenn das Level bis jetzt keine Bestzeit hat, wird Double.MAX_VALUE zurückgegeben
     * @param level Das jetzige Level als String
     * @return Die Bestzeit für das jeweilige Level
     */
    public double readBestScore(String level) {
        File scoresDir = FileUtil.getFileByResource("scores/");
        File saveFile = new File(scoresDir, level + ".save");
        if (!saveFile.exists()) {
            try {
                boolean s = saveFile.createNewFile();
                System.out.println("creating new file: "+s);
                return Double.MAX_VALUE;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(saveFile))) {
            String num = bufferedReader.readLine();
            return Double.parseDouble(num);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
