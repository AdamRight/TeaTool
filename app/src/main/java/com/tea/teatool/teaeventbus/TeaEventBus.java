package com.tea.teatool.teaeventbus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jiangtea on 2019/7/7.
 */
public class TeaEventBus {

    static volatile TeaEventBus defaultInstance;

    private final Map<Class<?>, CopyOnWriteArrayList<Subscription>> subscriptionsByEventType;

    private final Map<Object, List<Class<?>>> typesBySubscriber;

    public TeaEventBus() {
        typesBySubscriber = new HashMap<>();
        subscriptionsByEventType = new HashMap<>();
    }

    public static TeaEventBus getDefault() {
        if (defaultInstance == null) {
            synchronized (TeaEventBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new TeaEventBus();
                }
            }
        }
        return defaultInstance;
    }

    public void register(Object object){


    }
}
