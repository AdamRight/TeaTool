package com.tea.teatool.tearx;

class LambdaObserver<T> implements Observer<T>{

    private Consumer<T> onNext;

    public LambdaObserver(Consumer<T> onNext) {
        this.onNext = onNext;
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onNext(T item) {
        try {
            onNext.accept(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
