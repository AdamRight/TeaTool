package com.tea.teatool.teahandler;


/**
 * Created by jiangtea on 2019/6/26.
 */

public class TeaLooper {

    static final ThreadLocal<TeaLooper> sThreadLocal = new ThreadLocal<>();
    public TeaMessageQueue mQueue;

    public TeaLooper() {
        mQueue = new TeaMessageQueue();
    }

    public static void prepare() {
        sThreadLocal.set(new TeaLooper());
    }

    public static void loop() {
        TeaLooper looper = myLooper();
        for (;;){
            final TeaMessageQueue queue = looper.mQueue;
            TeaMessage message = queue.next();
            message.target.handleMessage(message);
        }
    }

    public static TeaLooper myLooper() {

        return sThreadLocal.get();
    }
}
