package com.ghs.mazegame;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.VAO;
import com.ghs.mazegame.engine.display.Camera;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    private Resources resources;

    private Shader shader;

    private VAO vao;

    private Camera camera;

    public Renderer(Resources resources) {
        this.resources = resources;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        shader = new Shader(resources, R.raw.vert, R.raw.frag);

        float[] vertices = {
            25, 25, 0,  //TOP LEFT
            25, 75, 0,  //BOTTOM LEFT
            75, 75, 0,  //BOTTOM RIGHT
            75, 25, 0   //TOP RIGHT
        };
        int[] indices = {
            0, 1, 3,
            1, 2, 3
        };

        vao = new VAO(vertices, indices);
        camera = new Camera(100, 100);
    }

    public void onSurfaceChanged(GL10 gl, int wid, int hig) {
        GLES20.glViewport(0,0,wid,hig);
    }

    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0f, 0f, 0f, 1f);
        GLES20.glUniform4f(Shader.colorHandle, 1.0f, 0.0f, 0.0f, 1.0f);

        shader.setUniformMat4f("projection", camera.getProjection());

        shader.enable();
        vao.render();
        shader.disable();
    }
}