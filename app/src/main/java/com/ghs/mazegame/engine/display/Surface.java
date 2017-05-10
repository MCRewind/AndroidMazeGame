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

    int GLOBAL_TOUCH_POSITION_X = 0;
    int GLOBAL_TOUCH_CURRENT_POSITION_X = 0;
    int GLOBAL_TOUCH_POSITION_Y = 0;
    int GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;

    private float[] x = {-1, -1}, y = {-1, -1}, x2 = {-1, -1}, y2 = {-1, -1};

    private GLSurfaceView surface;

    public Surface(Context context) {
        super(context);
    }


    public boolean onTouchEvent(MotionEvent e) {
        touchX = e.getX() * cameraWidth / getWidth();
        touchY = e.getY() * cameraHeight / getHeight();
        return true;
    }
}
