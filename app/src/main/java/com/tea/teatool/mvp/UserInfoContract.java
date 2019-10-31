package com.tea.teatool.mvp;

import com.tea.teatool.mvp.base.BaseView;

/**
 * Created by jiangtea on 2019/10/29.
 */
public class UserInfoContract {
    interface UserInfoView extends BaseView {
        void onLoading();

        void onError();

        void onSuccess(String userInfo);
    }

    interface UserInfoPresenter {
        void getUsers(String token);
    }

    interface UserInfoModel {
        String getUsers(String token);
    }

}
