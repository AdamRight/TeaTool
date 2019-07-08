
package com.tea.teatool.teaeventbus;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiangtea on 2019/7/7.
 */
class AsyncPoster implements Runnable {

    private Subscription subscription;
    private Object event;

    private final static ExecutorService executorService = Executors.newCachedThreadPool();

    public AsyncPoster(Subscription subscription, Object event){
        this.subscription = subscription;
        this.event = event;
    }

    public static void enqueue(Subscription subscription, Object event) {
        AsyncPoster asyncPoster = new AsyncPoster(subscription,event);
        executorService.execute(asyncPoster);
    }

    @Override
    public void run() {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber,event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
