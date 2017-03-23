package com.ghs.mazegame;

import android.net.Uri;
import android.opengl.GLES20;

import com.ghs.util.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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