package com.ghs.mazegame.game;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.ghs.mazegame.engine.display.Surface;

public class LaunchActivity extends Activity {

    private Surface surface;
    private InputMethodManager imm;
    private StringBuilder input;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        surface = new Surface(this);
        surface.setEGLContextClientVersion(2);
        surface.setRenderer(new Main(surface.getResources(), this, this));
        surface.setPreserveEGLContextOnPause(true);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        setContentView(surface);

        fullScreenCall();

        input = new StringBuilder();
    }

    public void fullScreenCall() {
        if(Build.VERSION.SDK_INT < 19){
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
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

    public void showKeyboard() {
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void hideKeyboard() {
        imm.hideSoftInputFromWindow(surface.getWindowToken(), 0);
    }

    public String getInput() {
        return input.toString();
    }

    public void clear() {
        input.delete(0, input.length());
    }

    public void truncate(int length) {
        input.setLength(length);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        char c = (char) event.getUnicodeChar();
        if(c >= 32 && c <= 127)
            input.append(c);
        else if(keyCode == 67 && input.length() > 0)
            input.deleteCharAt(input.length() - 1);
        return true;
    }
}
