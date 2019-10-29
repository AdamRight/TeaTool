package com.tea.teatool.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tea.teatool.R;

public class MvpActivity extends AppCompatActivity implements UserInfoContract.UserInfoView, View.OnClickListener {

    private UserInfoContract.UserInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        Button mvpBtn = findViewById(R.id.mvp_btn);
        mvpBtn.setOnClickListener(this);

        presenter = new UserInfoPresenter(this);

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
        presenter.getUsers("从View层传递");
    }
}
