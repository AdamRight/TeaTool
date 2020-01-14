package com.tea.teatool.tearx;

class ObservableJust<T> extends TeaObservable {

    private T item;

    public ObservableJust(T item) {
        this.item = item;
    }

    @Override
    protected void subscribeActual(Observer observer) {
        ScalarDisposable scalarDisposable = new ScalarDisposable(observer, item);
        observer.onSubscribe();
        scalarDisposable.run();
    }

    public class ScalarDisposable<T>{

        private Observer<T> observer;
        private T item;

        public ScalarDisposable(Observer<T> observer, T item) {
            this.observer = observer;
            this.item = item;
        }

        public void run(){
            try {
                observer.onNext(item);
                observer.onComplete();
            } catch (Exception e){
                observer.onError(e);
            }
        }
    }
}
