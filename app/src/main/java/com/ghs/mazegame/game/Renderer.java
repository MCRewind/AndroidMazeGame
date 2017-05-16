package com.ghs.mazegame.game;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.interfaces.Panel;

import com.ghs.mazegame.game.panels.EditPanel;
import com.ghs.mazegame.game.panels.MainMenu;
import com.ghs.mazegame.game.panels.PlayTestPanel;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    public static final int
        STATE_PLAY = 0,
        STATE_PLAY_TEST = 1,
        STATE_EDIT = 2,
        STATE_MAIN_MENU = 3;

    public static final int SCALE = 16;

    public static int cameraWidth = 192;
    public static int cameraHeight = 108;

    public static float time;

    public static Resources resources;
    public static Shader defaultShader;

    private float pastTime, curTime;

    private Camera camera;

    private Panel[] panels;
    public static int cur = STATE_EDIT;

    public Renderer(Resources resources) {
        this.resources = resources;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        camera = new Camera(cameraWidth, cameraHeight);

        defaultShader = new Shader(R.raw.defaultvs, R.raw.defaultfs);

        GLES20.glClearColor(0f, 0f, 0f, 1f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        pastTime = System.nanoTime() / 1000000f;

        panels = new Panel[4];
        panels[STATE_PLAY_TEST] = new PlayTestPanel(camera);
        panels[STATE_EDIT] = new EditPanel(camera);
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
        if(state != -1) {
            panels[state].setActive(panels[cur].getMap());
            cur = state;
        }
    }
}