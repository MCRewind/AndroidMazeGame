package com.ghs.mazegame;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.os.Debug;
import android.util.Log;

import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.objects.Tile;
import com.ghs.mazegame.objects.UIImage;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    private final int SCALE = 32;

    private Resources resources;
    private Camera camera;
    private Tile[][] map;

    public Renderer(Resources resources) {
        this.resources = resources;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        camera = new Camera(384, 216);
        GLES20.glClearColor(0f, 0f, 0f, 1f);
        initMap();
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
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                map[i][j].render();
            }
        }
    }

    public void initMap(){
        map = new Tile[20][20];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if(i == 0 || j== 0 || i == map.length - 1 || j == map[0].length - 1)
                    map[i][j] = new Tile(camera, new Texture(resources, R.drawable.brick_wall), new Shader(resources, R.raw.vert, R.raw.frag), i * SCALE, j  * SCALE, SCALE, SCALE);
                else
                    map[i][j] = new Tile(camera, new Texture(resources, R.drawable.stone_floor), new Shader(resources, R.raw.vert, R.raw.frag), i * SCALE, j  * SCALE, SCALE, SCALE);
            }
        }
    }
}