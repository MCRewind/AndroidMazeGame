package com.ghs.mazegame.engine.display;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.game.Renderer;

import static com.ghs.mazegame.game.Renderer.cameraHeight;
import static com.ghs.mazegame.game.Renderer.cameraWidth;

public class Surface extends GLSurfaceView {

    public static float touchX = -1;
    public static float touchY = -1;
    public static Vector3f swipe = new Vector3f();

    private GLSurfaceView surface;

    public Surface(Context context) {
        super(context);
    }


    public boolean onTouchEvent(MotionEvent e) {
        if(e.getActionMasked() == MotionEvent.ACTION_UP) {
            touchX = -1;
            touchY = -1;
        }
        else {
            touchX = e.getX() * cameraWidth / getWidth();
            touchY = e.getY() * cameraHeight / getHeight();
        }
        return true;
    }
}
