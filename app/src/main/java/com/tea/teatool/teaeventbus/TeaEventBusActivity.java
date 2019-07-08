package com.tea.teatool.teaeventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tea.teatool.R;

public class TeaEventBusActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button button;
    private Integer i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_event_bus);
        TeaEventBus.getDefault().register(this);
        textView = (TextView) findViewById(R.id.tv_eb);
        button = findViewById(R.id.btn_eb);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TeaEventBus.getDefault().post(i++);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TeaEventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void teaBus(Integer i){
        textView.setText("手写实现EventBus" + i);
    }

}
