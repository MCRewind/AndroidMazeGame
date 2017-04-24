package com.ghs.mazegame.game;

import android.app.Activity;
import android.os.Bundle;

import com.ghs.mazegame.engine.display.Surface;

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
