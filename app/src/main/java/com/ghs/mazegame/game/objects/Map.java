package com.ghs.mazegame.game.objects;

import android.content.res.Resources;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.engine.utils.Hitbox;
import com.ghs.mazegame.game.Renderer;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;

public class Map {

    private final float SCALE = Renderer.SCALE;

    private int width, height, rWidth, rHeight;

    private Camera camera;

    private com.ghs.mazegame.game.objects.Tile[][] map;

    public Map(Camera camera, float x, float y, int width, int height) {
        this.camera = camera;
        map = new com.ghs.mazegame.game.objects.Tile[width][height];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if(i == 0 || j== 0 || i == map.length - 1 || j == map[0].length - 1)
                    map[i][j] = new com.ghs.mazegame.game.objects.Tile(camera, new Texture(R.drawable.brick_wall), new Shader(R.raw.defaultvs, R.raw.defaultfs), i * SCALE, j  * SCALE, SCALE, SCALE, true);
                else
                    map[i][j] = new com.ghs.mazegame.game.objects.Tile(camera, new Texture(R.drawable.stone_floor), new Shader(R.raw.defaultvs, R.raw.defaultfs), i * SCALE, j  * SCALE, SCALE, SCALE, false);
            }
        }
        this.width = width;
        this.height = height;
        rWidth = (int) Math.ceil((camera.getX() + camera.getWidth()) / SCALE) + 1;
        rHeight = (int) Math.ceil((camera.getY() + camera.getHeight()) / SCALE) + 1;
    }

    public void render() {
        int minX = (int) (camera.getX() / SCALE);
        int maxX = minX + rWidth;
        int minY = (int) (camera.getY() / SCALE);
        int maxY = minY + rHeight;
        for (int i = minX; i < maxX; ++i)
            for (int j = minY; j < maxY; ++j)
                map[i][j].render();
    }

    //Checks player hitbox against the nearest block and adjusts position accordingly
    //If the collision is successful it checks again so that you dont get pushed into another block
    public void checkPlayerCollision(Player player) {
        boolean check = false;
        Hitbox playerHitbox = player.getHitbox();
        //Determines which tiles the player is over
        int minX = (int) (player.getX() / SCALE);
        int maxX = (int) ((player.getX() + player.getWidth()) / SCALE);
        int minY = (int) (player.getY() / SCALE);
        int maxY = (int) ((player.getY() + player.getHeight()) / SCALE);
        Hitbox closest = null;
        Vector3f length1 = null;
        //Determines the closest tile of the ones the player is on
        for (int i = Math.max(minX, 0); i <= maxX && i < map.length; i++) {
            for (int j = Math.max(minY, 0); j <= maxY && j < map[0].length; j++) {
                if(map[i][j].isSolid()) {
                    if(closest == null) {
                        closest = map[i][j].getHitbox();
                        length1 = closest.getCenter().sub(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0, new Vector3f());
                    }
                    else {
                        Vector3f length2 = map[i][j].getHitbox().getCenter().sub(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0, new Vector3f());
                        if(length1.lengthSquared() > length2.lengthSquared()) {
                            closest = map[i][j].getHitbox();
                            length1 = length2;
                        }
                    }
                }
            }
        }
        //Adjusts the player position if necessary and runs another check if necessary
        if(closest != null) {
            check = playerHitbox.checkCollision(closest);
            if(check) {
                player.translate(playerHitbox.collisionAdjust(closest));
                checkPlayerCollision(player);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getRightBound() {
        return width * SCALE;
    }

    public float getBottomBound() {
        return height * SCALE;
    }
}
