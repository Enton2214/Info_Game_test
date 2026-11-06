import java.awt.*;
import java.util.ArrayList;

public class CollisionChecker {

    private final Main main;
    private final TileManager tileManager;

    public CollisionChecker(Main main) {
        this.main = main;
        this.tileManager = Main.tileManager;
    }

    public boolean checkPlayerCollision(double targetWorldX, double targetWorldY) {
        if (checkCollisionForPlayer(targetWorldX, targetWorldY, tileManager.mapBG)) return true;

        if (checkCollisionForPlayer(targetWorldX, targetWorldY, tileManager.mapMG)) return true;

        if (checkEntityCollision(targetWorldX, targetWorldY)) return true;

        return false;
    }

    private boolean checkEntityCollision(double targetWorldX, double targetWorldY) {
        Rectangle playerFutureHitBox = new Rectangle(
                (int) (targetWorldX + main.playerHitBox.x),
                (int) (targetWorldY + main.playerHitBox.y),
                main.playerHitBox.width,
                main.playerHitBox.height
        );

        for (Entity npc : main.npcList) {
            if (npc != null && npc.hitBox != null) {

                Rectangle npcHitBox = new Rectangle(
                        (int) (npc.worldX + npc.hitBox.x),
                        (int) (npc.worldY + npc.hitBox.y),
                        npc.hitBox.width,
                        npc.hitBox.height
                );

                if (playerFutureHitBox.intersects(npcHitBox)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkCollisionForPlayer(double targetWorldX, double targetWorldY, ArrayList<ArrayList<Integer>> mapPath) {
        int leftWorldX   = (int) Math.floor(targetWorldX + main.playerHitBox.x);
        int rightWorldX  = (int) Math.floor(targetWorldX + main.playerHitBox.x + main.playerHitBox.width);
        int topWorldY    = (int) Math.floor(targetWorldY + main.playerHitBox.y);
        int bottomWorldY = (int) Math.floor(targetWorldY + main.playerHitBox.y + main.playerHitBox.height);

        int tileSize = main.originalTileSize; // 24

        int startCol = leftWorldX / tileSize;
        int endCol   = rightWorldX / tileSize;
        int startRow = topWorldY / tileSize;
        int endRow   = bottomWorldY / tileSize;

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {

                if (row < 0 || col < 0 || row >= mapPath.size() || col >= mapPath.get(0).size()) {
                    return true;
                }

                int tileId = mapPath.get(row).get(col);
                if (tileId <= 0) continue;

                Tile t = tileManager.tile[tileId];
                if (t == null || !t.collision) continue;

                if (t.collisionMap != null) {
                    int tileWorldX = col * tileSize;
                    int tileWorldY = row * tileSize;

                    int checkStartX = Math.max(leftWorldX, tileWorldX);
                    int checkEndX   = Math.min(rightWorldX, tileWorldX + tileSize - 1);
                    int checkStartY = Math.max(topWorldY, tileWorldY);
                    int checkEndY   = Math.min(bottomWorldY, tileWorldY + tileSize - 1);

                    for (int py = checkStartY; py <= checkEndY; py++) {
                        for (int px = checkStartX; px <= checkEndX; px++) {

                            int localX = px - tileWorldX;
                            int localY = py - tileWorldY;

                            if (localY >= 0 && localY < 24 && localX >= 0 && localX < 24) {
                                if (t.collisionMap[localY][localX]) {
                                    return true;
                                }
                            }
                        }
                    }
                } else {
                    return true;
                }
            }
        }

        return false;
    }
}