package com.tea.teatool.mvp;

import com.tea.teatool.mvp.base.BasePresenter;

/**
 * Created by jiangtea on 2019/10/29.
 */
public class UserInfoPresenter extends BasePresenter<UserInfoContract.UserInfoView> implements UserInfoContract.UserInfoPresenter {

    private UserInfoContract.UserInfoModel model;

    public UserInfoPresenter() {
        model = new UserInfoModel();
    }

    @Override
    public void getUsers(String token) {
        getView().onLoading();
        String result = model.getUsers(token);
        getView().onSuccess(result);
    }
}
