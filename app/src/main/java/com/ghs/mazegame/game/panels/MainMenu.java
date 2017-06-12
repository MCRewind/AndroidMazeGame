package com.ghs.mazegame.game.panels;

import android.util.Log;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.game.Main;
import com.ghs.mazegame.game.interfaces.Panel;
import com.ghs.mazegame.game.objects.Button;
import com.ghs.mazegame.game.objects.Font;
import com.ghs.mazegame.game.objects.Image;
import com.ghs.mazegame.game.objects.Rectangle;
import com.ghs.mazegame.game.objects.Thumbnail;

import java.util.ArrayList;

import static com.ghs.mazegame.engine.display.Surface.swipe;
import static com.ghs.mazegame.engine.display.Surface.touchX;
import static com.ghs.mazegame.engine.display.Surface.touchY;
import static com.ghs.mazegame.game.Main.SCALE;
import static com.ghs.mazegame.game.Main.STATE_EDIT;
import static com.ghs.mazegame.game.Main.cameraHeight;
import static com.ghs.mazegame.game.Main.cameraWidth;
import static com.ghs.mazegame.game.Main.keyboardShowing;
import static com.ghs.mazegame.game.Main.maps;
import static com.ghs.mazegame.game.objects.Button.STATE_PRESSED;
import static com.ghs.mazegame.game.objects.Thumbnail.STATE_RELEASED;
import static com.ghs.mazegame.game.objects.Thumbnail.STATE_UNPRESSED;
import static com.ghs.mazegame.game.objects.Thumbnail.maxChars;

public class MainMenu implements Panel {

    private final int HOR_VIEWS = 2;

    private final float SPEED = 10;

    private final float CURSOR_FPS = 2;

    private int state = -1;

    private int x, y, ax, ay;

    private int map = -1;

    private Camera camera;

    private Button[] dir;
    private Button[] actions;
    private Image background;

    private ArrayList<Thumbnail> thumbnails;
    private Button newMap, up;
    private Image levelBackground;
    private boolean goTop;

    private Vector3f lastTouch;
    private Vector3f scroll;
    private boolean scrolled;

    private String name;
    private Font font;
    private boolean naming;
    private float sx, sy;
    private Rectangle textBorder, textMain;
    private Button enter, back;

    private Rectangle cursor;
    private float pastTime, mspb;
    private boolean showCursor;
    private String lastInput;

    public MainMenu(Camera camera) {
        this.camera = camera;
        x = 0;
        y = 0;
        ay = 0;
        ax = 0;
        background = new Image(camera, new Texture(R.drawable.menu_background), 0, 0, 0.9f, cameraWidth, cameraHeight, false);
        levelBackground = new Image(camera, new Texture(R.drawable.menu_level_background), 0, cameraHeight, 0.9f, cameraWidth, SCALE * 7, false);
        actions = new Button[HOR_VIEWS];
        actions[0] = new Button(camera, new Texture(R.drawable.menu_levels_button), new Texture(R.drawable.menu_levels_button_pressed), (cameraWidth - 82) / 2, 55, 0.2f, 82, 22, false);
        actions[1] = new Button(camera, new Texture(R.drawable.menu_help_button), new Texture(R.drawable.menu_help_button_pressed), cameraWidth + ((cameraWidth - 56) / 2f), 55, 0.2f, 56, 22 , false);
        dir = new Button[2];
        dir[0] = new Button(camera, new Texture(R.drawable.left_menu_arrow), new Texture(R.drawable.left_menu_arrow_pressed), 0, 0, 0.1f, SCALE * 2, cameraHeight, false);
        dir[1] = new Button(camera, new Texture(R.drawable.right_menu_arrow), new Texture(R.drawable.right_menu_arrow_pressed), camera.getWidth() - SCALE * 2, 0, 0.1f, SCALE * 2, cameraHeight, false);
        up = new Button(camera, new Texture(R.drawable.menu_up_arrow), new Texture(R.drawable.menu_up_arrow_pressed), 0, cameraHeight, 0.1f, cameraWidth, SCALE, false);
        thumbnails = new ArrayList<>();
        lastTouch = new Vector3f(-1, -1, -1);
        scroll = new Vector3f();
        scrolled = false;
        pastTime = 0;
        mspb = 1000 / CURSOR_FPS;
        showCursor = true;
        lastInput = new String();
        loadThumbnails();
    }

