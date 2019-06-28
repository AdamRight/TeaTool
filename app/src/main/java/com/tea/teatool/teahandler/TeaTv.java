package com.tea.teatool.teahandler;

/**
 * Created by jiangtea on 2019/6/28.
 */

public class TeaTv {

    private Thread mThread;
    public TeaTv(){
        mThread = Thread.currentThread();
    }

    public void setText(CharSequence text){
        checkThread();

        System.out.println("更新UI成功："+text);
    }

    void checkThread() {
        if (mThread != Thread.currentThread()) {
            throw new RuntimeException(
                    "Only the original thread that created a view hierarchy can touch its views.");
        }
    }
}
