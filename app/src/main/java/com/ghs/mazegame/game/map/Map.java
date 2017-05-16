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

    public static int NUM_TILES = 19;

    public static final HashMap<String, Integer> types = new HashMap<>();

    public static final boolean
            STATE_EDIT = false,
            STATE_PLAY = true,
            TILE_PAINT = true;

    private int width, height, rWidth, rHeight;

    private boolean state;

    private Camera camera;

    private int[][] map;
    private int[][] over;
    private Tile[] tiles;

    public Map(Camera camera, int width, int height) {
        this.camera = camera;
        map = new int[width][height];
        over = new int[width][height];
        this.width = width;
        this.height = height;
        rWidth = (int) Math.ceil((camera.getX() + camera.getWidth()) / SCALE);
        rHeight = (int) Math.ceil((camera.getY() + camera.getHeight()) / SCALE);
        tileInit();
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                map[i][j] = types.get("TYPE_EMPTY");
                over[i][j] = types.get("TYPE_EMPTY");
            }
        }
        tiles = new Tile[NUM_TILES];
        tiles[types.get("TYPE_EMPTY")] = new Tile(camera);
        tiles[types.get("TYPE_BRICK_WALL")] = new Tile(camera, new Texture(R.drawable.brick_wall), true, 0.9f);
        tiles[types.get("TYPE_SQUARE_WALL")] = new Tile(camera, new Texture(R.drawable.square_wall), true, 0.9f);
        tiles[types.get("TYPE_S_WALL")] = new Tile(camera, new Texture(R.drawable.s_wall), true, 0.9f);
        tiles[types.get("TYPE_STONE_FLOOR")] = new Tile(camera, new Texture(R.drawable.stone_floor), false, 0.9f);
        tiles[types.get("TYPE_LIMESTONE_FLOOR")] = new Tile(camera, new Texture(R.drawable.limestone_floor), false, 0.9f);
        tiles[types.get("TYPE_WOOD_FLOOR")] = new Tile(camera, new Texture(R.drawable.wood_floor), false, 0.9f);
        tiles[types.get("TYPE_START")] = new Tile(camera, new Texture(R.drawable.start), false, 0.8f);
        tiles[types.get("TYPE_END")] = new Tile(camera, new Texture(R.drawable.end), false, 0.8f);
        tiles[types.get("TYPE_BRICK_WALL_BLUE")] = new Tile(camera, new Texture(R.drawable.brick_wall_blue), true, 0.9f);
        tiles[types.get("TYPE_BRICK_WALL_CYAN")] = new Tile(camera, new Texture(R.drawable.brick_wall_cyan), true, 0.9f);
        tiles[types.get("TYPE_BRICK_WALL_GREEN")] = new Tile(camera, new Texture(R.drawable.brick_wall_green), true, 0.9f);
        tiles[types.get("TYPE_BRICK_WALL_MAGENTA")] = new Tile(camera, new Texture(R.drawable.brick_wall_magenta), true, 0.9f);
        tiles[types.get("TYPE_BRICK_WALL_ORANGE")] = new Tile(camera, new Texture(R.drawable.brick_wall_orange), true, 0.9f);
        tiles[types.get("TYPE_BRICK_WALL_PURPLE")] = new Tile(camera, new Texture(R.drawable.brick_wall_purple), true, 0.9f);
        tiles[types.get("TYPE_BRICK_WALL_RED")] = new Tile(camera, new Texture(R.drawable.brick_wall_red), true, 0.9f);
        tiles[types.get("TYPE_BRICK_WALL_YELLOW")] = new Tile(camera, new Texture(R.drawable.brick_wall_yellow), true, 0.9f);
        tiles[types.get("TYPE_TRUE_TILE")] = new Tile(camera, new Texture(R.drawable.true_tile), false, 0.9f);
        tiles[types.get("TYPE_FALSE_TILE")] = new Tile(camera, new Texture(R.drawable.false_tile), false, 0.9f);
        tiles[types.get("TYPE_SANDSTONE_WALL")] = new Tile(camera, new Texture(R.drawable.sandstone_wall), true, 0.9f);
    }

    private void tileInit() {
        types.put("TYPE_EMPTY",              0);
        types.put("TYPE_BRICK_WALL",         1);
        types.put("TYPE_SQUARE_WALL",        2);
        types.put("TYPE_S_WALL",             3);
        types.put("TYPE_STONE_FLOOR",        4);
        types.put("TYPE_LIMESTONE_FLOOR",    5);
        types.put("TYPE_WOOD_FLOOR",         6);
        types.put("TYPE_START",              7);
        types.put("TYPE_END",                8);
        types.put("TYPE_BRICK_WALL_BLUE",    9);
        types.put("TYPE_BRICK_WALL_CYAN",    10);
        types.put("TYPE_BRICK_WALL_GREEN",   11);
        types.put("TYPE_BRICK_WALL_MAGENTA", 12);
        types.put("TYPE_BRICK_WALL_ORANGE",  13);
        types.put("TYPE_BRICK_WALL_PURPLE",  14);
        types.put("TYPE_BRICK_WALL_RED",     15);
        types.put("TYPE_BRICK_WALL_YELLOW",  16);
        types.put("TYPE_TRUE_TILE",          17);
        types.put("TYPE_FALSE_TILE",         18);
        types.put("TYPE_SANDSTONE_WALL",     19);
        NUM_TILES = types.size();
    }

    public void render() {
        int minX = (int) (camera.getX() / SCALE);
        int maxX = minX + rWidth;
        int minY = (int) (camera.getY() / SCALE);
        int maxY = minY + rHeight;
        for (int i = Math.max(minX, 0); i <= maxX && i < map.length; i++) {
            for (int j = Math.max(minY, 0); j <= maxY && j < map[0].length; j++) {
                if (map[i][j] < NUM_TILES) {
                    if (map[i][j] != types.get("TYPE_EMPTY") || state != STATE_PLAY) {
                        tiles[map[i][j]].setPosition(i * SCALE, j * SCALE);
                        tiles[map[i][j]].render();
                    }
                    if (over[i][j] != types.get("TYPE_EMPTY")) {
                        tiles[over[i][j]].setPosition(i * SCALE, j * SCALE);
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
                    if (TILE_PAINT)
                        //map[i][j] = EditPanel.paintType;
                        map[i][j] = types.get("TYPE_TRUE_TILE");
                    overHandle(over[i][j]);
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


    public void overHandle(int tile) {
        if (tile == types.get("TYPE_END")) {
            state = STATE_EDIT;
            Renderer.cur = Renderer.STATE_EDIT;
        }
    }


    public void setTile(int type, int x, int y) {
        if (map.length > 0) {
            if ((x >= 0) && (y >= 0) && (x < map[0].length) && (x < map[0].length)) {
                if (type == types.get("TYPE_START") || type == types.get("TYPE_END")) {
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            if (over[i][j] == type)
                                over[i][j] = types.get("TYPE_EMPTY");
                        }
                    }
                    if (map[x][y] < types.get("TYPE_STONE_FLOOR") || map[x][y] > types.get("TYPE_WOOD_FLOOR"))
                        map[x][y] = types.get("TYPE_STONE_FLOOR");
                    over[x][y] = type;
                } else {
                    if (over[x][y] == types.get("TYPE_START") || over[x][y] == types.get("TYPE_END"))
                        over[x][y] = types.get("TYPE_EMPTY");
                    map[x][y] = type;
                }
            }
        }
    }

    public Vector3f getStart() {
        Vector3f ret = new Vector3f(-1, -1, 0);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (over[i][j] == types.get("TYPE_START")) {
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
}
