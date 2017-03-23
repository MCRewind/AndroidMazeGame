package com.ghs.engine.components;

import android.opengl.GLES20;

import com.ghs.engine.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class Shader {

    private static int program;

    public static int positionHandle;
 
    public static int colorHandle;

    public static void makeProgram() {
        int vertexShader = 0;
        int fragmentShader = 0;
        try {
            vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, FileUtils.getFileContents(new File("shaders/vertex.glsl")));
            fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FileUtils.getFileContents(new File("shaders/fragment.glsl")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        program = GLES20.glCreateProgram();

        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        GLES20.glLinkProgram(program);

        positionHandle = GLES20.glGetAttribLocation(program,"a_pos");
        colorHandle = GLES20.glGetUniformLocation(program, "u_color");

        GLES20.glUseProgram(program);
    }

    private static int loadShader(int type, String shaderText) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderText);
        GLES20.glCompileShader(shader);
        return shader;
    }
}