package com.tea.teatool.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jiangtea on 2019/10/31.
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{

    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        presenter = createPresenter();
        presenter.attachView(this);
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract P createPresenter();

    protected abstract void setContentView();

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
