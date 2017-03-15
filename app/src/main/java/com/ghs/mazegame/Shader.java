package com.ghs.mazegame;

import android.opengl.GLES20;
import android.renderscript.Matrix4f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cmeyer3887 on 3/7/2017.
 */

public class Shader {

    private String name;
    private int program, vs, fs;
    public static int VERT_ATTRIB = 0, TEX_COORD_ATTRIB = 1;
    boolean enabled;
    Map<String, Integer> uniforms;

    public Shader(String vertPath, String fragPath) {
        name = new File(new File(vertPath).getParent()).getName();
        uniforms = new HashMap<>();
        String vert = load(vertPath);
        String frag = load(fragPath);
        create(vert, frag);
    }

    private String load(String path) {
        StringBuilder file = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line;
            while((line = reader.readLine()) != null)
                file.append(line + '\n');
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.toString();
    }

    public void create(String vert, String frag) {
        program = GLES20.glCreateProgram();

        vs = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vs, vert);
        GLES20.glCompileShader(vs);
        IntBuffer buffer = IntBuffer.allocate(1);
        if(GLES20.glGetShaderInfoLog(vs).equals(1)) {
            throw new RuntimeException("Failed to compile shader! " + GLES20.glGetShaderInfoLog(vs));
        }

        fs = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fs, frag);
        GLES20.glCompileShader(fs);

        GLES20.glAttachShader(program, vs);
        GLES20.glAttachShader(program, fs);

        GLES20.glBindAttribLocation(program, VERT_ATTRIB, "vertices");
        GLES20.glBindAttribLocation(program, TEX_COORD_ATTRIB, "texCoords");

        GLES20.glLinkProgram(program);
        GLES20.glValidateProgram(program);
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

    public void setUniform1i(String name, int value) {
        enable();
        GLES20.glUniform1i(getLocation(name), value);
        disable();
    }

    public void setUniform1f(String name, float value) {
        enable();
        GLES20.glUniform1f(getLocation(name), value);
        disable();
    }

    public void setUniform2f(String name, float x, float y) {
        enable();
        GLES20.glUniform2f(getLocation(name), x, y);
        disable();
    }

    public void setUniform3f(String name, int x, int y, int z) {
        enable();
        GLES20.glUniform3f(getLocation(name), x, y, z);
        disable();
    }

    public void setUniformMat4f(String name, Matrix4f matrix) {
        enable();
        FloatBuffer buffer = FloatBuffer.allocate(16);
        buffer.put(matrix.getArray()).flip();
        GLES20.glUniformMatrix4fv(getLocation(name), 16,  false, buffer);
        disable();
    }

    public String getName() {
        return name;
    }

    public void enable() {
        enabled = true;
        GLES20.glUseProgram(program);
    }

    public void disable() {
        enabled = false;
        GLES20.glUseProgram(0);
    }
}
