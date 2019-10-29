package com.tea.teatool.mvp;

/**
 * Created by jiangtea on 2019/10/29.
 */
public class UserInfoPresenter implements UserInfoContract.UserInfoPresenter{

    private UserInfoContract.UserInfoView view;
    private UserInfoContract.UserInfoModel model;

    public UserInfoPresenter(UserInfoContract.UserInfoView view) {
        this.view = view;
        model = new UserInfoModel();
    }

    @Override
    public void getUsers(String token) {
        view.onLoading();
        String result = model.getUsers(token);
        view.onSuccess(result);
    }
}
