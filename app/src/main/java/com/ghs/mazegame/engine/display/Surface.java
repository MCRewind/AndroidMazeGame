package com.ghs.mazegame.engine.display;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

public class Surface extends GLSurfaceView {

    public static float camX;
    public static float camY;

    private GLSurfaceView surface;

    public Surface(Context context) {
        super(context);
    }

    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        if(e.getAction() == MotionEvent.ACTION_UP) {
            camX = -1;
            camY = -1;
        }
        else {
            camX = (x * com.ghs.mazegame.game.Renderer.cameraWidth) / getWidth();
            camY = (y * com.ghs.mazegame.game.Renderer.cameraHeight) / getHeight();
        }
        Log.e("kek", "X: " + camX + ", Y: " + camY);
        return true;
    }

}
