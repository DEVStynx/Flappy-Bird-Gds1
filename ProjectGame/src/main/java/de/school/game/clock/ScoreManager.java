package de.school.game.clock;

import de.school.game.Game;
import de.school.game.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreManager {

    //Die Zeit die der Spieler für das aktuelle Level benötigt hat in Nanosekunden
    public float currentLevelTime;
    public long levelStartTime;

    //Dies soll nur gecallt werden, wenn der Spieler ein Level startet
    public ScoreManager() {
        currentLevelTime = 0;
        levelStartTime = System.nanoTime();
    }
    //Die Zeit die der Spieler für das aktuelle Level benötigt wird inkrementiert
    public void updateLevelTime(long nanoseconds) {
        currentLevelTime = nanoseconds;
    }

    //Speichert die jetzige Zeit in einer Datei ab. Wird aufgerufen wenn der Spieler ein Level verliert
    public void saveInFile(String level) {
        BufferedWriter writer = null;
        try {
            File file = new File(ClassLoader.getSystemResource("scores/"+level+".score").getPath());
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write( (currentLevelTime - levelStartTime)+"\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