    public void update() {
        if(!naming) {
            if (y == 0) {
                if (camera.getY() > 0 && ay != -1) {
                    camera.translate(0, -cameraHeight / SPEED, 0);
                    if (camera.getY() < 0) {
                        camera.setPosition(camera.getX(), 0, 0);
                        ay = -1;
                    }
                } else {
                    dir[0].update();
                    dir[1].update();
                    if (dir[0].getState() == Button.STATE_RELEASED && x > 0) {
                        ax = x;
                        --x;
                    } else if (dir[1].getState() == Button.STATE_RELEASED && x < HOR_VIEWS - 1) {
                        ax = x;
                        ++x;
                    }
                    if (camera.getX() / cameraWidth > x) {
                        camera.translate(-cameraWidth / SPEED, 0, 0);
                        if (camera.getX() / cameraWidth < x)
                            camera.setPosition(x * cameraWidth, camera.getY(), 0);
                    } else if (camera.getX() / cameraWidth < x) {
                        camera.translate(cameraWidth / SPEED, 0, 0);
                        if (camera.getX() / cameraWidth > x)
                            camera.setPosition(x * cameraWidth, camera.getY(), 0);
                    } else
                        ax = -1;
                    if (ax == -1) {
                        actions[x].update();
                        if (actions[0].getState() == Button.STATE_RELEASED) {
                            y = 1;
                            ay = 0;
                        }
                    }
                    dir[0].setX(camera.getX());
                    dir[1].setX(camera.getX() + cameraWidth - dir[1].getWidth());
                    background.setX(camera.getX());
                }
            } else if (y == 1) {
                if (camera.getY() < cameraHeight && ay != -1) {
                    camera.translate(0, cameraHeight / SPEED, 0);
                    if (camera.getY() > cameraHeight) {
                        camera.setPosition(camera.getX(), cameraHeight, 0);
                        ay = -1;
                    }
                } else {
                    if (!scrolled) {
                        up.update();
                        if (up.getState() == STATE_RELEASED) {
                            if (camera.getY() < cameraHeight + 8) {
                                y = 0;
                                ay = 1;
                            } else
                                goTop = true;
                        } else if (up.getState() < STATE_PRESSED && !goTop) {
                            for (int i = 0; i < thumbnails.size(); i++) {
                                thumbnails.get(i).update();
                                if (thumbnails.get(i).getState() == STATE_RELEASED) {
                                    state = STATE_EDIT;
                                    map = i;
                                }
                            }
                            newMap.update();
                            if (newMap.getState() == STATE_RELEASED) {
                                naming = true;
                                Main.showKeyboard();
                                sx = (cameraWidth - maxChars * font.getMaxWidth() - font.getHeight() - 5) / 2f;
                                sy = cameraHeight / 6f;
                                textBorder.setPosition(camera.getX() + sx - 2, camera.getY() + sy - 2);
                                textMain.setPosition(camera.getX() + sx - 1, camera.getY() + sy - 1);
                                enter.setPosition(sx + maxChars * font.getMaxWidth() + 3, camera.getY() + sy - 2);
                                pastTime = System.nanoTime() / 1000000f;
                            }
                        }
                    } else {
                        for (int i = 0; i < thumbnails.size(); i++) {
                            thumbnails.get(i).setState(STATE_UNPRESSED);
                        }
                        newMap.setState(STATE_UNPRESSED);
                    }
                    if (!goTop) {
                        if (lastTouch.y != -1 && touchY != -1)
                            scroll.y = lastTouch.y - touchY;
                        else if (swipe.y != 0)
                            scroll.y = swipe.y;
                        camera.translate(scroll);
                        if (camera.getY() + cameraHeight > newMap.getY() + newMap.getHeight() + 11)
                            camera.setPosition(camera.getX(), newMap.getY() + newMap.getHeight() + 11 - cameraHeight, 0);
                        if (camera.getY() < cameraHeight)
                            camera.setPosition(camera.getX(), cameraHeight, 0);
                        if (touchY == -1 && lastTouch.y != -1 && scroll.y < 3 && scroll.y > -3)
                            scroll.y = 0;
                        if (scroll.y >= 0.5f || scroll.y <= -0.5f)
                            scrolled = true;
                        else if (scroll.y == 0 && touchY == -1)
                            scrolled = false;
                        if (scroll.y != 0) {
                            scroll.y *= 0.9f;
                            if (scroll.y < 0.5f && scroll.y > -0.5f)
                                scroll.y = 0;
                        }
                        lastTouch.x = touchX;
                        lastTouch.y = touchY;
                    } else {
                        camera.translate(0, -15, 0);
                        if (camera.getY() < cameraHeight) {
                            camera.setPosition(camera.getX(), cameraHeight, 0);
                            goTop = false;
                        }
                    }
                }
                up.setPosition(camera.getX(), camera.getY());
            }
        }
        else {
            Log.e("Map", "Showing: " + keyboardShowing());
            if(textBorder.contains(touchX, touchY))
                Main.showKeyboard();
            name = Main.getInput();
            if(font.getLength(name) > textMain.getWidth() - 2) {
                Main.truncate(name.length() - 1);
                name = Main.getInput();
            }
            if(!lastInput.equals(name)) {
                showCursor = true;
                pastTime = System.nanoTime() / 1000000f;
            }
            else if(System.nanoTime() / 1000000f - pastTime > mspb) {
                showCursor = !showCursor;
                pastTime += mspb;
            }
            if(showCursor)
                cursor.setPosition(camera.getX() + sx + font.getLength(name), camera.getY() + sy);
            enter.update();
            back.update();
            if(enter.getState() == Button.STATE_RELEASED) {
                state = STATE_EDIT;
                map = maps.size();
                maps.add(name);
                Main.clear();
                Main.hideKeyboard();
            }
            else if(back.getState() == Button.STATE_RELEASED) {
                Main.hideKeyboard();
                Main.clear();
                naming = false;
            }
            lastInput = name;
        }
    }

