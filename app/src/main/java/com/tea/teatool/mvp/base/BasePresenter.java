package com.tea.teatool.mvp.base;

/**
 * Created by jiangtea on 2019/10/31.
 */
public class BasePresenter<V extends BaseView> {
    private V view;

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }

    public V getView() {
        return view;
    }
}
