package com.tea.teatool.iocannotate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tea.teatool.R;

public class IOCAnnotateActivity extends AppCompatActivity {

    @IdByView(R.id.annotate_tv)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iocannotate);
        FinderUtils.inject(this);
        textView.setText("成功注解，可以点击我试试哦");
    }

    @IdOnClick(R.id.annotate_tv)
    @CheckNet
    private void textClick(TextView tv){
        tv.setText("点击注解成功啦！！");
    }
}
