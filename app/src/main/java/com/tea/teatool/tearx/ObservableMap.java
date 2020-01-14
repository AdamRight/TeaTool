package com.tea.teatool.tearx;

class ObservableMap<T, R> extends TeaObservable<R> {

    final TeaObservable<T> source;
    final Function<T, R> function;

    public ObservableMap(TeaObservable<T> source, Function<T, R> function) {
        this.source = source;
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<R> observer) {
        source.subscribe(new MapObserver(observer, function));
    }

    private class MapObserver<T> implements Observer<T> {

        final Observer<R> observer;
        final Function<T, R> function;

        public MapObserver(Observer<R> observer, Function<T, R> function) {
            this.observer = observer;
            this.function = function;
        }

        @Override
        public void onSubscribe() {
            observer.onSubscribe();
        }

        @Override
        public void onNext(T s) {
            try {
                R apply = function.apply(s);
                observer.onNext(apply);
            } catch (Exception e) {
                observer.onError(e);
            }
        }

        @Override
        public void onError(Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }
}
