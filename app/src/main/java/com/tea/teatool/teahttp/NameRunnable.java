package com.tea.teatool.teahttp;

/**
 * Created by jiangtea on 2019/8/4.
 */
public abstract class NameRunnable implements Runnable{
    @Override
    public void run() {
        execute();
    }

    protected abstract void execute();

}
