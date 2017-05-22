package com.ghs.mazegame.game.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.engine.utils.Hitbox;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.objects.Image;
import com.ghs.mazegame.game.objects.Player;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static com.ghs.mazegame.game.Main.SCALE;
import static com.ghs.mazegame.game.Main.context;
import static com.ghs.mazegame.game.Main.maps;

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
        TYPE_STONE_KEY = 20,
        TYPE_GOLD_KEY = 21,
        TYPE_22 = 22,
        TYPE_23 = 23,
        TYPE_24 = 24;

    public static final boolean
        STATE_EDIT = false,
        STATE_PLAY = true;

    private int width, height, rWidth, rHeight;
    private float x, y, rightBound, bottomBound;

    private boolean state;

    private Camera camera;

    private int[][] map;
    private int[][] over;
    private static Tile[] tiles;

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
        rightBound = 0;
        bottomBound = 0;
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                map[i][j] = TYPE_EMPTY;
                over[i][j] = TYPE_EMPTY;
            }
        }
<<<<<<< HEAD
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
        tiles[TYPE_STONE_KEY]          = new Tile(camera, new Texture(R.drawable.stone_key),          false, 0.8f);
        tiles[TYPE_GOLD_KEY]           = new Tile(camera, new Texture(R.drawable.gold_key),           false, 0.8f);
        tiles[TYPE_22]                 = new Tile(camera, new Texture(R.drawable.true_tile),          false, 0.9f);
        tiles[TYPE_23]                 = new Tile(camera, new Texture(R.drawable.true_tile),          false, 0.9f);
        tiles[TYPE_24]                 = new Tile(camera, new Texture(R.drawable.true_tile),          false, 0.9f);
=======
        if(tiles == null) {
            tiles = new Tile[NUM_TILES];
            tiles[TYPE_EMPTY]              = new Tile(camera);
            tiles[TYPE_BRICK_WALL]         = new Tile(camera, R.drawable.brick_wall,         true,  0.9f);
            tiles[TYPE_SQUARE_WALL]        = new Tile(camera, R.drawable.square_wall,        true,  0.9f);
            tiles[TYPE_S_WALL]             = new Tile(camera, R.drawable.s_wall,             true,  0.9f);
            tiles[TYPE_STONE_FLOOR]        = new Tile(camera, R.drawable.stone_floor,        false, 0.9f);
            tiles[TYPE_LIMESTONE_FLOOR]    = new Tile(camera, R.drawable.limestone_floor,    false, 0.9f);
            tiles[TYPE_WOOD_FLOOR]         = new Tile(camera, R.drawable.wood_floor,         false, 0.9f);
            tiles[TYPE_START]              = new Tile(camera, R.drawable.start,              false, 0.8f);
            tiles[TYPE_END]                = new Tile(camera, R.drawable.end,                false, 0.8f);
            tiles[TYPE_BRICK_WALL_RED]     = new Tile(camera, R.drawable.brick_wall_red,     true,  0.9f);
            tiles[TYPE_BRICK_WALL_ORANGE]  = new Tile(camera, R.drawable.brick_wall_orange,  true,  0.9f);
            tiles[TYPE_BRICK_WALL_YELLOW]  = new Tile(camera, R.drawable.brick_wall_yellow,  true,  0.9f);
            tiles[TYPE_BRICK_WALL_GREEN]   = new Tile(camera, R.drawable.brick_wall_green,   true,  0.9f);
            tiles[TYPE_BRICK_WALL_CYAN]    = new Tile(camera, R.drawable.brick_wall_cyan,    true,  0.9f);
            tiles[TYPE_BRICK_WALL_BLUE]    = new Tile(camera, R.drawable.brick_wall_blue,    true,  0.9f);
            tiles[TYPE_BRICK_WALL_PURPLE]  = new Tile(camera, R.drawable.brick_wall_purple,  true,  0.9f);
            tiles[TYPE_BRICK_WALL_MAGENTA] = new Tile(camera, R.drawable.brick_wall_magenta, true,  0.9f);
            tiles[TYPE_SANDSTONE_WALL]     = new Tile(camera, R.drawable.sandstone_wall,     true,  0.9f);
            tiles[TYPE_SANDSTONE_FLOOR]    = new Tile(camera, R.drawable.sandstone_floor,    false, 0.9f);
            tiles[TYPE_STONE_KEY_WALL]     = new Tile(camera, R.drawable.stone_key_wall,     true,  0.9f);
            tiles[TYPE_20]                 = new Tile(camera, R.drawable.true_tile,          false, 0.9f);
            tiles[TYPE_21]                 = new Tile(camera, R.drawable.true_tile,          false, 0.9f);
            tiles[TYPE_22]                 = new Tile(camera, R.drawable.true_tile,          false, 0.9f);
            tiles[TYPE_23]                 = new Tile(camera, R.drawable.true_tile,          false, 0.9f);
            tiles[TYPE_24]                 = new Tile(camera, R.drawable.true_tile,          false, 0.9f);
        }
