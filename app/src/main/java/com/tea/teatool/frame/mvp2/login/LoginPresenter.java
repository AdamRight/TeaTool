package com.tea.teatool.frame.mvp2.login;


import com.tea.teatool.frame.mvp2.Mvp2Activity;
import com.tea.teatool.frame.mvp2.base.BasePresenter;
import com.tea.teatool.frame.mvp2.bean.UserInfo;

public class LoginPresenter extends BasePresenter<Mvp2Activity, LoginMode, LoginContract.Presenter> {

    @Override
    public LoginContract.Presenter getContract() {
        // 既要履行View给它的需求，又要分配工作给Model去完成这个需求
        return new LoginContract.Presenter<UserInfo>() {
            @Override
            public void requestLogin(String name, String pwd) {
                try {
                    // 三种风格（P层很极端，要么不做事只做转发，要么就是拼命一个人干活）
                    m.getContract().executeLogin(name, pwd);

                    // 第二种，让功能模块去工作（Library：下载、请求、图片加载）
//                    HttpEngine engine = new HttpEngine<>(LoginPresenter.this);
//                    engine.post(name, pwd);

                    // P层自己处理（谷歌例子）
//                    if ("netease".equalsIgnoreCase(name) && "163".equals(pwd)) {
//                        responseResult(new UserInfo("网易", "彭老师"));
//                    } else {
//                        responseResult(null);
//                    }

                    // 内存泄露测试
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            SystemClock.sleep(50000);
//                        }
//                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(UserInfo userInfo) {
                // 不管谁完成需求，有结果就告知View层
                getView().getContract().handlerResult(userInfo);
            }
        };
    }

    @Override
    public LoginMode getModel() {
        return new LoginMode(this);
    }
}
