package com.ghs.mazegame.game.panels;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.engine.utils.ObjectManager;
import com.ghs.mazegame.game.Renderer;
import com.ghs.mazegame.game.objects.Backplate;
import com.ghs.mazegame.game.objects.Button;
import com.ghs.mazegame.game.map.Map;
import com.ghs.mazegame.game.objects.Image;
import com.ghs.mazegame.game.objects.ToggleButton;

import static com.ghs.mazegame.engine.display.Surface.swipe;
import static com.ghs.mazegame.engine.display.Surface.touchX;
import static com.ghs.mazegame.engine.display.Surface.touchY;
import static com.ghs.mazegame.game.Renderer.SCALE;
import static com.ghs.mazegame.game.Renderer.time;
import static com.ghs.mazegame.game.objects.Backplate.makePlate;

public class EditPanel {

    private final int NUM_BLOCKS = 16, NUM_TOGGLES = 8;

    private int state;

    private Map map;

    private Camera camera;

    private Backplate top, left, corner;
    private ToggleButton[] blockSelect;
    private Image[] blockPreview;
    private Button testPlay, leftArrow, rightArrow;

    private int curType, typeIter = 1, numPages;

    private ObjectManager objectManager;

    public EditPanel(Camera camera) {
        numPages = NUM_BLOCKS/NUM_TOGGLES;
        this.camera = camera;
        this.map = new Map(camera, 20, 20);
        map.setState(Map.STATE_EDIT);
        curType = Map.TYPE_BRICK_WALL;
        for (int i = 0; i < map.getWidth(); ++i)
            for (int j = 0; j < map.getHeight(); ++j)
                map.setTile(Map.TYPE_EMPTY, i, j);
        corner = new Backplate(camera, 0, 0, SCALE * 2, (int) (SCALE * 1.5f), 2);
        top = new Backplate(camera, SCALE * 2, 0, camera.getWidth() - SCALE * 2, (int) (SCALE * 1.5f), 2);
        left = new Backplate(camera, 0, SCALE * 1.5f, SCALE * 2, (int) (camera.getHeight() - SCALE * 1.5f), 2);
        camera.setPosition(-left.getWidth(), -top.getHeight(), 0);
        blockSelect = new ToggleButton[NUM_TOGGLES];
        blockPreview = new Image[NUM_BLOCKS];
        int dim = SCALE - 1;
        for (int i = 0; i < NUM_TOGGLES; i++)
            blockSelect[i] = new ToggleButton(camera, makePlate(SCALE, SCALE, 1, false), makePlate(SCALE, SCALE, 1, true), corner.getWidth() + ((top.getWidth() - NUM_TOGGLES * (dim + 1)) / 2) + i * (dim + 1), (top.getHeight() - dim) / 2, dim, dim);
        dim = SCALE - 6;
        blockPreview[0] = new Image(camera, new Texture(R.drawable.brick_wall_prev),                     blockSelect[0].getX() + (blockSelect[0].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[1] = new Image(camera, new Texture(R.drawable.square_wall_prev),                    blockSelect[1].getX() + (blockSelect[1].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[2] = new Image(camera, new Texture(R.drawable.s_wall_prev),                         blockSelect[2].getX() + (blockSelect[2].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[3] = new Image(camera, new Texture(R.drawable.stone_floor_prev),                    blockSelect[3].getX() + (blockSelect[3].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[4] = new Image(camera, new Texture(R.drawable.limestone_floor_prev),                blockSelect[4].getX() + (blockSelect[4].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[5] = new Image(camera, new Texture(R.drawable.wood_floor_prev),                     blockSelect[5].getX() + (blockSelect[5].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[6] = new Image(camera, new Texture(R.drawable.start_prev),                          blockSelect[6].getX() + (blockSelect[6].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[7] = new Image(camera, new Texture(R.drawable.end_prev),                            blockSelect[7].getX() + (blockSelect[7].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[8] = new Image(camera, new Texture(R.drawable.brick_wall_blue_prev),                blockSelect[0].getX() + (blockSelect[0].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[9] = new Image(camera, new Texture(R.drawable.brick_wall_cyan_prev),                blockSelect[1].getX() + (blockSelect[1].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[10] = new Image(camera, new Texture(R.drawable.brick_wall_green_prev),              blockSelect[2].getX() + (blockSelect[2].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[11] = new Image(camera, new Texture(R.drawable.brick_wall_magenta_prev),            blockSelect[3].getX() + (blockSelect[3].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[12] = new Image(camera, new Texture(R.drawable.brick_wall_orange_prev),             blockSelect[4].getX() + (blockSelect[4].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[13] = new Image(camera, new Texture(R.drawable.brick_wall_purple_prev),             blockSelect[5].getX() + (blockSelect[5].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[14] = new Image(camera, new Texture(R.drawable.brick_wall_red_prev),                blockSelect[6].getX() + (blockSelect[6].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        blockPreview[15] = new Image(camera, new Texture(R.drawable.brick_wall_yellow_prev),             blockSelect[7].getX() + (blockSelect[7].getWidth() - dim) / 2, (top.getHeight() - dim) / 2, dim, dim);
        testPlay = new Button(camera, new Texture(R.drawable.play_unpressed), new Texture(R.drawable.play_pressed), (corner.getWidth() - SCALE) / 2, (corner.getHeight() - SCALE) / 2, SCALE, SCALE);
        leftArrow = new Button(camera, new Texture(R.drawable.left_arrow), new Texture(R.drawable.left_arrow_down), corner.getWidth() + SCALE / 3, (corner.getHeight() - SCALE / 2) / 2, SCALE / 2, SCALE / 2);
        rightArrow = new Button(camera, new Texture(R.drawable.right_arrow), new Texture(R.drawable.right_arrow_down), camera.getWidth() - ((SCALE / 8) * 7), (top.getHeight() - SCALE / 2) / 2, SCALE / 2, SCALE / 2);
        state = -1;
    }

    public void update() {
        updateCamera();
        if (!top.contains(touchX, touchY) && !left.contains(touchX, touchY) && !corner.contains(touchX, touchY))
            draw();
        updateToolbar();
    }

    private void updateCamera() {
        float speed = SCALE * 3;
        Vector3f dir = new Vector3f(swipe);
        if(dir.x != 0 || dir.y != 0) {
            int width = map.getWidth();
            int height = map.getHeight();
            camera.translate(dir.mul(time * speed, new Vector3f()));
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
        if(testPlay.getState() == Button.STATE_RELEASED && map.getStart().x != -1)
            state = Renderer.STATE_PLAY_TEST;
        leftArrow.update();
        if (leftArrow.getState() == Button.STATE_PRESSED) {
            if (typeIter == 1) {
                typeIter = numPages;
            } else {
                typeIter--;
            }
        }
        rightArrow.update();
        if (rightArrow.getState() == Button.STATE_PRESSED) {
            if (typeIter < numPages) {
                typeIter++;
            } else {
                typeIter = 1;
            }
        }
    }

    private void draw() {
        if(touchX > -1 && touchY > -1) {
            int x = (int) (touchX + camera.getX()) / SCALE;
            int y = (int) (touchY + camera.getY()) / SCALE;
            map.setTile(curType, x, y);
        }
    }

    public void render() {
        map.render();
        top.render();
        left.render();
        corner.render();
        for (int i = 0; i < NUM_TOGGLES; i++) {
            blockSelect[i].render();
        }
        for (int i = (typeIter*NUM_TOGGLES)-NUM_TOGGLES; i < typeIter*NUM_TOGGLES; i++) {
            if(blockPreview[i] != null)
                blockPreview[i].render();
        }
        testPlay.render();
        leftArrow.render();
        rightArrow.render();
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
}
