package com.tea.teatool.teahandler;

/**
 * Created by jiangtea on 2019/6/28.
 */

public class TeaMainActivity extends TeaActivity {
    private TeaTv mTextView;

    private TeaHandler mHandler = new TeaHandler(){
        public void handleMessage(TeaMessage msg) {
            mTextView.setText((String)msg.obj);
            System.out.println("线程名称2："+Thread.currentThread());
        };
    };

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("onCreate 执行了");
        mTextView = findViewById(TeaR.id.text_view);


        new Thread(){
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程名称1："+Thread.currentThread());

                TeaMessage message = new TeaMessage();
                message.obj = "后台数据";
                mHandler.sendMessage(message);
            };
        }.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume 执行了");
    }
}
