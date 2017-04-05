package com.ghs.mazegame;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

import java.io.FileInputStream;
import java.util.Scanner;

import com.ghs.engine.components.Renderer;

public class MainActivity extends Activity {

    //private GLSurfaceView surface;
    private MyGLSurfaceView mySurfaceView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*

        surface = new GLSurfaceView(this);
        surface.setEGLContextClientVersion(2);
<<<<<<< HEAD
        surface.setRenderer(new Renderer(surface.getResources()));

=======
        surface.setRenderer(new Renderer());
>>>>>>> 34f61c032e6fe69fd7077833566b46f1e0746ffb
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
