package com.tea.teatool.tearx;

class ObservableJust<T> extends Observable{

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

    public class ScalarDisposable{

        private Observer observer;
        private T item;

        public ScalarDisposable(Observer observer, T item) {
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
