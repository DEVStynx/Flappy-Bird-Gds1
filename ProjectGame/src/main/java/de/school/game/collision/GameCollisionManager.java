package de.school.game.collision;

import de.school.game.Game;
import de.school.game.GameController;
import de.school.game.entity.RenderableObject;
import de.school.game.entity.TileObject;
import java.util.HashSet;
import java.util.Set;

/**
 * The main Game Collision-manager
 */
public class GameCollisionManager {
    public Set<TileObject> nearbyTiles;

    //Initialize Set
    public GameCollisionManager() {
        nearbyTiles = new HashSet<>();
    }

    /**
     * Calculates nearby tiles to prevent for checking every single Tile for collision
     * @return a {@link Set Set} with {@link TileObject Tileobjects}
     */
    public Set<TileObject> get_nearbyTiles() {
        nearbyTiles.clear();
        int[] playerTilePosition = getPlayerTilePosition();
        Set<TileObject> nearbyTiles = new HashSet<>();
        for(int col = playerTilePosition[0]; col < playerTilePosition[0] + 3; col++) {
            for(int row = playerTilePosition[1]; row < playerTilePosition[1] + 3; row++) {
                if (isTileOutofRange(new int[]{col, row})) {
                    continue;
                }

                TileObject tile = new TileObject(col * Game.gameWindow().tileSize, row * Game.gameWindow().tileSize, Game.worldTileManager().collisionTileManager.worldCollision[col][row]);
                nearbyTiles.add(tile);
            }
        }
        return nearbyTiles;
    }

    /**
     * Check if the player can Move to the offsetted position
     * @param offsetx offset in the (X-Axis)
     * @param offsety offset in the (Y-Axis)
     * @return  {@link Boolean true} if the player can Move
     */
    public boolean canPlayerMove(int offsetx, int offsety) {
        int[] playerTilePosition = getPlayerTilePosition(offsetx,offsety);
        Set<TileObject> nearbyTiles = get_nearbyTiles();
        if (!Game.gameController().getGamestate().equals(GameController.Gamestate.RUNNING))
            return false;
        for(TileObject tile : nearbyTiles) {
            if (tile.getHitbox().intersects(new TileObject(Game.player().x + offsetx, Game.player().y + offsety,0,Game.player().getHitbox().width,Game.player().getHitbox().height).getHitbox())) {
                switch (tile.collisionId) {
                    //Collision detected
                    case 1:
                        return false;
                    //Game Lost
                    case 2:
                        Game.gameController().loseGame();
                        break;
                    //Game Won
                    case 3:
                        Game.gameController().winGame();
                        break;

                }

            }


        }
        return true;
    }

    /**
     * Calculate the Players Position and return it as Rows and Columns
     * @return a int Array simulating a Point/Location
     */
    public int[] getPlayerTilePosition() {
        return new int[]{Game.player().x / Game.gameWindow().tileSize, Game.player().y / Game.gameWindow().tileSize};
    }

    /**
     * Calculate the players position with offset and return it as Rows and Columns
     * @param offsetx offset in the X-Axis
     * @param offsety offset in the Y-Axis
     * @return a int Array simulating a Point/Location
     */
    public int[] getPlayerTilePosition(int offsetx, int offsety) {
        return new int[]{Game.player().x +offsetx/ Game.gameWindow().tileSize, Game.player().y +offsety / Game.gameWindow().tileSize};
    }

    /**
     * Test if a Location is outside of the Level/Window
     * @param tileposition The Location as a int Array
     * @return {@link Boolean true} if the Tile is outside of the Window
     */
    public boolean isTileOutofRange(int[]tileposition) {
        return tileposition[0] > Game.gameWindow().maxScreenCol -1 || tileposition[0] < 0 || tileposition[1] > Game.gameWindow().maxScreenRows -1 || tileposition[1] < 0;
    }

}
