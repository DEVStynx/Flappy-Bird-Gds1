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
        initWidgets();
        this.setVisible(true);
    }
    public void deleteMenu() {
        setVisible(false);
    }
}
