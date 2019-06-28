package com.tea.teatool.loadingshape;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tea.teatool.R;

public class ShapeLoadActivity extends AppCompatActivity {

    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_load);
        loadingView = findViewById(R.id.loading);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingView.setVisibility(View.INVISIBLE);
    }
}
