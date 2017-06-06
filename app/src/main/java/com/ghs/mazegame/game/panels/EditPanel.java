package com.ghs.mazegame.game.panels;

import android.content.Context;
import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.display.Surface;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.engine.utils.ObjectManager;
import com.ghs.mazegame.game.Main;
import com.ghs.mazegame.game.interfaces.Panel;
import com.ghs.mazegame.game.objects.Backplate;
import com.ghs.mazegame.game.objects.Button;
import com.ghs.mazegame.game.map.Map;
import com.ghs.mazegame.game.objects.Image;
import com.ghs.mazegame.game.objects.Selector;
import com.ghs.mazegame.game.objects.ToggleButton;

import static com.ghs.mazegame.engine.display.Surface.swipe;
import static com.ghs.mazegame.engine.display.Surface.touchX;
import static com.ghs.mazegame.engine.display.Surface.touchY;
import static com.ghs.mazegame.game.Main.SCALE;
import static com.ghs.mazegame.game.Main.cameraHeight;
import static com.ghs.mazegame.game.objects.Backplate.makePlate;

public class EditPanel implements Panel {

    private final int NUM_BLOCKS = 24, NUM_TOGGLES = 8;

    private String name;

    private int state;

    private Map map;

    private Camera camera;

    private Backplate top, left, corner;
    private ToggleButton[] blockSelect;
    private Image[] blockPreview;
    private Button testPlay, leftArrow, rightArrow;

    private int curType, typeIter = 1, numPages, lastX = 0, lastY = 0;

    private Selector sel;

    private Context context;

    private float touchHeld = 0f;

    public static int paintType = 1;

    private boolean selector = false;

    private ObjectManager objectManager;

