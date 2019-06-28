package com.tea.teatool.teahandler;


/**
 * Created by jiangtea on 2019/6/28.
 */

public class TeaActivityThread {
    final H mH = new H();

    public void attach(boolean b) {
        TeaActivity mainActivity = new TeaMainActivity();
        mainActivity.onCreate();

        // 通过 Handler 去执行Activity的生命周期
        TeaMessage message = new TeaMessage();
        message.obj = mainActivity;
        mH.sendMessage(message);
    }

    private class H extends TeaHandler {
        public void handleMessage(TeaMessage msg) {
            TeaActivity mainActivity = (TeaActivity) msg.obj;
            mainActivity.onResume();
        }
    }
}
