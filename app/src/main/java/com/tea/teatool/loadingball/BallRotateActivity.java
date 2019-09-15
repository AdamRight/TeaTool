package com.tea.teatool.loadingball;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tea.teatool.R;

public class BallRotateActivity extends AppCompatActivity {

    private BallRotateView ballRotateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_rotate);
        ballRotateView = findViewById(R.id.ball_loading);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ballRotateView.disappear();
            }
        },3000);
    }
}
