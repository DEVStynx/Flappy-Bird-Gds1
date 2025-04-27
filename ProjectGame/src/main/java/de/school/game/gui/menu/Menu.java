package de.school.game.gui.menu;

import de.school.game.Game;

import javax.swing.*;
import java.awt.*;

/**
 * the abstract Menu Class to easily create and switch between mennus/Windows
 */
public abstract class Menu extends JFrame {
    public static Point windowLocation;
    public String name;
    private boolean initialized = false; // Flag zur Überprüfung der Initialisierung

    public Menu(String name) {
        this.name = name;
        setTitle(this.name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * An abstract method to initialize and locate Widgets/Components
     */
    public abstract void initWidgets();

    /**
     * The Method to show the Menu to the player
     */
    public void showMenu() {
        Game.showGameWindow(false);
        setLocation(windowLocation);
        // Nur initialisieren, wenn es noch nicht gemacht wurde
        if (!initialized) {
            initWidgets();
            initialized = true;
        }

        this.setVisible(true);
    }

    public void deleteMenu() {
        setVisible(false);
    }

    /**
     * Methode zum manuellen Neuinitialisieren der Widgets bei Bedarf
     */
    public void reinitWidgets() {
        initialized = false;
        getContentPane().removeAll();
        initWidgets();
        initialized = true;
        revalidate();
        repaint();
    }
}