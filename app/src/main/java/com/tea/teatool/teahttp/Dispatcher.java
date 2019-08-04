package com.tea.teatool.teahttp;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiangtea on 2019/8/3.
 */
public class Dispatcher {

    private ExecutorService executorService;

    public synchronized ExecutorService executorService(){
        if (executorService == null){
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    Thread teaHttp = new Thread(r, "TeaHttp");
                    teaHttp.setDaemon(false);
                    return teaHttp;
                }
            });
        }
        return executorService;
    }

    public void enqueue(RealCall.AsyncCall asyncCall) {
        executorService().execute(asyncCall);
    }
}
