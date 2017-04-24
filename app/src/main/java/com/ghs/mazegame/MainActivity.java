package com.ghs.mazegame;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.util.Log;

import com.ghs.mazegame.engine.display.Surface;

import java.io.FileInputStream;
import java.util.Scanner;
>>>>>>> bb8141b46427c88b20b00ed2d66b55956dfbdf71

public class MainActivity extends Activity {

    Surface surface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        surface = new Surface(this);
        surface.setEGLContextClientVersion(2);
        surface.setRenderer(new Renderer(surface.getResources()));

        setContentView(surface);
    }

    public void onPause() {
        super.onPause();
        surface.onPause();
    }

    public void onResume() {
        super.onResume();
        surface.onResume();
    }
}
