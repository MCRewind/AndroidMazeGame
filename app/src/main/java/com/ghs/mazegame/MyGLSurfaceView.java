package com.ghs.mazegame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;


public class MyGLSurfaceView extends GLSurfaceView {

    private Renderer myRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context.
        //setEGLContextClientVersion(2);
        // Set the Renderer for drawing on the GLSurfaceView
        //myRenderer = new Renderer();
        //setRenderer(myRenderer);
        // Render the view only when there is a change in the drawing data

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.d("Touched",e.toString());
        return true;
    }

    //public void start() {
        //mMyRenderer = ...;
        //setRenderer(Renderer);
    //}


    public boolean onKeyDown(final int keyCode, KeyEvent event) {
        //if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            queueEvent(new Runnable() {
                // This method will be called on the rendering
                // thread:

                public void run() {
                    //mMyRenderer.handleDpadCenter();
                    Log.d("Keydown",KeyEvent.keyCodeToString(keyCode));
                }});


            //return true;
        //}
        return super.onKeyDown(keyCode, event);
    }

}
