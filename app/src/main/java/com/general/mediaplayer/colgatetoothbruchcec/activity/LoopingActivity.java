package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.general.mediaplayer.colgatetoothbruchcec.R;
import com.general.mediaplayer.colgatetoothbruchcec.model.Global;
import com.general.mediaplayer.colgatetoothbruchcec.model.ProductModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoopingActivity extends UsbSerialActivity implements View.OnClickListener{


    ScalableVideoView videoView;

    @BindView(R.id.button1)
    Button leftbottomBtn;

    @BindView(R.id.button2)
    Button rightbottomBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_looping);
        ButterKnife.bind(this);
        leftbottomBtn.setOnClickListener(this);
        rightbottomBtn.setOnClickListener(this);

        videoView =  findViewById(R.id.videoView);
        videoView.setDisplayMode(ScalableVideoView.DisplayMode.FULL_SCREEN);
        videoView.setVisibility(View.VISIBLE);
        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.looping_video));
        videoView.requestFocus();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    @Override
    protected void onResume(){

        super.onResume();

        videoView.start();

    }

    public void onFinish(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private long mLastClickTIme = 0;

    @Override
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTIme < 500)
        {
            // show CSR app
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.general.mediaplayer.csr");
            if (launchIntent != null) {
                startActivity(launchIntent);//null pointer check in case package name was not found
            }

            return;
        }

        mLastClickTIme = SystemClock.elapsedRealtime();
    }
}