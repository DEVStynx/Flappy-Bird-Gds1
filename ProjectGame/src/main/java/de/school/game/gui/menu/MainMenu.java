package de.school.game.gui.menu;

import de.school.game.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainMenu extends Menu {
    BufferedImage background;
    public MainMenu() {
        super("Start-Menu");

    }

    @Override
    public void initWidgets() {


        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Game started");
                //laden des Levels
                deleteMenu();
                Game.loadLevel("/maps/map0");
            }
        });
        startButton.setBounds(100,200,startButton.getWidth(), startButton.getHeight());
        this.add(startButton);
        repaint();
    }


}