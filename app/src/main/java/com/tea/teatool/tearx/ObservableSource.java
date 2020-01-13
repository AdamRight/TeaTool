package com.tea.teatool.tearx;

public interface ObservableSource<T> {
    void subscribe(Observer<T> observer);
}
