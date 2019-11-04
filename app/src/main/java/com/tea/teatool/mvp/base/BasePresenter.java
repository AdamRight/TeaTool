package com.tea.teatool.mvp.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by jiangtea on 2019/10/31.
 */
public class BasePresenter<V extends BaseView> {

    private V viewWeak;
    private V proxyView;

    public void attachView(final V view) {
        this.viewWeak = view;
        proxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (viewWeak == null) {
                    return null;
                }
                return method.invoke(viewWeak, args);
            }
        });
    }

    public void detachView() {
        this.viewWeak = null;
    }

    public V getView() {
        return proxyView;
    }
}
