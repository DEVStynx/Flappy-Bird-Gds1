package de.school.game.collision;

import de.school.game.Game;
import de.school.game.entity.RenderableObject;
import de.school.game.entity.TileObject;

import java.util.HashSet;
import java.util.Set;

public class GameCollisionManager {
    public Set<TileObject> nearbyTiles;

    public GameCollisionManager() {
        nearbyTiles = new HashSet<>();
    }

    public boolean doess_entity_collide_with_entity(RenderableObject entity1, RenderableObject entity2) {
        return entity1.getHitbox().intersects(entity2.getHitbox());
    }

    public Set<TileObject> get_nearbyTiles() {
        nearbyTiles.clear();
        int[] playerTilePosition = getPlayerTilePosition();
        Set<TileObject> nearbyTiles = new HashSet<>();
        for(int col = playerTilePosition[0]; col < playerTilePosition[0] + 3; col++) {
            for(int row = playerTilePosition[1]; row < playerTilePosition[1] + 3; row++) {
                if (isTileOutofRange(new int[]{col, row})) {
                    continue;
                }
                System.out.println("Tile: " + col + " " + row);
                TileObject tile = new TileObject(col * Game.gameWindow().tileSize, row * Game.gameWindow().tileSize, Game.worldTileManager().collisionTileManager.worldCollision[col][row]);
                nearbyTiles.add(tile);
            }
        }
        return nearbyTiles;
    }
    public boolean canPlayerMove(int offsetx, int offsety) {
        int[] playerTilePosition = getPlayerTilePosition(offsetx,offsety);
        Set<TileObject> nearbyTiles = get_nearbyTiles();
        for(TileObject tile : nearbyTiles) {
            if (tile.collisionId == 1) {         //Simulating player with offsetted position //TODO: CLEANUP
                if (tile.getHitbox().intersects(new TileObject(Game.player().x + offsetx, Game.player().y + offsety,0).getHitbox())) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[] getPlayerTilePosition() {
        return new int[]{Game.player().x / Game.gameWindow().tileSize, Game.player().y / Game.gameWindow().tileSize};
    }
    public int[] getPlayerTilePosition(int offsetx, int offsety) {
        return new int[]{Game.player().x +offsetx/ Game.gameWindow().tileSize, Game.player().y +offsety / Game.gameWindow().tileSize};
    }
    public boolean isTileOutofRange(int[]tileposition) {
        return tileposition[0] > Game.gameWindow().maxScreenCol -1 || tileposition[0] < 0 || tileposition[1] > Game.gameWindow().maxScreenRows -1 || tileposition[1] < 0;
    }

}
