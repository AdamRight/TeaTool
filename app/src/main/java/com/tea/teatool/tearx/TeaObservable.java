package com.tea.teatool.tearx;

/**
 * 被观察者
 *
 * @param <T>
 */
public abstract class TeaObservable<T> implements ObservableSource<T> {

    public static <T> TeaObservable<T> just(T item) {
        return onAssembly(new ObservableJust<T>(item));
    }

    public <R> TeaObservable<R> map(Function<T, R> function) {
        return onAssembly(new ObservableMap<>(this, function));
    }

    public TeaObservable<T> subscribeOn(Schedulers schedulers){
        return onAssembly(new ObservableSchedulers(this,schedulers));
    }

    public TeaObservable<T> observeOn(Schedulers schedulers){
        return onAssembly(new ObserverOnObservable(this,schedulers));
    }

    private static <T> TeaObservable<T> onAssembly(TeaObservable<T> observable) {
        return observable;
    }

    public void subscribe(Consumer<T> onNext) {
        subscribe(onNext, null, null);
    }

    private void subscribe(Consumer<T> onNext, Consumer<T> error, Consumer<T> complete) {
        subscribe(new LambdaObserver<T>(onNext));
    }

    @Override
    public void subscribe(Observer<T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<T> observer);

}
