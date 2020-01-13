package com.tea.teatool.tearx;

/**
 * 观察者
 * @param <T>
 */
public interface Observer<T> {

    void onSubscribe();

    void onNext(T s);

    void onError(Throwable e);

    void onComplete();
}
