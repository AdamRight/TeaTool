package com.tea.teatool.letterindex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tea.teatool.R;

public class LetterIndexActivity extends AppCompatActivity implements TeaLetterIndex.LetterSelectedListener {

    private TextView tvShowLetter;
    private TeaLetterIndex letterIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_index);
        tvShowLetter = findViewById(R.id.tv_show_letter);
        letterIndex = findViewById(R.id.letter_index);
        letterIndex.setOnLetterSelectedListener(this);
    }

    @Override
    public void selected(String letter) {
        tvShowLetter.setText(letter);
        tvShowLetter.setVisibility(TextUtils.isEmpty(letter) ? View.GONE : View.VISIBLE);
    }
}
