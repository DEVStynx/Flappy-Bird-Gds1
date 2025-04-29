package de.school.game.gui.world;

import de.school.game.Game;
import de.school.game.gui.Animation;
import de.school.game.util.FileUtil;
import de.school.game.util.rendering.RenderUtil;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The way to load levels/maps by a directory
 */
public class WorldTileManager {
    public HashMap<Integer, TileTexture> tileLibrary;
    public HashMap<Integer, TileTexture> detailLibrary;
    public CollisionTileManager collisionTileManager;

    public int currentTileKey = 0;
    public int currentDetailKey = 0;
    public int[][] loadedMap;

    public int[][] loadedDetails;

    /**
     * The Constructor to add all the textures
     * and create the lists/maps
     */
    public WorldTileManager() {


        tileLibrary = new HashMap<>();
        detailLibrary = new HashMap<>();
        loadedMap = new int[Game.gameWindow().maxScreenCol][Game.gameWindow().maxScreenRows];
        loadedDetails = new int[Game.gameWindow().maxScreenCol][Game.gameWindow().maxScreenRows];
        collisionTileManager = new CollisionTileManager();
        //Tile Registry

        //Grass
        addTileToLibrary(new TileTexture(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/tiles/air.png")))); //0
        addTileToLibrary(new TileTexture(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/tiles/grass_up.png")))); //1
        addTileToLibrary(new TileTexture(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/tiles/grass_up_left.png")))); //2
        addTileToLibrary(new TileTexture(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/tiles/grass_up_right.png")))); //3

        //Stone
        addTileToLibrary(new TileTexture(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/tiles/stone_up.png")))); //4
        addTileToLibrary(new TileTexture(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/tiles/stone_up_left.png")))); //5
        addTileToLibrary(new TileTexture(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/tiles/stone_up_right.png")))); //6


        addDetailToLibrary(new TileTexture(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/details/air.png")))); //0
        addDetailToLibrary(new TileTexture(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/details/spike_trap.png")))); //1
        //Aimated Tiles/Details
        //2 For the Goal Star
        AnimatedTileTexture animatedTileTexture = new AnimatedTileTexture("textures/map/details/goal_star/",30);
        addDetailToLibrary(animatedTileTexture);

    }

    private void addTileToLibrary(TileTexture tileTexture) {
        tileLibrary.put(currentTileKey, tileTexture);
        currentTileKey++;
    }

    private void addDetailToLibrary(TileTexture tileTexture) {
        detailLibrary.put(currentDetailKey, tileTexture);
        currentDetailKey++;
    }

    public void loadmap(String resource) {
        loadfromFile(resource+".map",loadedMap);
        loadfromFile(resource+".detail",loadedDetails);
        collisionTileManager.loadMapCollisions(resource + ".collision");

    }
    public void loadMapByDir(String resource) {
        File file = new File(resource);
        if (file.isDirectory()) {
            loadmap(resource);
        }
        String name = file.getName();
        loadmap(resource + File.separator + name);


    }
    private void loadfromFile(String resource, int[][] array) {
        try {
            InputStream stream = WorldTileManager.class.getResourceAsStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            int row = 0;
            while (row < Game.gameWindow().maxScreenRows) {
                String currentLine = reader.readLine();
                if (currentLine == null) break;  // Falls weniger Zeilen vorhanden sind

                String[] tileIds = currentLine.split(" ");
                for (int col = 0; col < Game.gameWindow().maxScreenCol; col++) {
                    if (col < tileIds.length) { // Falls eine Zeile zu kurz ist
                        array[col][row] = Integer.parseInt(tileIds[col]);
                    } else {
                        array[col][row] = 0; // Standardwert setzen
                    }
                }
                row++;
            }
            reader.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * The Method to render the whole map/level
     */
    public void render(Graphics2D graphics2D) {
        render(graphics2D, loadedMap, tileLibrary);
        render(graphics2D, loadedDetails, detailLibrary);
    }

    /**
     * The Method to render a map array
     * @param graphics2D The needed instance to render to the screen
     * @param map the loaded map array
     * @param tiles a {@link java.util.Map Map} to get the right Image of the texture index
     */
    private void render(Graphics2D graphics2D, int[][] map, HashMap<Integer, TileTexture> tiles) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < Game.gameWindow().maxScreenCol && row < Game.gameWindow().maxScreenRows) {
            graphics2D.drawImage(tiles.get(map[col][row]).getTexture(), x, y, Game.gameWindow().tileSize, Game.gameWindow().tileSize, null);
            col++;
            x += Game.gameWindow().tileSize;
            if (col == Game.gameWindow().maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += Game.gameWindow().tileSize;
            }
        }
    }

    /**
     * Loads the collisions of the level/map
     */
    public class CollisionTileManager {
        public int[][] worldCollision;
        public int[] levelGoal;
        public int[] startposition;

        public CollisionTileManager() {
            worldCollision = new int[Game.gameWindow().maxScreenCol][Game.gameWindow().maxScreenRows];
            levelGoal = new int[2];
            //Startposition initialisieren
            startposition = new int[2];
            //0 ist keine Kollision
            //1 ist die Kollision mit einer Wand
            //2 ist das "sterben"/"neu beginnen" eines Levels
            //3 ist das Ziel eines Levels
            //4 ist die Startposition


        }

        /**
         * Loads the map collisions from a file
         * @param resource The String file resource
         */
        public void loadMapCollisions(String resource) {
            try {
                InputStream stream = WorldTileManager.class.getResourceAsStream(resource);
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                int row = 0;
                while (row < Game.gameWindow().maxScreenRows) {
                    String currentLine = reader.readLine();
                    if (currentLine == null) break;

                    String[] tileIds = currentLine.split(" ");
                    for (int col = 0; col < Game.gameWindow().maxScreenCol; col++) {
                        if (col < tileIds.length) {
                            int coll = Integer.parseInt(tileIds[col]);
                            if (coll > 4) {
                                throw new IllegalArgumentException("diese Zahl/Kollision existiert nicht!");
                            }
                            worldCollision[col][row] = coll;
                            if (coll == 3) {
                                levelGoal[0] = col;
                                levelGoal[1] = row;
                            }
                            else if(coll == 4) {
                                startposition[0] = col;
                                startposition[1] = row;
                            }
                        } else {
                            worldCollision[col][row] = 0;
                        }
                    }
                    row++;
                }
                reader.close();
                stream.close();
                Game.player().x = startposition[0] * Game.gameWindow().tileSize;
                Game.player().y = startposition[1] * Game.gameWindow().tileSize;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public int[] getLevelGoal (){
            return levelGoal;
        }

    }
}