    public EditPanel(Camera camera, Context context) {
        this.context = context;
        numPages = NUM_BLOCKS / NUM_TOGGLES;
        this.camera = camera;
        this.map = new Map(camera, 0, 0, 20, 20);
        sel = new Selector(camera, 0, 0, 0.1f, SCALE * 3, SCALE * 3, new int[] {1, 1, 1, 1}, new int[] {R.drawable.stone_key_wall_left, R.drawable.stone_key_wall_right, R.drawable.stone_key_wall_up, R.drawable.stone_key_wall_down}, false);
        map.setState(Map.STATE_EDIT);
        curType = -1;
        for (int i = 0; i < map.getWidth(); ++i)
            for (int j = 0; j < map.getHeight(); ++j)
                map.setTile(Map.TYPE_EMPTY, i, j);
        corner = new Backplate(camera, 0, 0, SCALE * 2, (int) (SCALE * 1.5f), 2);
        top = new Backplate(camera, SCALE * 2, 0, camera.getWidth() - SCALE * 2, (int) (SCALE * 1.5f), 2);
        left = new Backplate(camera, 0, SCALE * 1.5f, SCALE * 2, (int) (camera.getHeight() - SCALE * 1.5f), 2);
        blockSelect = new ToggleButton[NUM_TOGGLES];
        blockPreview = new Image[NUM_BLOCKS];
        int dim = SCALE - 1;
        for (int i = 0; i < NUM_TOGGLES; i++)
            blockSelect[i] = new ToggleButton(camera, makePlate(SCALE, SCALE, 1, false), makePlate(SCALE, SCALE, 1, true), corner.getWidth() + ((top.getWidth() - NUM_TOGGLES * (dim + 1)) / 2) + i * (dim + 1), (top.getHeight() - dim) / 2, dim, dim);
        dim = SCALE - 6;
        blockPreview[0]  = new Image(camera, new Texture(R.drawable.brick_wall_prev),         blockSelect[0].getX() + (blockSelect[0].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[1]  = new Image(camera, new Texture(R.drawable.square_wall_prev),        blockSelect[1].getX() + (blockSelect[1].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[2]  = new Image(camera, new Texture(R.drawable.s_wall_prev),             blockSelect[2].getX() + (blockSelect[2].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[3]  = new Image(camera, new Texture(R.drawable.stone_floor_prev),        blockSelect[3].getX() + (blockSelect[3].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[4]  = new Image(camera, new Texture(R.drawable.limestone_floor_prev),    blockSelect[4].getX() + (blockSelect[4].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[5]  = new Image(camera, new Texture(R.drawable.wood_floor_prev),         blockSelect[5].getX() + (blockSelect[5].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[6]  = new Image(camera, new Texture(R.drawable.start_prev),              blockSelect[6].getX() + (blockSelect[6].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[7]  = new Image(camera, new Texture(R.drawable.end_prev),                blockSelect[7].getX() + (blockSelect[7].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[8]  = new Image(camera, new Texture(R.drawable.brick_wall_red_prev),    blockSelect[0].getX() + (blockSelect[0].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[9]  = new Image(camera, new Texture(R.drawable.brick_wall_orange_prev),    blockSelect[1].getX() + (blockSelect[1].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[10] = new Image(camera, new Texture(R.drawable.brick_wall_yellow_prev),   blockSelect[2].getX() + (blockSelect[2].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[11] = new Image(camera, new Texture(R.drawable.brick_wall_green_prev), blockSelect[3].getX() + (blockSelect[3].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[12] = new Image(camera, new Texture(R.drawable.brick_wall_cyan_prev),  blockSelect[4].getX() + (blockSelect[4].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[13] = new Image(camera, new Texture(R.drawable.brick_wall_blue_prev),  blockSelect[5].getX() + (blockSelect[5].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[14] = new Image(camera, new Texture(R.drawable.brick_wall_purple_prev),     blockSelect[6].getX() + (blockSelect[6].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[15] = new Image(camera, new Texture(R.drawable.brick_wall_magenta_prev),  blockSelect[7].getX() + (blockSelect[7].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[16] = new Image(camera, new Texture(R.drawable.sandstone_wall_prev),     blockSelect[0].getX() + (blockSelect[0].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[17] = new Image(camera, new Texture(R.drawable.sandstone_floor_prev),    blockSelect[1].getX() + (blockSelect[1].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[18] = new Image(camera, new Texture(R.drawable.stone_key_wall_prev),     blockSelect[2].getX() + (blockSelect[2].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[19] = new Image(camera, new Texture(R.drawable.stone_key_prev),          blockSelect[3].getX() + (blockSelect[3].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[20] = new Image(camera, new Texture(R.drawable.gold_key_prev),           blockSelect[4].getX() + (blockSelect[4].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[21] = new Image(camera, new Texture(R.drawable.brick_wall_green_prev),   blockSelect[5].getX() + (blockSelect[5].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[22] = new Image(camera, new Texture(R.drawable.brick_wall_green_prev),   blockSelect[6].getX() + (blockSelect[6].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        blockPreview[23] = new Image(camera, new Texture(R.drawable.brick_wall_green_prev),   blockSelect[7].getX() + (blockSelect[7].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim, 0.0f);
        testPlay = new Button(camera, new Texture(R.drawable.play_unpressed), new Texture(R.drawable.play_pressed), (corner.getWidth() - SCALE) / 2, (corner.getHeight() - SCALE) / 2, 0.1f, SCALE, SCALE, true);
        leftArrow = new Button(camera, new Texture(R.drawable.left_arrow), new Texture(R.drawable.left_arrow_down), corner.getWidth() + SCALE / 3, (corner.getHeight() - SCALE / 2) / 2, 0.1f, SCALE / 2, SCALE / 2, true);
        rightArrow = new Button(camera, new Texture(R.drawable.right_arrow), new Texture(R.drawable.right_arrow_down), camera.getWidth() - ((SCALE / 8) * 7), (top.getHeight() - SCALE / 2) / 2, 0.1f, SCALE / 2, SCALE / 2, true);
        state = -1;
    }

    public void update() {
        Log.d("time held", touchHeld + "");
        if (Surface.down) {
            touchHeld++;
        } else {
            touchHeld = 0f;
        }
        updateCamera();
        if (!top.contains(touchX, touchY) && !left.contains(touchX, touchY) && !corner.contains(touchX, touchY) && curType != -1 && touchX >= 0 && touchY >= 0)
            draw();
        updateToolbar();
        if (selector)
            sel.update();
    }

    private void updateCamera() {
        Vector3f dir = new Vector3f(swipe);
        swipe.x = 0;
        swipe.y = 0;
        if(dir.x != 0 || dir.y != 0) {
            int width = map.getWidth();
            int height = map.getHeight();
            camera.translate(dir);
            if (camera.getWidth() - left.getWidth() > map.getWidth() * SCALE)
                camera.setPosition(-left.getWidth(), camera.getY(), 0);
            else {
                if (camera.getX() < -left.getWidth())
                    camera.setPosition(-left.getWidth(), camera.getY(), 0);
                else if (camera.getX() > width * SCALE - camera.getWidth())
                    camera.setPosition(width * SCALE - camera.getWidth(), camera.getY(), 0);
            }
            if (camera.getHeight() - top.getHeight() > map.getHeight() * SCALE)
                camera.setPosition(camera.getX(), -top.getHeight(), 0);
            else {
                if (camera.getY() < -top.getHeight())
                    camera.setPosition(camera.getX(), -top.getHeight(), 0);
                else if (camera.getY() > height * SCALE - camera.getHeight())
                    camera.setPosition(camera.getX(), height * SCALE - camera.getHeight(), 0);
            }
        }
    }

    private void updateToolbar() {
        leftArrow.update();
        if (leftArrow.getState() == Button.STATE_RELEASED) {
            if (typeIter == 1) {
                typeIter = numPages;
            } else {
                typeIter--;
            }
            map.save("meh");
        }
        rightArrow.update();
        if (rightArrow.getState() == Button.STATE_RELEASED) {
            if (typeIter < numPages) {
                typeIter++;
            } else {
                typeIter = 1;
            }
        }
        for (int i = 0; i < NUM_TOGGLES; i++) {
            blockSelect[i].update();
            if(blockSelect[i].getState() == ToggleButton.STATE_PRESSED)
                curType = (i + 1)+((typeIter - 1) * NUM_TOGGLES);
        }
        for (int i = 0; i < NUM_TOGGLES; i++) {
            if((i + 1)+((typeIter - 1) * NUM_TOGGLES) == curType)
                blockSelect[i].setState(ToggleButton.STATE_PRESSED);
            else
                blockSelect[i].setState(ToggleButton.STATE_UNPRESSED);
        }
        testPlay.update();
        if(testPlay.getState() == Button.STATE_RELEASED && map.getStart().x != -1) {
            paintType = curType;
            curType = -1;
            map.save("meh");
            map.save("meh1");
            state = Main.STATE_PLAY_TEST;
        }
    }

    private void draw() {
        Log.e("Map", "entered");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(touchX > -1 && touchY > -1) {
            int x = (int) (touchX + camera.getX()) / SCALE;
            int y = (int) (touchY + camera.getY()) / SCALE;
            if (lastX != x || lastY != y) {
                touchHeld = 0;
            }
            lastX = x;
            lastY = y;
            if (map.getTile(x, y, false) == Map.TYPE_STONE_KEY_WALL && touchHeld > 40) {
                sel.setPosition(x * SCALE, y * SCALE);
                selector = true;
            } else if(touchHeld <= 40) {
                map.setTile(curType, x, y);
                selector = false;
            }
        }
        else
            updateCamera();
    }

    public void render() {
        map.render();
        top.render();
        left.render();
        corner.render();
        for (int i = 0; i < NUM_TOGGLES; i++) {
            blockSelect[i].render();
        }
        for (int i = (typeIter * NUM_TOGGLES) - NUM_TOGGLES; i < typeIter * NUM_TOGGLES; i++) {
            if(blockPreview[i] != null)
                blockPreview[i].render();
        }
        testPlay.render();
        leftArrow.render();
        rightArrow.render();
        if (selector)
            sel.render();
    }

    public int checkState() {
        int temp = state;
        state = -1;
        return temp;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setActive(boolean newMap) {
        if(newMap)
            map = new Map(camera, 0, 0, 20, 20);
        camera.setPosition(-left.getWidth(), -top.getHeight(), 0);
        map.setState(Map.STATE_EDIT);
    }

    public void setActive(String name) {
        map.load(name);
        camera.setPosition(-left.getWidth(), -top.getHeight(), 0);
        map.setState(Map.STATE_EDIT);
    }
}
