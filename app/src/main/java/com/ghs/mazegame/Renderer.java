package com.ghs.mazegame;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.VAO;
<<<<<<< HEAD
import com.ghs.mazegame.engine.display.Camera;
=======

import java.nio.FloatBuffer;
>>>>>>> e2c9be26e16a1b837b9f5e28300b5dca83da518a

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

<<<<<<< HEAD
=======
    private float width = 0.5f, height = 0.5f;

    private FloatBuffer vertBuffer;

<<<<<<< HEAD
=======
>>>>>>> e2c9be26e16a1b837b9f5e28300b5dca83da518a
>>>>>>> 8b1c2fbfb6c1455e31805f333058242da36d7ed0
    private Resources resources;

    private Shader shader;

    private VAO vao;

<<<<<<< HEAD
    private Camera camera;

=======
>>>>>>> e2c9be26e16a1b837b9f5e28300b5dca83da518a
    public Renderer(Resources resources) {
        this.resources = resources;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        shader = new Shader(resources, R.raw.vert, R.raw.frag);
<<<<<<< HEAD

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
<<<<<<< HEAD
=======
=======
>>>>>>> 8b1c2fbfb6c1455e31805f333058242da36d7ed0
        GLES20.glEnableVertexAttribArray(Shader.VERT_ATTRIB);

        float[] vertices = new float[] {
                0.0f,  0.0f,   1.0f, //TOP LEFT
                0.0f,  height, 1.0f, //BOTTOM LEFT
                width, height, 1.0f, //BOTTOM RIGHT
                width, 0.0f,   1.0f  //TOP RIGHT
        };
        int[] indices = new int[] {
                0, 1, 3,
                1, 2, 3
        };
        int[] texCoords = new int[] {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
        vao = new VAO(vertices, indices, texCoords);
<<<<<<< HEAD
=======
>>>>>>> e2c9be26e16a1b837b9f5e28300b5dca83da518a
>>>>>>> 8b1c2fbfb6c1455e31805f333058242da36d7ed0
    }

    public void onSurfaceChanged(GL10 gl, int wid, int hig) {
        GLES20.glViewport(0,0,wid,hig);
    }

    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0f, 0f, 0f, 1f);
        GLES20.glUniform4f(Shader.colorHandle, 1.0f, 0.0f, 0.0f, 1.0f);

<<<<<<< HEAD
        shader.setUniformMat4f("projection", camera.getProjection());

        shader.enable();
        vao.render();
        shader.disable();
=======
        vao.render();
>>>>>>> e2c9be26e16a1b837b9f5e28300b5dca83da518a
    }
}