package de.school.game.gui.world;

import de.school.game.Game;
import de.school.game.util.FileUtil;
import de.school.game.util.rendering.RenderUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class WorldTileManager {
    public HashMap<Integer, Tile> tileLibrary;
    int[][] loadedMap;
    public int currentKey = 0;

    public WorldTileManager() {
        tileLibrary = new HashMap<>();
        loadedMap = new int[Game.gameWindow().maxScreenCol][Game.gameWindow().maxScreenRows];
        addTileToLibrary(new Tile(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/tiles/grass01.png"))));
        addTileToLibrary(new Tile(RenderUtil.loadTexture(FileUtil.getFileByResource("textures/map/tiles/floor01.png"))));
    }
    private void addTileToLibrary(Tile tile) {
        tileLibrary.put(currentKey,tile);
        currentKey++;
    }
    public void loadMap(String resource) {
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
                        loadedMap[col][row] = Integer.parseInt(tileIds[col]);
                    } else {
                        loadedMap[col][row] = 0; // Standardwert setzen
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


    public void render(Graphics2D graphics2D) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col <Game.gameWindow().maxScreenCol && row < Game.gameWindow().maxScreenRows) {
            graphics2D.drawImage(tileLibrary.get(loadedMap[col][row]).texture, x, y, Game.gameWindow().tileSize, Game.gameWindow().tileSize, null);
            col++;
            x+= Game.gameWindow().tileSize;
            if (col == Game.gameWindow().maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += Game.gameWindow().tileSize;
            }
        }
    }

}