    public void render() {
        if (y == 0 || ay == 0) {
            background.render();
            actions[x].render();
            if (ax != -1)
                actions[ax].render();
            dir[0].render();
            dir[1].render();
        }
        if(y == 1 || ay == 1) {
            if(ay == 1) {
                levelBackground.setY(cameraHeight);
                levelBackground.render();
            }
            else {
                if(levelBackground.getY() + levelBackground.getHeight() <= camera.getY())
                    levelBackground.translate(0, levelBackground.getHeight());
                else if(levelBackground.getY() > camera.getY())
                    levelBackground.translate(0, -levelBackground.getHeight());
                levelBackground.render();
                if(levelBackground.getY() + levelBackground.getHeight() < camera.getY() + cameraHeight) {
                    levelBackground.translate(0, levelBackground.getHeight());
                    levelBackground.render();
                    levelBackground.translate(0, -levelBackground.getHeight());
                }
            }
            if(!naming) {
                for (int i = 0; i < thumbnails.size(); i++)
                    thumbnails.get(i).render();
                newMap.render();
                up.render();
            }
            else {
                textMain.render();
                textBorder.render();
                font.drawString(name, camera.getX() + sx, camera.getY() + sy);
                enter.render();
                if(showCursor && Main.keyboardShowing())
                    cursor.render();
                back.render();
            }
        }
    }

    public int checkState() {
        int temp = state;
        state = -1;
        return temp;
    }

    public void setActive() {
        loadThumbnails();
    }

    private void loadThumbnails() {
        thumbnails.clear();
        int i;
        for (i = 0; i < maps.size(); i++)
            thumbnails.add(new Thumbnail(camera, maps.get(i), ((i % 3) * ((cameraWidth - SCALE) / 3f)) + (SCALE * (i % 3 + 1) / 4f),
                    ((i / 3) * ((cameraWidth - SCALE) * 2 / 9f + 6)) + (SCALE * (i / 3 + 1) / 4f) + cameraHeight + SCALE, 0.2f));
        newMap = new Button(camera, new Texture(R.drawable.new_map_button), new Texture(R.drawable.new_map_button_pressed),
                ((i % 3) * ((cameraWidth - SCALE) / 3f)) + (SCALE * (i % 3 + 1) / 4f),
                ((i / 3) * ((cameraWidth - SCALE) * 2 / 9f + 6)) + (SCALE * (i / 3 + 1) / 4f) + cameraHeight + SCALE, 0.2f, (cameraWidth - SCALE) / 3f, (cameraWidth - SCALE) * 2 / 9f, false);
        font = new Font(camera, R.drawable.bunky_font, R.raw.bunky, 6, 0, 1, 1, 1, 1);
        maxChars = (int) (newMap.getWidth() / font.getMaxWidth());
        font.setHeight(10);
        sx = (cameraWidth - maxChars * font.getMaxWidth() - font.getHeight() - 5) / 2f;
        sy = cameraHeight / 6f + cameraHeight;
        textBorder = new Rectangle(camera, sx - 2, sy - 2, 0.12f, maxChars * font.getMaxWidth() + 4, font.getHeight() + 4, 1, 1, 1, 1);
        textMain   = new Rectangle(camera, sx - 1, sy - 1, 0.11f, maxChars * font.getMaxWidth() + 2, font.getHeight() + 2, 0, 0, 0, 1);
        enter = new Button(camera, new Texture(R.drawable.menu_enter), new Texture(R.drawable.menu_enter_pressed), sx + maxChars * font.getMaxWidth() + 3, sy - 2, 0.1f, font.getHeight() + 4, font.getHeight() + 4, false);
        cursor = new Rectangle(camera, sx, sy, 0.05f, 1, font.getHeight(), 1, 1, 1, 1);
        back = new Button(camera, new Texture(R.drawable.menu_back_arrow), new Texture(R.drawable.menu_back_arrow_pressed), 1, 1, 0.1f, font.getHeight() + 4, font.getHeight() + 4, true);
        name = "";
    }

    public int getMap() {
        return map;
    }
}