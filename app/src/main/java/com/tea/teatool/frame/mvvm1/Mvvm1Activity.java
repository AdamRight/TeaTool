package com.tea.teatool.frame.mvvm1;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tea.teatool.R;
import com.tea.teatool.databinding.ActivityMvvm1Binding;
import com.tea.teatool.frame.mvvm1.vm.LoginViewModel;

public class Mvvm1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1、必须先ReBuilder，2、书写代码绑定
        ActivityMvvm1Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm1);
        new LoginViewModel(binding);
    }
}
