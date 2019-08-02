package com.tea.teatool.circleload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tea.teatool.R;

public class CircleLoadingActivity extends AppCompatActivity {

    private CircleLoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_loading);

        loadingView = findViewById(R.id.load_view);
    }

    @Override
    protected void onDestroy() {
        loadingView.loadingStop();
        super.onDestroy();
    }
}
