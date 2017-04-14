package com.ghs.mazegame.engine.components;

<<<<<<< HEAD
public class VAO {

/**
 * Created by cmeyer3887 on 3/23/2017.
 */
<<<<<<< HEAD

    public VAO() {

=======

    public VAO() {
        -+`                     ij
>>>>>>> 8b1c2fbfb6c1455e31805f333058242da36d7ed0
    }

}
=======
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VAO {

    private int vbo, ibo, count;

    public VAO(float[] vertices, int[] indices) {
        count = indices.length;
        init(vertices, indices);
    }

    private void init(float[] vertices, int[] indices) {
        int[] tempVBO = new int[1], tempIBO = new int[1];

        GLES20.glGenBuffers(1, tempVBO, 0);
        GLES20.glGenBuffers(1, tempIBO, 0);

        vbo = tempVBO[0];
        ibo = tempIBO[0];

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertices.length * 4, toFloatBuffer(vertices), GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, count * 4, toIntBuffer(indices), GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void render() {
        GLES20.glEnableVertexAttribArray(Shader.VERT_ATTRIB);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);
        GLES20.glVertexAttribPointer(Shader.VERT_ATTRIB, 3, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, count, GLES20.GL_UNSIGNED_INT, 0);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);

        GLES20.glDisableVertexAttribArray(Shader.VERT_ATTRIB);
    }

    public FloatBuffer toFloatBuffer(float[] array) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(array).position(0);
        return buffer;
    };

    public IntBuffer toIntBuffer(int[] array) {
        IntBuffer buffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(array).position(0);
        return buffer;
    };
}
>>>>>>> sam
