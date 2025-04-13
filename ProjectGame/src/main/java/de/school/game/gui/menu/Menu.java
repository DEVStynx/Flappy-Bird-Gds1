package de.school.game.gui.menu;

import de.school.game.Game;

import javax.swing.*;
import java.awt.*;

public abstract class Menu extends JFrame {
    public static Point windowLocation;
    public String name;
    public Menu(String name) {
        this.name = name;
        setTitle(this.name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public abstract void initWidgets();

    public void showMenu() {
        Game.showGameWindow(false);
        initWidgets();
        this.setVisible(true);
    }
    public void deleteMenu() {
        setVisible(false);
    }
}
