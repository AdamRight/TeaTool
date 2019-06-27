package com.tea.teatool.teahandler;

/**
 * Created by jiangtea on 2019/6/26.
 */

public class TeaHandler {

    TeaMessageQueue mQueue;

    public TeaHandler() {
        TeaLooper looper = TeaLooper.myLooper();
        if (looper == null){
            throw new RuntimeException(
                    "Can't create handler inside thread that has not called Looper.prepare()");
        }
        mQueue = looper.mQueue;
    }

    public void sendMessage(TeaMessage message) {
        sendMessageDelayed(message, 0);
    }

    public final boolean sendMessageDelayed(TeaMessage msg, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, delayMillis);
    }

    public boolean sendMessageAtTime(TeaMessage msg, long uptimeMillis) {
        TeaMessageQueue queue = mQueue;
        return enqueueMessage(queue, msg, uptimeMillis);
    }

    private boolean enqueueMessage(TeaMessageQueue queue, TeaMessage msg, long uptimeMillis) {
        msg.target = this;
        return queue.enqueueMessage(msg, uptimeMillis);
    }


    public void handleMessage(TeaMessage msg) {

    }
}
