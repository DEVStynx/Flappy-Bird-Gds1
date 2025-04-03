package de.school.game.gui.menu;

import de.school.game.Game;
import de.school.game.util.FileUtil;
import de.school.game.util.rendering.RenderUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends Menu {

    public MainMenu() {
        super("Start-Menu");
    }

    @Override
    public void initWidgets() {

        ImageIcon title_Image = new ImageIcon(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/menu/main-menu/Flappy-Bird-Label.png")));


        JLabel title_image_label = new JLabel(title_Image);
        title_image_label.setLocation(100,100);
        this.add(title_image_label);
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Game started");
                // Hier laden wir das Level, wenn der Button gedrückt wird
                deleteMenu();
                Game.loadLevel("/maps/map0");
            }
        });
        startButton.setBounds(50,100,startButton.getWidth(), startButton.getHeight());
        this.add(startButton);
        revalidate();
    }
}
