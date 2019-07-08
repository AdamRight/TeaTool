package com.tea.teatool.teaeventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Method;
import java.util.ArrayList;
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

    public void register(Object object) {
        List<SubscriberMethod> subscriberMethods = new ArrayList<>();
        Class<?> objectClass = object.getClass();
        Method[] methods = objectClass.getDeclaredMethods();
        for (Method method : methods) {
            Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if (subscribe != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                SubscriberMethod subscriberMethod = new SubscriberMethod(method, parameterTypes[0], subscribe.threadMode(), subscribe.priority(), subscribe.sticky());
                subscriberMethods.add(subscriberMethod);
            }
        }

        for (SubscriberMethod subscriberMethod : subscriberMethods) {
            //存放到 subscriptionsByEventType 里面去
            subscriber(object, subscriberMethod);
        }
    }

    private void subscriber(Object object, SubscriberMethod subscriberMethod) {
        Class<?> eventType = subscriberMethod.eventType;

        CopyOnWriteArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
        if (subscriptions == null) {
            subscriptions = new CopyOnWriteArrayList<>();
            subscriptionsByEventType.put(eventType, subscriptions);
        }
        // 判断优先级 （不写）
        Subscription subscription = new Subscription(object, subscriberMethod);
        subscriptions.add(subscription);

        // typesBySubscriber为了方便移除
        List<Class<?>> eventTypes = typesBySubscriber.get(object);
        if (eventTypes == null) {
            eventTypes = new ArrayList<>();
            typesBySubscriber.put(object, eventTypes);
        }
        if (!eventTypes.contains(eventType)) {
            eventTypes.add(eventType);
        }
    }

    public void unregister(Object object) {
        List<Class<?>> eventTypes = typesBySubscriber.get(object);
        if (eventTypes != null) {
            for (Class<?> eventType : eventTypes) {
                removeObject(eventType, object);
            }
        }
    }

    private void removeObject(Class<?> eventType, Object object) {
        List<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
        if (subscriptions != null) {
            int size = subscriptions.size();
            for (int i = 0; i < size; i++) {
                Subscription subscription = subscriptions.get(i);
                if (subscription.subscriber == object) {
                    subscriptions.remove(i);
                    i--;
                    size--;
                }
            }
        }
    }

    public void post(Object event) {
        Class<?> eventType = event.getClass();
        CopyOnWriteArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
        if (subscriptions != null) {
            for (Subscription subscription : subscriptions) {
                executeMethod(subscription, event);
            }
        }
    }

    private void executeMethod(final Subscription subscription, final Object event) {
        ThreadMode threadMode = subscription.subscriberMethod.threadMode;
        boolean isMainThread = Looper.getMainLooper() == Looper.myLooper();
        switch (threadMode) {
            case POSTING:
                invokeMethod(subscription, event);
                break;
            case MAIN:
                if (isMainThread) {
                    invokeMethod(subscription, event);
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            invokeMethod(subscription, event);
                        }
                    });
                }
                break;
            case ASYNC:
                AsyncPoster.enqueue(subscription, event);
                break;
            case BACKGROUND:
                if (!isMainThread) {
                    invokeMethod(subscription, event);
                } else {
                    AsyncPoster.enqueue(subscription, event);
                }
                break;
        }
    }

    private void invokeMethod(Subscription subscription, Object event) {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
