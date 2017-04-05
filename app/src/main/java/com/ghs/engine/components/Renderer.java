package com.ghs.engine.components;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

<<<<<<< HEAD:app/src/main/java/com/ghs/mazegame/Renderer.java
import com.ghs.mazegame.engine.component.Shader;
=======
import com.ghs.engine.components.Shader;
>>>>>>> 34f61c032e6fe69fd7077833566b46f1e0746ffb:app/src/main/java/com/ghs/engine/components/Renderer.java

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    private FloatBuffer vertBuffer;

<<<<<<< HEAD:app/src/main/java/com/ghs/mazegame/Renderer.java
    private Resources resources;

    private Shader shader;

    public Renderer(Resources resources) {
        this.resources = resources;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        shader = new Shader(resources, R.raw.vert, R.raw.frag);
        GLES20.glEnableVertexAttribArray(Shader.VERT_ATTRIB);
=======
    private Shader shader;

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        shader = new Shader("src/main/shaders/vertex.glsl", "src/main/shaders/fragment.glsl");


        GLES20.glEnableVertexAttribArray(Shader.positionHandle);
>>>>>>> 34f61c032e6fe69fd7077833566b46f1e0746ffb:app/src/main/java/com/ghs/engine/components/Renderer.java

        float[] verts =
            {
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
            };

        vertBuffer = makeFloatBuffer(verts);
    }

    public void onSurfaceChanged(GL10 gl, int wid, int hig) {
        GLES20.glViewport(0,0,wid,hig);
    }

    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0f, 0f, 0f, 1f);
        GLES20.glUniform4f(Shader.colorHandle, 1.0f, 0.0f, 0.0f, 1.0f);

        GLES20.glVertexAttribPointer(Shader.VERT_ATTRIB, 3, GLES20.GL_FLOAT, false, 0, vertBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

    }//end on drawFrame

    public FloatBuffer makeFloatBuffer(float[] array)
    {
        //maybe my missing key:
        FloatBuffer floatBuff= ByteBuffer.allocateDirect(array.length * 4).
                order(ByteOrder.nativeOrder()).asFloatBuffer();

        floatBuff.put(array).position(0);

        return floatBuff;
    };
}