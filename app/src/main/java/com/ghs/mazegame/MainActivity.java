package com.ghs.mazegame;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {

    //private GLSurfaceView surface;
    private MyGLSurfaceView mySurfaceView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*

        surface = new GLSurfaceView(this);
        surface.setEGLContextClientVersion(2);
        surface.setRenderer(new Renderer());
        setContentView(surface);
        */
        mySurfaceView = new MyGLSurfaceView(this);
        mySurfaceView.setEGLContextClientVersion(2);
        mySurfaceView.setRenderer(new Renderer());
        mySurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(mySurfaceView);
    }

    public void onPause() {
        super.onPause();
        mySurfaceView.onPause();
    }

    public void onResume() {
        super.onResume();
        mySurfaceView.onResume();
    }
}
