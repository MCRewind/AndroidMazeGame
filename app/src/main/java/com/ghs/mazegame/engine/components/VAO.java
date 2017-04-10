package com.ghs.mazegame.engine.components;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_INT;
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
        vbo = GLES20.glGenBuffers();
        GLES20.glBindBuffer(GL_ARRAY_BUFFER, vbo);
        GLES20.glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);

        ibo = GLES20.glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        GLES20.glBufferData(GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indices), GL_STATIC_DRAW);

        tcbo = GLES20.glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tcbo);
        GLES20.glBufferData(GL_ARRAY_BUFFER, createIntBuffer(texCoords), GL_STATIC_DRAW);

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

    public FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public IntBuffer createIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}