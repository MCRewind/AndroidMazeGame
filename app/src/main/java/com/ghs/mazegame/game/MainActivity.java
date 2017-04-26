package com.ghs.mazegame.game;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.ghs.mazegame.engine.display.Surface;

public class MainActivity extends Activity {

    Surface surface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        surface = new Surface(this);
        surface.setEGLContextClientVersion(2);
        surface.setRenderer(new Renderer(surface.getResources()));

        setContentView(surface);

        FullScreencall();
    }

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT < 19){
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for higher api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
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
