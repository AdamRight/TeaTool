package com.tea.teatool.teahandler;


/**
 * Created by jiangtea on 2019/6/28.
 */

public class Client {
    public static void main(String[] args) {
        TeaLooper.prepare();

        TeaActivityThread thread = new TeaActivityThread();
        thread.attach(false);

        TeaLooper.loop();
    }
}
