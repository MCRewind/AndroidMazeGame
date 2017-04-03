package com.ghs.engine.components;

import android.opengl.GLES20;

import com.ghs.engine.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Shader {

    private String name;
    private int program, vs, fs;
    public static int VERT_ATTRIB = 0, TEX_COORD_ATTRIB = 1;
    boolean enabled;
    Map<String, Integer> uniforms;

    public static int positionHandle;
 
    public static int colorHandle;

    public Shader(String vertPath, String fragPath) {
        name = new File(new File(vertPath).getParent()).getName();
        uniforms = new HashMap<>();
        try {
            String vert = FileUtils.getFileContents(new File(vertPath));
            String frag = FileUtils.getFileContents(new File(fragPath));
            makeProgram(vert, frag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeProgram(String vertSource, String fragSource) {
        int vertexShader = 0;
        int fragmentShader = 0;
        vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertSource);
        fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragSource);

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