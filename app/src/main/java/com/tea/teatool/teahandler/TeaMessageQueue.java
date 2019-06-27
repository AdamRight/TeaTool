package com.tea.teatool.teahandler;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by jiangtea on 2019/6/27.
 */

class TeaMessageQueue {

    private TeaMessage mMessages;

    public boolean enqueueMessage(TeaMessage msg, long when) {
        synchronized (this) {
            msg.when = when;
            TeaMessage p = mMessages;
            if (p == null || when == 0 || when < p.when) {
                msg.next = p;
                mMessages = msg;
            } else {
                TeaMessage prev;
                for (; ; ) {
                    prev = p;
                    p = p.next;
                    if (p == null || when < p.when) {
                        break;
                    }
                }
                msg.next = p;
                prev.next = msg;
            }
        }
        return true;

    }

    public TeaMessage next() {
        int pendingIdleHandlerCount = 1;
        for (; ; ) {
            synchronized (this) {
                Log.d("tea","tea:" +"next");
                final long now = SystemClock.uptimeMillis();
                TeaMessage prevMsg = null;
                TeaMessage msg = mMessages;
                if (msg != null && msg.target == null) {
                    do {
                        prevMsg = msg;
                        msg = msg.next;
                    } while (msg != null);
                }
                if (msg != null) {
                    if (now < msg.when) {
                    } else {
                        if (prevMsg != null) {
                            prevMsg.next = msg.next;
                        } else {
                            mMessages = msg.next;
                        }
                        msg.next = null;
                        return msg;
                    }
                } else {
                }

                if (pendingIdleHandlerCount <= 0) {
                    continue;
                }
            }
        }
    }
}
