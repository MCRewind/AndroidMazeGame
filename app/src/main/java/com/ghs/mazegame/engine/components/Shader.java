package com.ghs.mazegame.engine.components;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.renderscript.Matrix4f;

import com.ghs.mazegame.game.Renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Shader {

    private int program;

    public static int VERT_ATTRIB = 0, TEX_COORD_ATTRIB = 1;

    private Map<String, Integer> uniforms;

    public Shader(int vertId, int fragId) {
        uniforms = new HashMap<>();
        makeProgram(vertId, fragId);
    }

    private void makeProgram(int vertId, int fragId) {
        Resources resources = Renderer.resources;

        Scanner vert = new Scanner(resources.openRawResource(vertId));
        Scanner frag = new Scanner(resources.openRawResource(fragId));

        String vertCode = "";
        String fragCode = "";

        while (vert.hasNextLine())
            vertCode += vert.nextLine();

        while (frag.hasNextLine())
            fragCode += frag.nextLine();

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertCode);

        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragCode);

        program = GLES20.glCreateProgram();

        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        GLES20.glBindAttribLocation(program, VERT_ATTRIB, "vertices");
        GLES20.glBindAttribLocation(program, TEX_COORD_ATTRIB, "itexCoords");

        GLES20.glLinkProgram(program);

        GLES20.glUseProgram(program);
    }

    private static int loadShader(int type, String shaderText) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderText);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public int getLocation(String name) {
        if(uniforms.containsKey(name))
            return uniforms.get(name);
        int location = GLES20.glGetUniformLocation(program, name);
        uniforms.put(name, location);
        if(location != -1)
            return location;
        else
            throw new RuntimeException("Could not find uniform: " + name);
    }

    public void setUniform1i(String name, int x) {
        enable();
        GLES20.glUniform1i(getLocation(name), x);
        disable();
    }

    public void setUniform2f(String name, float x, float y) {
        enable();
        GLES20.glUniform2f(getLocation(name), x, y);
        disable();
    }

    public void setUniform4f(String name, float x, float y, float z, float w) {
        enable();
        GLES20.glUniform4f(getLocation(name), x, y, z, w);
        disable();
    }

    public void setUniformMat4f(String name, Matrix4f matrix) {
        enable();
        GLES20.glUniformMatrix4fv(getLocation(name), 1, false, matrix.getArray(), 0);
        disable();
    }

    public void enable() {
        GLES20.glUseProgram(program);
    }

    public void disable() {
        GLES20.glUseProgram(0);
    }
}
