package com.tea.teatool.tearx;

final class ObservableSchedulers<T> extends TeaObservable<T> {

    final TeaObservable<T> source;
    final Schedulers schedulers;

    public ObservableSchedulers(TeaObservable<T> source, Schedulers schedulers) {
        this.source = source;
        this.schedulers = schedulers;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        schedulers.scheduleDirect(new SchedulerTask(observer));
    }

    private class SchedulerTask implements Runnable{

        final Observer<T> observer;
        public SchedulerTask(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void run() {
            source.subscribe(observer);
        }
    }
}
