package com.tea.teatool.tearx;

class ObserverOnObservable<T> extends TeaObservable<T> {

    final TeaObservable<T> source;
    final Schedulers schedulers;

    public ObserverOnObservable(TeaObservable<T> source, Schedulers schedulers) {
        this.source = source;
        this.schedulers = schedulers;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        source.subscribe(new ObserverOnObserver(observer,schedulers));
    }

    private class ObserverOnObserver implements Observer<T>, Runnable {

        private Observer<T> observer;
        private Schedulers schedulers;
        private T value;
        public ObserverOnObserver(Observer<T> observer, Schedulers schedulers) {
            this.observer = observer;
            this.schedulers = schedulers;
        }

        @Override
        public void onSubscribe() {
            observer.onSubscribe();
        }

        @Override
        public void onNext(T s) {
            value = s;
            schedulers.scheduleDirect(this);
        }

        @Override
        public void onError(Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }

        @Override
        public void run() {
            observer.onNext(value);
        }
    }
}
