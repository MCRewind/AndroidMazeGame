package com.ghs.mazegame.game;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;

import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.game.objects.DPad;
import com.ghs.mazegame.game.objects.Player;
import com.ghs.mazegame.game.objects.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    public static final int SCALE = 16;

    public static int cameraWidth = 192;
    public static int cameraHeight = 108;

    public static Resources resources;
    private Camera camera;

    private Map map;
    private Player player;
    private DPad dpad;

    public Renderer(Resources resources) {
        this.resources = resources;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        camera = new Camera(cameraWidth, cameraHeight);

        map = new Map(camera, 0, 0, 20, 20);
        dpad = new DPad(camera, SCALE / 2, cameraHeight - SCALE * 2, SCALE * 1.5f, SCALE * 1.5f);
        player = new Player(camera, new Texture(R.drawable.samby), new Shader(R.raw.defaultvs, R.raw.defaultfs), SCALE, SCALE, SCALE, SCALE, map.getRightBound(), map.getBottomBound());

        GLES20.glClearColor(0f, 0f, 0f, 1f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
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
        dpad.update();
        updatePlayer();
        updateCamera();
    }

    public void render() {
        map.render();
        player.render();
        dpad.render();
    }

    private void updatePlayer() {
        float speed = 1.0f;
        float dx = 0;
        float dy = 0;
        int dir = dpad.getDir();
        if(dir == 1 || dir == 4 || dir == 6)
            dx = -speed;
        else if(dir == 3 || dir == 5 || dir == 8)
            dx = speed;
        if(dir == 1 || dir == 2 || dir == 3)
            dy = -speed;
        else if(dir == 6 || dir == 7 || dir == 8)
            dy = speed;
        player.translate(dx, dy);
        player.update();
        map.checkPlayerCollision(player);
    }

    private void updateCamera() {
        int width = map.getWidth();
        int height = map.getHeight();
        //Sets the camera position putting the player in the center
        camera.setPosition(player.getX() + (player.getWidth() - camera.getWidth()) / 2, player.getY() + (player.getHeight() - camera.getHeight()) / 2, 0);
        //Checks that the camera is not going past the edge of the map and adjusting the camera position if neccessary
        if(camera.getX() < 0)
            camera.setPosition(0, camera.getY(), 0);
        else if(camera.getX() > width * SCALE - camera.getWidth())
            camera.setPosition(width * SCALE - camera.getWidth(), camera.getY(), 0);
        if(camera.getY() < 0)
            camera.setPosition(camera.getX(), 0, 0);
        else if(camera.getY() > height * SCALE - camera.getHeight())
            camera.setPosition(camera.getX(), height * SCALE - camera.getHeight(), 0);
    }
}