package com.tea.teatool.teahttp;

/**
 * Created by jiangtea on 2019/8/3.
 */
public interface Call {
    void enqueue(Callback callback);

    TeaResponse execute();
}
