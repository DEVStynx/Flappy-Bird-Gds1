package de.school.game.gui.menu;

import de.school.game.Game;
import de.school.game.util.FileUtil;
import de.school.game.util.rendering.RenderUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainMenu extends Menu {
    BufferedImage background;
    public MainMenu() {
        super("Start-Menu");
    }

    @Override
    public void initWidgets() {

        setMinimumSize(new Dimension(Game.gameWindow().screenWidth,Game.gameWindow().ScreenHeight));
        setLayout(null);
        setResizable(false);



        try {
            //Title Image
            ImageIcon title = new ImageIcon(ImageIO.read(ClassLoader.getSystemResourceAsStream("textures/menu/main-menu/Flappy-Bird-Label.png")).getScaledInstance(getWidth() / 2, getHeight() / 4 , Image.SCALE_SMOOTH));
            JLabel titleLabel = new JLabel(title);
            titleLabel.setBounds(getWidth() /2 - getWidth() / 4, getHeight() / 8,title.getIconWidth(),title.getIconHeight());
            titleLabel.setOpaque(true);
            //                          Macht den Hintergrund transparent
            titleLabel.setBackground(new Color(0,0,0, 0));
            titleLabel.setBorder(null);
            add(titleLabel);
            //Background Image
            ImageIcon background = new ImageIcon(ImageIO.read(ClassLoader.getSystemResourceAsStream("textures/menu/main-menu/background.png")).getScaledInstance(Game.gameWindow().screenWidth,Game.gameWindow().ScreenHeight,Image.SCALE_SMOOTH));
            JLabel backgroundImage = new JLabel(background);
            backgroundImage.setBounds(0,0,getWidth(),getHeight());
            add(backgroundImage);




            ImageIcon startButtonIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResourceAsStream("textures/menu/main-menu/start_button.png")).getScaledInstance(getWidth() / 2, 75,Image.SCALE_SMOOTH));
            JButton startButton = new JButton("Start Game",startButtonIcon);
            //Hintergrund Transparent
            startButton.setBackground(new Color(0,0,0,0));
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Game started");
                    //laden des Levels
                    deleteMenu();
                    Game.loadLevel("/maps/map0");
                }
            });
            startButton.setOpaque(true);
            startButton.setBorderPainted(false);

            startButton.setBounds(getWidth() /2 - getWidth() / 4,getHeight()  - getHeight() / 4,getWidth() / 2, 75);
            add(startButton);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showMenu() {
        super.showMenu();
        setLocation(windowLocation);
    }
}