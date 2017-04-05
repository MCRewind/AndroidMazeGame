package com.ghs.mazegame;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {

    private Surface surface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        surface = new Surface(this);
        surface.setEGLContextClientVersion(2);
        surface.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
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
