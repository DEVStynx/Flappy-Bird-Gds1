package de.school.game;

import de.school.game.audio.AudioController;
import de.school.game.clock.GameClock;
import de.school.game.collision.GameCollisionManager;
import de.school.game.entity.player.PlayerEntity;
import de.school.game.gui.GameWindow;
import de.school.game.gui.menu.MainMenu;
import de.school.game.gui.world.WorldTileManager;
import de.school.game.input.InputListener;
import de.school.game.util.FileUtil;
import de.school.game.util.rendering.RenderUtil;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;


public class Game extends JFrame {
    private static GameWindow gameWindow;
    private static GameClock gameClock;
    private static PlayerEntity player;
    private static InputListener inputListener;
    private static WorldTileManager worldTileManager;
    private static GameCollisionManager gameCollisionManager;
    private static GameController gameController;
    private static MainMenu mainMenu;

    private static Game instance;

    private static AudioController audioController;
    public static int FPS;

    private static BufferedImage icon;

    public Game(int FPS) {
        instance = this;
        mainMenu = new MainMenu();
        this.setVisible(false);

        //                                  Setzt den Modus auf Debugging/Normal        Debugging = true
        gameController = new GameController(true); // Stelle sicher, dass der GameController initialisiert ist
        Game.FPS = FPS;
        startGame(Game.FPS);

    }

    public void startGame(int FPS) {
        this.setTitle("Game");

        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setFocusable(true);
        gameWindow = new GameWindow();

        this.add(gameWindow);


        this.pack();
        inputListener = new InputListener();
        this.addKeyListener(inputListener);
        


        icon = RenderUtil.loadTexture(FileUtil.getFileByResource("textures/player/player_anim_mid.png"));
        this.setIconImage(icon);
        //Wenn das Menü auftaucht soll das SpielFrame nicht sichtbar sein
        MainMenu.windowLocation = getLocation();
        setVisible(false);

        // GameController auf Menü setzen und Menü anzeigen
        gameController.setGamestate(GameController.Gamestate.MENU);
        mainMenu.showMenu(); // Menü wird jetzt angezeigt
        mainMenu.setLocation(MainMenu.windowLocation);
        audioController = new AudioController();
        try {
            audioController.loadByDir();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        pack();
    }

    public static void loadLevel(String level) {
        MainMenu.windowLocation = mainMenu().getLocation();
        instance.setVisible(true);
        instance.setLocation(MainMenu.windowLocation);
        audioController().playSound("background.wav",true);
        // Stelle sicher, dass das Level nur geladen wird, wenn das Menü nicht mehr aktiv ist
        Game.gameWindow.repaint();
        gameController.setGamestate(GameController.Gamestate.STARTING);

        player = new PlayerEntity(gameWindow.maxScreenCol / 2 * gameWindow.tileSize, gameWindow.maxScreenRows / 2 * gameWindow.tileSize, 1);
        worldTileManager = new WorldTileManager();
        worldTileManager.loadMapByDir(level);
        gameCollisionManager = new GameCollisionManager();
        if (gameClock != null)
            gameClock.killGameThread();

        gameClock = new GameClock(Game.FPS); // Starte den GameClock, um das Level zu aktualisieren
        gameClock.startGameThread();

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
    public static void showGameWindow(boolean showWindow) {
        instance.setVisible(showWindow);
    }

    public static WorldTileManager worldTileManager() {
        return worldTileManager;
    }

    public static GameCollisionManager gameCollisionManager() {
        return gameCollisionManager;
    }

    public static GameController gameController() {
        return gameController;
    }
    public static MainMenu mainMenu() {
        return mainMenu;
    }
    public static AudioController audioController() {return audioController;}


}
