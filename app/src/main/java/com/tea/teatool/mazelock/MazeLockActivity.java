package com.tea.teatool.mazelock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.tea.teatool.R;

public class MazeLockActivity extends AppCompatActivity implements MazeLockView.IndexListener {

    private MazeLockView mazeLockView;

    private String lock = "";
    private String currentLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze_lock);
        mazeLockView = findViewById(R.id.mlv);
        mazeLockView.setOnIndexListener(this);
    }

    @Override
    public void setIndex(int[] index) {
        currentLock = "";
        for (int i = 0; i < index.length; i++) {
            currentLock += index[i];
        }

        mazeLockView.setErrorOrNormal((!TextUtils.isEmpty(lock) && !TextUtils.equals(lock, currentLock)) ? 2 : 1);
        lock = currentLock;
        Toast.makeText(this, currentLock, Toast.LENGTH_SHORT).show();
    }
}