>>>>>>> dev
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

    public void setTileRaw(boolean isOver, int type, int x, int y){
        if(isOver){
            over[x][y] = type;
        } else {
            map[x][y] = type;
        }
    }

    public void setTile(int type, int x, int y) {
        if (map.length > 0) {
            if ((x >= 0) && (y >= 0) && (x < map[0].length) && (x < map[0].length)) {
                if (type == TYPE_START || type == TYPE_END || type == TYPE_STONE_KEY || type == TYPE_GOLD_KEY) {
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            if (over[i][j] == type)
                                over[i][j] = TYPE_EMPTY;
                        }
                    }
                    if (tiles[map[x][y]].isSolid() && map[x][y] != TYPE_EMPTY)
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

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void save(String filename) {
        if(!maps.contains(filename))
            maps.add(filename);

        try {
            FileOutputStream outputStream = context.openFileOutput("maps.log", Context.MODE_PRIVATE);
            for (int i = 0; i < maps.size(); i++)
                outputStream.write(maps.get(i).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = new File(context.getFilesDir(), filename + ".map");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int width = getWidth();
        int height = getHeight();

        byte[] map1 = new byte[width * height];
        byte[] map2 = new byte[width * height];

        ByteBuffer saveData = ByteBuffer.allocate(2 + 2 * width * height);
        try {
            BufferedOutputStream buf = new BufferedOutputStream(new FileOutputStream(file));
            saveData.put((byte) (width & 0xFF));
            saveData.put((byte) (height & 0xFF));

            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    map1[y*width+x] = (byte) (getTile(x, y, false) & 0xFF);
                    map2[y*width+x] = (byte) (getTile(x, y, true) & 0xFF);
                }
            }
            saveData.put(map1);
            saveData.put(map2);

            buf.write(saveData.array(), 0, saveData.array().length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            final int WIDTH = 12, HEIGHT = 8;
            Bitmap thumbnail = Bitmap.createBitmap(SCALE * WIDTH, SCALE * HEIGHT, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(thumbnail);

            Bitmap under = Bitmap.createBitmap(SCALE * WIDTH, SCALE * HEIGHT, Bitmap.Config.ARGB_8888);
            int[] pixels = new int[SCALE * SCALE];
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    tiles[map[i][j]].getBitmap().getPixels(pixels, 0, SCALE, 0, 0, SCALE, SCALE);
                    under.setPixels(pixels, 0, SCALE, i * SCALE, j * SCALE, SCALE, SCALE);
                }
            }
            canvas.drawBitmap(under, 4, 4, null);
            under.recycle();

            Bitmap overlay = Bitmap.createBitmap(SCALE * WIDTH, SCALE * HEIGHT, Bitmap.Config.ARGB_8888);
            pixels = new int[SCALE * SCALE];
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j <  HEIGHT; j++) {
                    if(over[i][j] != TYPE_EMPTY) {
                        tiles[over[i][j]].getBitmap().getPixels(pixels, 0, SCALE, 0, 0, SCALE, SCALE);
                        overlay.setPixels(pixels, 0, SCALE, i * SCALE, j * SCALE, SCALE, SCALE);
                    }
                }
            }
            canvas.drawBitmap(overlay, 4, 4, null);
            overlay.recycle();

            for (int i = 0; i < thumbnail.getWidth(); i++) {
                for (int j = 0; j < thumbnail.getHeight(); j++) {
                    if(i < 4 || j < 4 || i >= SCALE * WIDTH - 4 || j >= SCALE * HEIGHT - 4)
                    thumbnail.setPixel(i, j, 0xFFFFFFFF);
                }
            }

            File thumbFile = new File(context.getFilesDir(), filename + ".png");
            if(!thumbFile.exists())
                thumbFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(thumbFile);
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fos);
            thumbnail.recycle();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void load(String filename) {
        File file = new File(context.getFilesDir(), filename + ".map");
        if(file.exists()) {
            Log.e("File", "Exists!");
            int size = (int) file.length();
            byte[] bytes = new byte[size];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);

                buf.close();
                width = bytes[0] & 0xFF;
                height = bytes[1] & 0xFF;
                map = new int[width][height];
                over = new int[width][height];
                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[0].length; j++) {
                        map[i][j] = TYPE_EMPTY;
                        over[i][j] = TYPE_EMPTY;
                    }
                }

                for(int y = 0; y < height; y++)
                    for(int x = 0; x < width; x++)
                        setTileRaw(false, (bytes[2 + y * width + x] & 0xFF), x, y);

                for(int y = 0; y < height; y++)
                    for(int x = 0; x < width; x++)
                        setTileRaw(true, (bytes[2 + width * height + y * width + x] & 0xFF), x, y);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
