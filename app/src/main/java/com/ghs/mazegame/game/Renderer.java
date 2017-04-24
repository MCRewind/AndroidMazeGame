package com.ghs.mazegame.game;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.ghs.mazegame.engine.UIManager;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.objects.World;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    public static final int SCALE = 16;

    public static int cameraWidth = 192;
    public static int cameraHeight = 108;

    public static  Resources resources;
    private Camera camera;
    private World world;
    private UIManager uiManager;

    public Renderer(Resources resources) {
        this.resources = resources;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        camera = new Camera(cameraWidth, cameraHeight);
        world = new World(camera, 0, 0, 20, 20);
        uiManager = new UIManager();
        GLES20.glClearColor(0f, 0f, 0f, 1f);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }


    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        update();
        render();
    }

    public void update() {

    }

    public void render() {
        world.render();

    }
}