package de.school.game.gui.menu;

import de.school.game.Game;

import javax.swing.*;
import java.awt.*;

public abstract class Menu extends JPanel {
    public String name;
    public Menu(String name) {
        name = name;
    }


    public abstract void initWidgets();
    private void setValues() {
        setLayout(new GridLayout(Game.gameWindow().maxScreenRows,Game.gameWindow().maxScreenCol));
    }
    public void addMenu() {
        initWidgets();
        Game.gameWindow().add(this);
        Game.gameWindow().revalidate();
        Game.gameWindow().repaint();
    }

    public void showMenu() {
        addMenu();

        this.setVisible(true);
    }
    public void deleteMenu() {
        Game.gameWindow().remove(this);
    }
}
