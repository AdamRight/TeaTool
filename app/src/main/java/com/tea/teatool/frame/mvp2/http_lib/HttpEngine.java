package com.tea.teatool.frame.mvp2.http_lib;

import com.tea.teatool.frame.mvp2.bean.UserInfo;
import com.tea.teatool.frame.mvp2.login.LoginPresenter;

// 有可能是共有的Model
public class HttpEngine<P extends LoginPresenter> {

    private P p;

    public HttpEngine(P p) {
        this.p = p;
    }

    public void post(String name, String pwd) {
        if ("netease".equalsIgnoreCase(name) && "163".equals(pwd)) {
            p.getContract().responseResult(new UserInfo("网易", "彭老师"));
        } else {
            p.getContract().responseResult(null);
        }
    }
}
