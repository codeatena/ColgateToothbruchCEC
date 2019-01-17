package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.general.mediaplayer.colgatetoothbruchcec.model.Global;

public class BaseActivity extends AppCompatActivity {


    public static int FILTER_RESET_DELAY = 1000 * 2 * 60;
    private static Handler mFilterResetHandler = new Handler();
    private static Runnable mFilterResetRunnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void stopResetTimer() {
        if (mFilterResetRunnable != null) {
            mFilterResetHandler.removeCallbacks(mFilterResetRunnable);
        }
    }

    public void setupFilterResetTimer() {
        stopResetTimer();

        mFilterResetRunnable = new Runnable() {
            @Override
            public void run() {
                Global.isBestClean = false;
                Global.isWhiterSmile = false;
                Global.isSpeciality = false;
                Global.isExtraSoft = false;
                Global.isSoft = false;
                Global.isMedium = false;
                Global.isColgate = false;
                Global.isOral = false;
                Intent intent = new Intent(BaseActivity.this, LoopingActivity.class);
                startActivity(intent);
            }
        };

        mFilterResetHandler.postDelayed(mFilterResetRunnable, FILTER_RESET_DELAY);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        setupFilterResetTimer();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupFilterResetTimer();
    }

    @Override
    protected void onPause() {
        stopResetTimer();
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
    }

}
