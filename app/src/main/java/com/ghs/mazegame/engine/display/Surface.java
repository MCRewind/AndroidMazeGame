package com.ghs.mazegame.engine.display;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.game.Main;

public class Surface extends GLSurfaceView {

    public static float touchX = -1;
    public static float touchY = -1;
    public static Vector3f swipe = new Vector3f();

    float GLOBAL_TOUCH_POSITION_X = 0;
    float GLOBAL_TOUCH_CURRENT_POSITION_X = 0;
    float GLOBAL_TOUCH_POSITION_Y = 0;
    float GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;

    private boolean isPanning = false;

    public Surface(Context context) {
        super(context);
    }

    public boolean onTouchEvent(MotionEvent e) {
        int pointerCount = e.getPointerCount();

        Log.e("Pointers", "Pointer Count: " + pointerCount);

        float tx = e.getX();
        float ty = e.getY();

        if(pointerCount == 2){
            float tx2 = e.getX(1);
            float ty2 = e.getY(1);

            int action = e.getActionMasked();
            switch (action)
            {
                case MotionEvent.ACTION_DOWN:
                    touchX = -1;
                    touchY = -1;
                    isPanning = true;
                case MotionEvent.ACTION_POINTER_DOWN:
                    GLOBAL_TOUCH_POSITION_X = (((tx2+tx)/(float)2) * (float) Main.cameraWidth) / (float)getWidth();
                    GLOBAL_TOUCH_POSITION_Y = (((ty2+ty)/(float)2) * (float) Main.cameraHeight) / (float)getHeight();
                    isPanning = true;
                    touchX = -1;
                    touchY = -1;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP:
                    GLOBAL_TOUCH_CURRENT_POSITION_X = 0f;
                    GLOBAL_TOUCH_CURRENT_POSITION_Y = 0f;
                    touchX = -1;
                    touchY = -1;
                    swipe.x = 0;
                    swipe.y = 0;
                    isPanning = false;
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    isPanning = true;
                    touchX = -1;
                    touchY = -1;
                    GLOBAL_TOUCH_CURRENT_POSITION_X = (((tx2+tx)/(float)2) * (float) Main.cameraWidth) / (float)getWidth();
                    GLOBAL_TOUCH_CURRENT_POSITION_Y = (((ty2+ty)/(float)2) * (float) Main.cameraHeight) / (float)getHeight();

                    float diffX = GLOBAL_TOUCH_POSITION_X-GLOBAL_TOUCH_CURRENT_POSITION_X;
                    float diffY = GLOBAL_TOUCH_POSITION_Y-GLOBAL_TOUCH_CURRENT_POSITION_Y;
                    GLOBAL_TOUCH_POSITION_X = (((tx2+tx)/(float)2) * (float) Main.cameraWidth) / (float)getWidth();
                    GLOBAL_TOUCH_POSITION_Y = (((ty2+ty)/(float)2) * (float) Main.cameraHeight) / (float)getHeight();
                    if(Math.abs(diffX)<-0.4){
                        swipe.x = 0;
                    }
                    else {
                        swipe.x = diffX;
                    }
                    if(Math.abs(diffY)<-0.4){
                        swipe.y = 0;
                    }
                    else {
                        swipe.y = diffY;
                    }
                    break;
                default:
                    break;
            }
        }
        else if(!isPanning && pointerCount == 1) {
            switch (e.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    touchX = -1;
                    touchY = -1;
                    break;
                default:
                    touchX = (tx * Main.cameraWidth) / getWidth();
                    touchY = (ty * Main.cameraHeight) / getHeight();
                    break;
            }
        }
        return true;
    }
}
