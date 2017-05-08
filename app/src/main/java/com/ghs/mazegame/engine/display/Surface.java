package com.ghs.mazegame.engine.display;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.game.Renderer;

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

    private final GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            /*
                https://developer.android.com/training/gestures/scale.html
                https://developer.android.com/training/gestures/multi.html
                https://developer.android.com/training/gestures/scroll.html
            */
            Log.d("scroll", "xDis: " + distanceX + " yDis: " + distanceY);
            return true;
        }
    };


    public boolean onTouchEvent(MotionEvent e) {
        int pointerCount = e.getPointerCount();
        Log.d("h", "hello");
        Log.d("p", Integer.toString(pointerCount));

        float tx = e.getX();

        float ty = e.getY();
        //if(pointerCount == 2){
        int action = e.getActionMasked();
        int actionIndex = e.getActionIndex();
        String actionString;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (pointerCount == 2) {
                    GLOBAL_TOUCH_POSITION_X = (int) e.getX(1);
                    GLOBAL_TOUCH_POSITION_Y = (int) e.getY(1);
                }
                touchX = (tx * com.ghs.mazegame.game.Renderer.cameraWidth) / getWidth();
                touchY = (ty * com.ghs.mazegame.game.Renderer.cameraHeight) / getHeight();
                break;
            case MotionEvent.ACTION_UP:
                if (pointerCount == 2) {
                    GLOBAL_TOUCH_CURRENT_POSITION_X = 0;
                    GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;
                }
                touchX = -1;
                touchY = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                touchX = (tx * com.ghs.mazegame.game.Renderer.cameraWidth) / getWidth();
                touchY = (ty * com.ghs.mazegame.game.Renderer.cameraHeight) / getHeight();
                if (pointerCount == 2) {
                    GLOBAL_TOUCH_CURRENT_POSITION_X = (int) e.getX(1);
                    GLOBAL_TOUCH_CURRENT_POSITION_Y = (int) e.getY(1);
                    int diffX = GLOBAL_TOUCH_POSITION_X - GLOBAL_TOUCH_CURRENT_POSITION_X;
                    int diffY = GLOBAL_TOUCH_POSITION_Y - GLOBAL_TOUCH_CURRENT_POSITION_Y;
                    Log.d("X", Integer.toString(diffX));
                    swipe.x = (float) diffX;
                    swipe.y = (float) diffY;
                }


                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (pointerCount == 2) {
                    GLOBAL_TOUCH_POSITION_X = (int) e.getX(1);
                    GLOBAL_TOUCH_POSITION_Y = (int) e.getY(1);
                }
                break;
            default:
                break;
        }

        pointerCount = 0;
        // }
        //else if (pointerCount == 1){
//            GLOBAL_TOUCH_POSITION_X = 0;
//            GLOBAL_TOUCH_CURRENT_POSITION_X = 0;
//            switch (e.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    touchX = (tx * com.ghs.mazegame.game.Renderer.cameraWidth) / getWidth();
//                    touchY = (ty * com.ghs.mazegame.game.Renderer.cameraHeight) / getHeight();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    touchX = -1;
//                    touchY = -1;
//                    break;
//            }
        // }


//        float tx = e.getX();
//        float ty = e.getY();
//        if(e.getActionIndex() == 0) {
//            Log.d("finger 1", "x: " + x + " y: " + y);
//            if (e.getAction() == MotionEvent.ACTION_UP) {
//                x[0] = -1;
//                y[0] = -1;
//                x[1] = -1;
//                y[1] = -1;
//                touchX = -1;
//                touchY = -1;
//            } else {
//                x[0] = x[1];
//                y[0] = y[1];
//                x[1] = (tx * com.ghs.mazegame.game.Renderer.cameraWidth) / getWidth();
//                y[1] = (ty * com.ghs.mazegame.game.Renderer.cameraHeight) / getHeight();
//                touchX = x[1];
//                touchY = y[1];
//            }
//        }
//        else {
//            Log.d("finger 2", "x: " + x + " y: " + y);
//            if (e.getAction() == MotionEvent.ACTION_UP) {
//                x2[0] = -1;
//                y2[0] = -1;
//                x2[1] = -1;
//                y2[1] = -1;
//            } else {
//                x2[0] = x2[1];
//                y2[0] = y2[1];
//                x2[1] = (tx * com.ghs.mazegame.game.Renderer.cameraWidth) / getWidth();
//                y2[1] = (ty * com.ghs.mazegame.game.Renderer.cameraHeight) / getHeight();
//            }
//        }
//        if(x2[0] != -1) {
//            swipe.x = ((x2[1] - x2[0]) + (x[0] = x[1])) / 2.0f;
//            swipe.x = ((y2[1] - y2[0]) + (y[0] = y[1])) / 2.0f;
//        }
//        else {
//            swipe.x = 0;
//            swipe.y = 0;
//        }
        return true;
    }
}
