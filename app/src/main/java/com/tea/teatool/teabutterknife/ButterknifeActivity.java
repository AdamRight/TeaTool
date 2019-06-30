package com.tea.teatool.teabutterknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.butterknife.annotations.BindView;
import com.tea.butterknife.TeaButterKnife;
import com.tea.butterknife.Unbinder;
import com.tea.teatool.R;

public class ButterknifeActivity extends AppCompatActivity {

    @BindView(R.id.tv1)
    TextView textView1;

    @BindView(R.id.tv2)
    TextView textView2;

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife);
        bind = TeaButterKnife.bind(this);
        textView1.setText("手写ButterKnife");
        textView2.setText("手写ButterKnife");
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }
}
