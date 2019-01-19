package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.general.mediaplayer.colgatetoothbruchcec.model.Global;

public class BaseActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static int FILTER_RESET_DELAY = 1000 * 2 * 60;
    private static Handler mFilterResetHandler = new Handler();
    private static Runnable mFilterResetRunnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (ContextCompat.checkSelfPermission(BaseActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (!ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this,
                    Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(BaseActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
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
                Global.isNew = false;
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
