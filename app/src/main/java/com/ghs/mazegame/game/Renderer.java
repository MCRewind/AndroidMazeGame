package com.ghs.mazegame.game;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.interfaces.Panel;

import com.ghs.mazegame.game.panels.EditPanel;
import com.ghs.mazegame.game.panels.MainMenu;
import com.ghs.mazegame.game.panels.PlayTestPanel;
import com.ghs.mazegame.game.panels.SplashPanel;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    public static final int
        STATE_SPLASH_SCREEN = 0,
        STATE_PLAY = 1,
        STATE_PLAY_TEST = 2,
        STATE_EDIT = 3,
        STATE_MAIN_MENU = 4;

    public static final int SCALE = 16;

    public static int cameraWidth = 192;
    public static int cameraHeight = 108;

    public static float time;

    public static Resources resources;
    public static Shader defaultShader;

    private float pastTime, curTime;

    private Camera camera;

    private Context context;

    private Panel[] panels;
    public int cur = STATE_SPLASH_SCREEN;

    public Renderer(Resources resources, Context context) {
        this.resources = resources;
        this.context = context;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        camera = new Camera(cameraWidth, cameraHeight);

        defaultShader = new Shader(R.raw.defaultvs, R.raw.defaultfs);

        GLES20.glClearColor(0f, 0f, 0f, 1f);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        pastTime = System.nanoTime() / 1000000f;

        panels = new Panel[5];
        panels[STATE_SPLASH_SCREEN] = new SplashPanel(camera, 4000, new Texture(R.drawable.splash_screen));
        panels[STATE_PLAY_TEST] = new PlayTestPanel(camera);
        panels[STATE_EDIT] = new EditPanel(camera, context);
        panels[STATE_MAIN_MENU] = new MainMenu(camera);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }


    public void onDrawFrame(GL10 gl) {
        curTime = System.nanoTime() / 1000000f;
        time = (curTime - pastTime) / 1000f;
        pastTime = curTime;
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        update();
        render();
    }

    private void update() {
        panels[cur].update();
        checkState();
    }

    private void render() {
        panels[cur].render();
    }

    private void checkState() {
        int state = panels[cur].checkState();
        switch(state) {
            case STATE_EDIT:
                if(cur == STATE_MAIN_MENU) {
                    EditPanel edit = (EditPanel) panels[STATE_EDIT];
                    edit.setActive();
                }
                else if(cur == STATE_PLAY_TEST){
                    EditPanel edit = (EditPanel) panels[STATE_EDIT];
                    PlayTestPanel play = (PlayTestPanel) panels[STATE_PLAY_TEST];
                    edit.setActive(play.getMap());
                }
                break;
            case STATE_PLAY_TEST:
                PlayTestPanel play = (PlayTestPanel) panels[STATE_PLAY_TEST];
                EditPanel edit = (EditPanel) panels[STATE_EDIT];
                play.setActive(edit.getMap());
                break;
            case STATE_MAIN_MENU:
                if(cur == STATE_SPLASH_SCREEN) {
                    MainMenu menu = (MainMenu) panels[STATE_MAIN_MENU];
                    menu.setActive();
                }
        };
        if(state != -1)
            cur = state;
    }
}