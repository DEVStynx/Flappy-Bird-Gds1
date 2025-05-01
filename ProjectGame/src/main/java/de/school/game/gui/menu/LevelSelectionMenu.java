package de.school.game.gui.menu;

import de.school.game.Game;
import de.school.game.util.FileUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Level-selection Menu class
 */
public class LevelSelectionMenu extends Menu{
    BufferedImage backgroundImage;
    int mapcounter = 0;
    public LevelSelectionMenu() {
        super("Level Selection Menu");
    }

    /**
     * Implemented to initialize and position all Components correctly
     */
    @Override
    public void initWidgets() {
        setMinimumSize(new Dimension(Game.gameWindow().screenWidth,Game.gameWindow().ScreenHeight));
        setLayout(null);
        setResizable(false);

        try {
            //Background Image
            ImageIcon background = new ImageIcon(ImageIO.read(ClassLoader.getSystemResourceAsStream("textures/menu/main-menu/background.png")).getScaledInstance(Game.gameWindow().screenWidth,Game.gameWindow().ScreenHeight,Image.SCALE_SMOOTH));
            JLabel backgroundImage = new JLabel(background);
            backgroundImage.setBounds(0,0,getWidth(),getHeight());
            add(backgroundImage);

            loadAllLevels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zum Laden aller Levels
     */
    public void loadAllLevels() {
        mapcounter = 0;
        File mapDir = FileUtil.getFileByResource("/maps/");
        File[] maps = mapDir.listFiles();

        //Wird für das testen der Notwendigen Dateien gebracht
        //Wird hier deklariert, damit es nicht mehrmals unnötig deklariert werden muss!
        boolean foundCollisionFile = false;
        boolean foundMapFile = false;
        boolean foundDetailFile = false;

        for (File currentMap : maps) {
            mapcounter++;
            System.out.println("foundMap: "+currentMap.getName());
            foundCollisionFile = foundMapFile = foundDetailFile = false;
            if (!currentMap.exists() ||!currentMap.isDirectory())
                continue;
            File[] mapSpecificFiles = currentMap.listFiles();
            for (File mapspecificFile : mapSpecificFiles) {
                if (!mapspecificFile.getName().contains(currentMap.getName())) {
                    System.out.println("This File doesn't belong in the Level dir: ");
                    System.out.println("File Path: "+mapspecificFile.getAbsolutePath()+" Expected Name: "+currentMap.getName());
                }

                //Hier testen wir ob alle notwendigen Dateien vorhanden sind: *.map, *.detail, *.collision
                if (mapspecificFile.getName().contains(".collision")) {
                    foundCollisionFile = true;
                }
                if (mapspecificFile.getName().contains(".map")) {
                    foundMapFile = true;
                }
                if (mapspecificFile.getName().contains(".detail")) {
                    foundDetailFile = true;
                }

            }

            if (!foundCollisionFile || !foundMapFile || !foundDetailFile) {
                System.out.println("Dieses Level konnte nicht geladen werden: "+ currentMap.getName());
                System.out.println("Fehlt "+currentMap.getName()+".collision: "+ (foundCollisionFile));
                System.out.println("Fehlt "+currentMap.getName()+".map: "+ (foundMapFile));
                System.out.println("Fehlt "+currentMap.getName()+".detail: "+ (foundDetailFile));
                System.out.println("Dieses Level wird ignoriert!");
                continue;
            }
            addLevelToScreen(currentMap.getName());

        }
    }
    private void addLevelToScreen(String levelName) {
        JButton levelButton = new JButton(levelName);
        levelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.loadLevel("/maps/"+levelName);
            }
        });

        // Button-Dimensionen festlegen
        int buttonWidth = 200;
        int buttonHeight = 80;
        int buttonsPerRow = 3; // Anzahl der Buttons pro Zeile
        int horizontalGap = 50; // Abstand zwischen den Buttons horizontal
        int verticalGap = 40; // Abstand zwischen den Button-Reihen
        int startX = (getWidth() - (buttonWidth * buttonsPerRow + horizontalGap * (buttonsPerRow - 1))) / 2;
        int startY = 150; // Startposition Y (unter dem Titel)

        // Berechne die Position basierend auf dem Button-Zähler
        int row = (mapcounter - 1) / buttonsPerRow;
        int col = (mapcounter - 1) % buttonsPerRow;

        int x = startX + col * (buttonWidth + horizontalGap);
        int y = startY + row * (buttonHeight + verticalGap);

        levelButton.setBounds(x, y, buttonWidth, buttonHeight);
        levelButton.setOpaque(true);
        levelButton.setBorderPainted(false);
        levelButton.setBackground(new Color(70, 130, 180));
        levelButton.setForeground(Color.WHITE);
        levelButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(levelButton);
    }

    @Override
    public void showMenu() {
        super.showMenu();
        setLocation(Menu.getWindowLocation());
    }
}
