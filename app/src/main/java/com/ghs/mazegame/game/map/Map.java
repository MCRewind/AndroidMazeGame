package com.ghs.mazegame.game.map;

import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.engine.utils.Hitbox;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.Renderer;
import com.ghs.mazegame.game.objects.Player;
import com.ghs.mazegame.game.panels.EditPanel;

import java.util.HashMap;

import static com.ghs.mazegame.game.Renderer.SCALE;

public class Map {

    public static final int NUM_TILES = 25;

    public static final int
        TYPE_EMPTY = 0,
        TYPE_BRICK_WALL = 1,
        TYPE_SQUARE_WALL = 2,
        TYPE_S_WALL = 3,
        TYPE_STONE_FLOOR = 4,
        TYPE_LIMESTONE_FLOOR = 5,
        TYPE_WOOD_FLOOR = 6,
        TYPE_START = 7,
        TYPE_END = 8,
        TYPE_BRICK_WALL_RED = 9,
        TYPE_BRICK_WALL_ORANGE = 10,
        TYPE_BRICK_WALL_YELLOW = 11,
        TYPE_BRICK_WALL_GREEN = 12,
        TYPE_BRICK_WALL_CYAN = 13,
        TYPE_BRICK_WALL_BLUE = 14,
        TYPE_BRICK_WALL_PURPLE = 15,
        TYPE_BRICK_WALL_MAGENTA = 16,
        TYPE_SANDSTONE_WALL = 17,
        TYPE_SANDSTONE_FLOOR = 18,
        TYPE_STONE_KEY_WALL = 19,
        TYPE_20 = 20,
        TYPE_21 = 21,
        TYPE_22 = 22,
        TYPE_23 = 23,
        TYPE_24 = 24;

    public static final HashMap<String, Integer> types = new HashMap<>();

    public static final boolean
        STATE_EDIT = false,
        STATE_PLAY = true;

    private int width, height, rWidth, rHeight;
    private float x, y;

    private boolean state;

    private Camera camera;

    private int[][] map;
    private int[][] over;
    private Tile[] tiles;

