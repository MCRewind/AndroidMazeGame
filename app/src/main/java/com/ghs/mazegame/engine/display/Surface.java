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
        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_UP:

        }
        camX = (x * com.ghs.mazegame.game.Renderer.cameraWidth) / getWidth();
        camY = (y * com.ghs.mazegame.game.Renderer.cameraHeight) / getHeight();
        return true;
    }

}
