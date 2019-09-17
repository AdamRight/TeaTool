package com.tea.teatool.iocannotate;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jiangtea on 2019/9/17.
 */
public class FinderUtils {

    public static void inject(Activity activity){
        inject(new FinderView(activity),activity);
    }

    private static void inject(FinderView finderView, Object object) {
        injectFiled(finderView,object);
        injectEvent(finderView,object);
    }

    /**
     * 注入属性
     * @param finderView
     * @param object
     */
    private static void injectFiled(FinderView finderView, Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            IdByView annotation = field.getAnnotation(IdByView.class);
            if (annotation != null){
                int valueId = annotation.value();
                View view = finderView.findViewById(valueId);
                if (view != null){
                    field.setAccessible(true);
                    try {
                        field.set(object,view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 事件注入
     * @param finderView
     * @param object
     */
    private static void injectEvent(FinderView finderView, Object object) {
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            IdOnClick annotation = method.getAnnotation(IdOnClick.class);
            if (annotation != null){
                int[] values = annotation.value();
                for (int value : values) {
                    View view = finderView.findViewById(value);
                    boolean checkNet = method.getAnnotation(CheckNet.class) != null;
                    if (view != null){
                        view.setOnClickListener(new DeclareOnClickListener(method,object,checkNet));
                    }
                }
            }
        }
    }

    private static class DeclareOnClickListener implements View.OnClickListener{
        private Object object;
        private Method method;
        private boolean isCheckNet;

        public DeclareOnClickListener(Method method, Object object, boolean checkNet) {
            this.method = method;
            this.object = object;
            this.isCheckNet = checkNet;

        }

        @Override
        public void onClick(View v) {
            if (isCheckNet){
                if (networkAvailable(v.getContext())){
                    Toast.makeText(v.getContext(),"网络不给力",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            try {
                method.setAccessible(true);
                method.invoke(object,v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    method.invoke(object,null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断当前网络是否可用
     */
    private static boolean networkAvailable(Context context) {
        // 得到连接管理器对象
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
