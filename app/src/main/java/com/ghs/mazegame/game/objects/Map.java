package com.ghs.mazegame.game.objects;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.engine.utils.Hitbox;
import com.ghs.mazegame.game.Renderer;
import com.ghs.mazegame.engine.display.Camera;

public class Map {

    public static final int
        TYPE_WALL = 0,
        TYPE_FLOOR = 1,
        TYPE_SPAWN = 2,
        TYPE_END = 3;

    private final float SCALE = Renderer.SCALE;

    private int width, height, rWidth, rHeight;

    private Camera camera;

    private Tile[][] map;

    public Map(Camera camera, int width, int height) {
        this.camera = camera;
        map = new Tile[width][height];
        this.width = width;
        this.height = height;
        rWidth = (int) Math.ceil((camera.getX() + camera.getWidth()) / SCALE) + 1;
        rHeight = (int) Math.ceil((camera.getY() + camera.getHeight()) / SCALE);
        for (int i = 0; i < map.length; ++i)
            for (int j = 0; j < map[0].length; ++j)
                map[i][j] = new Tile(camera, i * SCALE, j * SCALE, SCALE, SCALE);
    }

    public void render() {
        int minX = (int) (camera.getX() / SCALE);
        int maxX = minX + rWidth;
        int minY = (int) (camera.getY() / SCALE);
        int maxY = minY + rHeight;
        for (int i = Math.max(minX, 0); i <= maxX && i < map.length; i++)
            for (int j = Math.max(minY, 0); j <= maxY && j < map[0].length; j++)
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

    public void setTile(int type, int x, int y) {
        switch(type) {
            case TYPE_FLOOR:
                map[x][y] = new Tile(camera, new Texture(R.drawable.stone_floor), new Shader(R.raw.defaultvs, R.raw.defaultfs), x * SCALE, y * SCALE, false);
                break;
            case TYPE_WALL:
                map[x][y] = new Tile(camera, new Texture(R.drawable.brick_wall), new Shader(R.raw.defaultvs, R.raw.defaultfs), x * SCALE, y * SCALE, true);
                break;
            case TYPE_SPAWN:
                map[x][y] = new Tile(camera, new Texture(R.drawable.stone_floor), new Shader(R.raw.defaultvs, R.raw.defaultfs), x * SCALE, y * SCALE, false);
                break;
            case TYPE_END:
                map[x][y] = new Tile(camera, new Texture(R.drawable.stone_floor), new Shader(R.raw.defaultvs, R.raw.defaultfs), x * SCALE, y * SCALE, false);
                break;
        }
    }
}
