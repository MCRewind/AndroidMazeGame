package com.ghs.mazegame.engine.components;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VAO {

    private FloatBuffer buffer;

    public VAO(float[] vertices) {
        init(vertices);
    }

    private void init(float[] vertices) {
        buffer = toFloatBuffer(vertices);
    }

    public void render() {
        GLES20.glEnableVertexAttribArray(Shader.VERT_ATTRIB);
        GLES20.glVertexAttribPointer(Shader.VERT_ATTRIB, 3, GLES20.GL_FLOAT, false, 0, buffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
        GLES20.glDisableVertexAttribArray(Shader.VERT_ATTRIB);
    }

    public FloatBuffer toFloatBuffer(float[] array) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(array).position(0);
        return buffer;
    };
}
