package com.ghs.mazegame.engine.display;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by cmeyer3887 on 4/24/2017.
 */

public class Surface extends GLSurfaceView {

    private GLSurfaceView surface;

    public Surface(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();

        float camX;
        float camY;

        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_UP:

        }

        camX = (x * com.ghs.mazegame.game.Renderer.cameraWidth) / getWidth();
        camY = (y * com.ghs.mazegame.game.Renderer.cameraHeight) / getHeight();

        Log.d("position", "X: " + camX +  " Y: " + camY);

        return true;
    }

}
