package com.tea.teatool.teabutterknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tea.teatool.R;

import com.butterknife.annotations.BindView;

public class ButterknifeActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife);

        String d = "dfsafdfsafddfsafdfsafasfdsafasdfsfafdfddfsdfsafasfdsafafssafsafsafsafafaasdfa";
        System.out.println("------------------df------------------>" + d);
//        textView.setText(d);
    }
}
