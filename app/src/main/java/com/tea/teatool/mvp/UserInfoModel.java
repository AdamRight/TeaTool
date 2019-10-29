package com.tea.teatool.mvp;

/**
 * Created by jiangtea on 2019/10/29.
 */
public class UserInfoModel implements UserInfoContract.UserInfoModel {
    @Override
    public String getUsers(String token) {
        return "M层通过" + token + "获取数据";
    }
}
