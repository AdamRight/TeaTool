package com.tea.teatool.tearx;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public abstract class Schedulers {

    static Schedulers MAIN_THREAD;
    static Schedulers IO;

    static {
        IO = new IOSchedulers();
        MAIN_THREAD = new MainSchedulers(new Handler(Looper.getMainLooper()));
    }

    public static Schedulers io(){
        return IO;
    }

    public static Schedulers mainThread(){
        return MAIN_THREAD;
    }

    public abstract void scheduleDirect(Runnable runnable);

    private static class MainSchedulers extends Schedulers {

        private Handler handler;

        public MainSchedulers(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void scheduleDirect(Runnable runnable) {
            Message obtain = Message.obtain(handler, runnable);
            handler.sendMessage(obtain);
        }
    }

    private static class IOSchedulers extends Schedulers {

        private ExecutorService executorService;

        public IOSchedulers() {
            executorService = Executors.newScheduledThreadPool(1, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            });
        }

        @Override
        public void scheduleDirect(Runnable runnable) {
            executorService.execute(runnable);
        }
    }
}
