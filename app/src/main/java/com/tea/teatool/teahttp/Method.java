package com.tea.teatool.teahttp;

/**
 * Created by jiangtea on 2019/8/3.
 */
public enum  Method {

    POST("POST"),GET("GET");

    public String name;

    Method(String name){
        this.name = name;
    }

    public boolean doOutput() {
        switch (this){
            case POST:
                return true;
        }
        return false;
    }
}
