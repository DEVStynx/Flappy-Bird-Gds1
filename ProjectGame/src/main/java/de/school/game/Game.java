package de.school.game;

import de.school.game.clock.GameClock;
import de.school.game.collision.GameCollisionManager;
import de.school.game.entity.player.PlayerEntity;
import de.school.game.gui.GameWindow;
import de.school.game.gui.world.WorldTileManager;
import de.school.game.input.InputListener;

import javax.swing.*;

public class Game extends JFrame {
    private static GameWindow gameWindow;
    private static GameClock gameClock;
    private static PlayerEntity player;
    private static InputListener inputListener;
    private static WorldTileManager worldTileManager;
    private static GameCollisionManager GameCollisionManager;

    public Game(int FPS) {
        this.setTitle("Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setFocusable(true);
        gameWindow = new GameWindow();
        this.add(gameWindow);
        this.setResizable(true);
        this.pack();
        inputListener = new InputListener();
        this.addKeyListener(inputListener);
        this.addMouseListener(inputListener);
        player = new PlayerEntity(0, 0, 1);
        worldTileManager = new WorldTileManager();
        worldTileManager().loadmap("/maps/map0");
        GameCollisionManager = new GameCollisionManager();

        gameClock = new GameClock(FPS);
        gameClock.startGameThread();
        this.setVisible(true);
    }

    public static GameWindow gameWindow() {
        return gameWindow;
    }

    public static GameClock gameClock() {
        return gameClock;
    }

    public static PlayerEntity player() {
        return player;
    }

    public static WorldTileManager worldTileManager() {
        return worldTileManager;
    }

    public static GameCollisionManager gameCollisionManager() {
        return GameCollisionManager;
    }

}
