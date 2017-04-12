package com.ghs.mazegame.engine.components;

import android.opengl.GLES20;

import com.ghs.mazegame.Renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_INT;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_INT;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glDrawElements;

/**
 * Created by cmeyer3887 on 3/23/2017.
 */

public class VAO {

    private int count, vbo, ibo, tcbo;

    public VAO(float[] vertices, int[] indices, int[] texCoords) {
        count = indices.length;
        init(vertices, indices, texCoords);
    }

    private void init(float[] vertices, int[] indices, int[] texCoords) {
        IntBuffer buffers = ByteBuffer.allocateDirect(3).asIntBuffer();
        GLES20.glGenBuffers(3, buffers);

        vbo = buffers.get(0);
        ibo = buffers.get(1);
        tcbo = buffers.get(2);

        GLES20.glBindBuffer(GL_ARRAY_BUFFER, vbo);
        GLES20.glBufferData(GL_ARRAY_BUFFER, vertices.length, toFloatBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        GLES20.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.length, toIntBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, tcbo);
        GLES20.glBufferData(GL_ARRAY_BUFFER, texCoords.length, toIntBuffer(texCoords), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void render() {
        GLES20.glEnableVertexAttribArray(Shader.VERT_ATTRIB);
        GLES20.glEnableVertexAttribArray(Shader.TEX_COORD_ATTRIB);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        GLES20.glVertexAttribPointer(Shader.VERT_ATTRIB, 3, GL_FLOAT, false, 0, 0);


        glBindBuffer(GL_ARRAY_BUFFER, tcbo);
        GLES20.glVertexAttribPointer(Shader.TEX_COORD_ATTRIB, 2, GL_INT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        GLES20.glDisableVertexAttribArray(Shader.VERT_ATTRIB);
        GLES20.glDisableVertexAttribArray(Shader.TEX_COORD_ATTRIB);
    }

    public IntBuffer toIntBuffer(int[] array) {
        IntBuffer buffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(array).position(0);
        return buffer;
    };

    public FloatBuffer toFloatBuffer(float[] array) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(array).position(0);
        return buffer;
    };

}