package com.tea.teatool.thumbup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tea.teatool.R;

public class ThumbUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb_up);
        Button btn = findViewById(R.id.btn);
        final ThumbUpView thumbUp = findViewById(R.id.thumb_up);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thumbUp.addThumbUp();
            }
        });
    }
}
