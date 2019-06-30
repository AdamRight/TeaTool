package com.tea.butterknife;

import android.support.annotation.UiThread;

/**
 * Created by jiangtea on 2019/6/30.
 */
public interface Unbinder {
    @UiThread
    void unbind();

    Unbinder EMPTY = new Unbinder() {
        @Override
        public void unbind() {
        }
    };
}
