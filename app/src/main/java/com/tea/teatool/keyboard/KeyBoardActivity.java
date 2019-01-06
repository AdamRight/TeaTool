package com.tea.teatool.keyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.teatool.R;

public class KeyBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board);
        EditText et = (EditText) findViewById(R.id.et);
        new KeyboardHelper(this, et);
    }

}
