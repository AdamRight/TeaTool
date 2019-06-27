package com.tea.teatool.teahandler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tea.teatool.R;

public class TeaHandlerActivity extends AppCompatActivity {

    private TeaHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_handler);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*new Thread(new Runnable() {
            @Override
            public void run() {

                TeaLooper.prepare();
                handler = new TeaHandler(){
                    @Override
                    public void handleMessage(TeaMessage msg) {
                        super.handleMessage(msg);
                        Log.d("tea","tea:" +(String) msg.obj);
                    }
                };

                TeaMessage message = new TeaMessage();
                message.obj = "发送消息tea";
                handler.sendMessage(message);

                TeaLooper.loop();

            }
        }).start();*/

    }
}
