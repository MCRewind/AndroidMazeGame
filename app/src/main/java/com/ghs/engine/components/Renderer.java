package com.ghs.engine.components;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.ghs.engine.components.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    private FloatBuffer vertBuffer;

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Shader.makeProgram();
        GLES20.glEnableVertexAttribArray(Shader.positionHandle);

        float[] verts =
                {
                        0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 0.0f,
                        1.0f, 1.0f, 0.0f
                };

        vertBuffer = makeFloatBuffer(verts);
    }

    public void onSurfaceChanged(GL10 gl, int wid, int hig) {
        GLES20.glViewport(0,0,wid,hig);
    }

    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0f, 0f, 0f, 1f);
        GLES20.glUniform4f(Shader.colorHandle, 1.0f, 0.0f, 0.0f, 1.0f);

        GLES20.glVertexAttribPointer(Shader.positionHandle,3,
                GLES20.GL_FLOAT, false, 0, vertBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

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