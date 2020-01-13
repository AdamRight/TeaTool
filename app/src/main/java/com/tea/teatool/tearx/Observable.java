package com.tea.teatool.tearx;

/**
 * 被观察者
 *
 * @param <T>
 */
public abstract class Observable<T> implements ObservableSource<T> {

    public static <T> Observable<T> just(T item) {
        return onAssembly(new ObservableJust<T>(item));
    }

    public <R> Observable<R> map(Function<T, R> function) {
        return onAssembly(new ObservableMap<>(this, function));
    }

    private static <T> Observable<T> onAssembly(Observable<T> observable) {
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