    public Map(Camera camera, float x, float y, int width, int height) {
        this.camera = camera;
        this.x = x;
        this.y = y;
        map = new int[width][height];
        over = new int[width][height];
        this.width = width;
        this.height = height;
        rWidth = (int) Math.ceil((camera.getX() + camera.getWidth()) / SCALE);
        rHeight = (int) Math.ceil((camera.getY() + camera.getHeight()) / SCALE);
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                map[i][j] = TYPE_EMPTY;
                over[i][j] = TYPE_EMPTY;
            }
        }
        tiles = new Tile[NUM_TILES];
        tiles[TYPE_EMPTY]              = new Tile(camera);
        tiles[TYPE_BRICK_WALL]         = new Tile(camera, new Texture(R.drawable.brick_wall),         true,  0.9f);
        tiles[TYPE_SQUARE_WALL]        = new Tile(camera, new Texture(R.drawable.square_wall),        true,  0.9f);
        tiles[TYPE_S_WALL]             = new Tile(camera, new Texture(R.drawable.s_wall),             true,  0.9f);
        tiles[TYPE_STONE_FLOOR]        = new Tile(camera, new Texture(R.drawable.stone_floor),        false, 0.9f);
        tiles[TYPE_LIMESTONE_FLOOR]    = new Tile(camera, new Texture(R.drawable.limestone_floor),    false, 0.9f);
        tiles[TYPE_WOOD_FLOOR]         = new Tile(camera, new Texture(R.drawable.wood_floor),         false, 0.9f);
        tiles[TYPE_START]              = new Tile(camera, new Texture(R.drawable.start),              false, 0.8f);
        tiles[TYPE_END]                = new Tile(camera, new Texture(R.drawable.end),                false, 0.8f);
        tiles[TYPE_BRICK_WALL_RED]     = new Tile(camera, new Texture(R.drawable.brick_wall_red),     true,  0.9f);
        tiles[TYPE_BRICK_WALL_ORANGE]  = new Tile(camera, new Texture(R.drawable.brick_wall_orange),  true,  0.9f);
        tiles[TYPE_BRICK_WALL_YELLOW]  = new Tile(camera, new Texture(R.drawable.brick_wall_yellow),  true,  0.9f);
        tiles[TYPE_BRICK_WALL_GREEN]   = new Tile(camera, new Texture(R.drawable.brick_wall_green),   true,  0.9f);
        tiles[TYPE_BRICK_WALL_CYAN]    = new Tile(camera, new Texture(R.drawable.brick_wall_cyan),    true,  0.9f);
        tiles[TYPE_BRICK_WALL_BLUE]    = new Tile(camera, new Texture(R.drawable.brick_wall_blue),    true,  0.9f);
        tiles[TYPE_BRICK_WALL_PURPLE]  = new Tile(camera, new Texture(R.drawable.brick_wall_purple),  true,  0.9f);
        tiles[TYPE_BRICK_WALL_MAGENTA] = new Tile(camera, new Texture(R.drawable.brick_wall_magenta), true,  0.9f);
        tiles[TYPE_SANDSTONE_WALL]     = new Tile(camera, new Texture(R.drawable.sandstone_wall),     true,  0.9f);
        tiles[TYPE_SANDSTONE_FLOOR]    = new Tile(camera, new Texture(R.drawable.sandstone_floor),    false, 0.9f);
        tiles[TYPE_STONE_KEY_WALL]     = new Tile(camera, new Texture(R.drawable.stone_key_wall),     true,  0.9f);
        tiles[TYPE_20]                 = new Tile(camera, new Texture(R.drawable.true_tile),          false, 0.9f);
        tiles[TYPE_21]                 = new Tile(camera, new Texture(R.drawable.true_tile),          false, 0.9f);
        tiles[TYPE_22]                 = new Tile(camera, new Texture(R.drawable.true_tile),          false, 0.9f);
        tiles[TYPE_23]                 = new Tile(camera, new Texture(R.drawable.true_tile),          false, 0.9f);
        tiles[TYPE_24]                 = new Tile(camera, new Texture(R.drawable.true_tile),          false, 0.9f);
    }

    public void render() {
        int minX = (int) ((camera.getX() - x) / SCALE);
        int maxX = minX + rWidth;
        int minY = (int) ((camera.getY() - y) / SCALE);
        int maxY = minY + rHeight;
        for (int i = Math.max(minX, 0); i <= maxX && i < map.length; i++) {
            for (int j = Math.max(minY, 0); j <= maxY && j < map[0].length; j++) {
                if (map[i][j] < NUM_TILES) {
                    if (map[i][j] != TYPE_EMPTY || state != STATE_PLAY) {
                        tiles[map[i][j]].setPosition(i * SCALE + x, j * SCALE + y);
                        tiles[map[i][j]].render();
                    }
                    if (over[i][j] != TYPE_EMPTY) {
                        tiles[over[i][j]].setPosition(i * SCALE + x, j * SCALE + y);
                        tiles[over[i][j]].render();
                    }
                }
            }
        }
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
        Hitbox current = null;
        Vector3f length1 = null;
        //Determines the closest tile of the ones the player is on
        for (int i = Math.max(minX, 0); i <= maxX && i < map.length; i++) {
            for (int j = Math.max(minY, 0); j <= maxY && j < map[0].length; j++) {
                if (map[i][j] < NUM_TILES) {
                    if (tiles[map[i][j]].isSolid()) {
                        if (closest == null) {
                            closest = new Hitbox(i * SCALE, j * SCALE, SCALE, SCALE);
                            current = new Hitbox(i * SCALE, j * SCALE, SCALE, SCALE);
                            length1 = closest.getCenter().sub(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0, new Vector3f());
                        } else {
                            current.setPosition(i * SCALE, j * SCALE);
                            Vector3f length2 = current.getCenter().sub(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0, new Vector3f());
                            if (length1.lengthSquared() > length2.lengthSquared()) {
                                closest.setPosition(current.getX(), current.getY());
                                length1 = length2;
                            }
                        }
                    }
                }
            }
        }
        //Adjusts the player position if necessary and runs another check if necessary
        if (closest != null) {
            check = playerHitbox.checkCollision(closest);
            if (check) {
                player.translate(playerHitbox.collisionAdjust(closest));
                checkPlayerCollision(player);
            }
        }
    }


    public int getTile(int x, int y, boolean overlay) {
        if(overlay)
            return over[x][y];
        else
            return map[x][y];
    }


    public void setTile(int type, int x, int y) {
        if (map.length > 0) {
            if ((x >= 0) && (y >= 0) && (x < map[0].length) && (x < map[0].length)) {
                if (type == TYPE_START || type == TYPE_END) {
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            if (over[i][j] == type)
                                over[i][j] = TYPE_EMPTY;
                        }
                    }
                    if (map[x][y] < TYPE_STONE_FLOOR || map[x][y] > TYPE_WOOD_FLOOR)
                        map[x][y] = TYPE_STONE_FLOOR;
                    over[x][y] = type;
                } else {
                    if ((over[x][y] == TYPE_START || over[x][y] == TYPE_END) && (type < TYPE_STONE_FLOOR || type > TYPE_WOOD_FLOOR))
                        over[x][y] = TYPE_EMPTY;
                    map[x][y] = type;
                }
            }
        }
    }

    public Vector3f getStart() {
        Vector3f ret = new Vector3f(-1, -1, 0);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (over[i][j] == TYPE_START) {
                    ret.x = i;
                    ret.y = j;
                    return ret;
                }
            }
        }
        return ret;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector3f getBounds() {
        return new Vector3f(width * SCALE, height * SCALE, 0);
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setMap(int[][] map, int xOffset, int yOffset) {
        int width = Math.min(this.width, map.length);
        int height = Math.min(this.height, map[0].length);
        for (int i = xOffset; i < width; i++)
            for (int j = yOffset; j < height; j++)
                this.map[i][j] = map[i][j];
    }
}
