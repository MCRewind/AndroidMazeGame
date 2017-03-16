package com.ghs.mazegame;

import android.opengl.GLES20;

public class Shader {

    private final static String vertCode =
            "attribute vec4 a_pos;"+
                    "void main(){"+
                    "gl_Position = a_pos;" +
                    "}";

    private final static String fragCode =
            "precision mediump float;" +
                    "uniform vec4 u_color;" +
                    "void main(){" +
                    "gl_FragColor = u_color;" +
                    "}";

    private static int program;

    public static int positionHandle;

    public static int colorHandle;

    public static void makeProgram() {
        int vertexShader   = loadShader(GLES20.GL_VERTEX_SHADER, vertCode);

        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragCode);

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