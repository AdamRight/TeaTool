package com.tea.teatool.mvp;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tea.teatool.R;
import com.tea.teatool.mvp.base.BaseMvpActivity;

public class MvpActivity extends BaseMvpActivity<UserInfoPresenter> implements UserInfoContract.UserInfoView, View.OnClickListener {

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Button mvpBtn = findViewById(R.id.mvp_btn);
        mvpBtn.setOnClickListener(this);
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_mvp);
    }

    @Override
    public void onLoading() {
        Toast.makeText(this,"onLoading",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess(String userInfo) {
        Toast.makeText(this,"onSuccess",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        getPresenter().getUsers("从View层传递");
    }
}
