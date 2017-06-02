package com.ghs.mazegame.engine.display;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.game.Main;

public class Surface extends GLSurfaceView {

    public static float touchX = -1;
    public static float touchY = -1;
    public static Vector3f swipe = new Vector3f();

    float globalTouchPositionX = 0;
    float globalTouchCurrentPositionX = 0;
    float globalTouchPositionY = 0;
    float globalTouchCurrentPositionY = 0;

    private boolean isPanning = false;

    public Surface(Context context) {
        super(context);
    }

    public boolean onTouchEvent(MotionEvent e) {
        int pointerCount = e.getPointerCount();

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
                    globalTouchPositionX = (((tx2+tx)/(float)2) * (float) Main.cameraWidth) / (float)getWidth();
                    globalTouchPositionY = (((ty2+ty)/(float)2) * (float) Main.cameraHeight) / (float)getHeight();
                    isPanning = true;
                    touchX = -1;
                    touchY = -1;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP:
                    globalTouchCurrentPositionX = 0f;
                    globalTouchCurrentPositionY = 0f;
                    touchX = -1;
                    touchY = -1;
                    swipe.x = 0;
                    swipe.y = 0;
                    isPanning = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    isPanning = true;
                    touchX = -1;
                    touchY = -1;
                    globalTouchCurrentPositionX = (((tx2+tx)/(float)2) * (float) Main.cameraWidth) / (float)getWidth();
                    globalTouchCurrentPositionY = (((ty2+ty)/(float)2) * (float) Main.cameraHeight) / (float)getHeight();

                    float diffX = globalTouchPositionX-globalTouchCurrentPositionX;
                    float diffY = globalTouchPositionY- globalTouchCurrentPositionY;
                    globalTouchPositionX = (((tx2+tx)/(float)2) * (float) Main.cameraWidth) / (float)getWidth();
                    globalTouchPositionY = (((ty2+ty)/(float)2) * (float) Main.cameraHeight) / (float)getHeight();
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
            }
        }
        else if(!isPanning && pointerCount == 1) {
            switch (e.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
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

    public boolean onKeyUp(int keyCode, KeyEvent event) {

        return true;
    }
}
