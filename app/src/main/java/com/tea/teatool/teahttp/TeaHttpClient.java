package com.tea.teatool.teahttp;

/**
 * Created by jiangtea on 2019/8/3.
 */
public class TeaHttpClient {

    final Dispatcher dispatcher;

    public TeaHttpClient() {
        this(new Builder());
    }

    private TeaHttpClient(Builder builder) {
        dispatcher = builder.dispatcher;
    }

    public Call newCall(TeaRequest request){
        return RealCall.newCall(request,this);
    }

    public static class Builder{
        Dispatcher dispatcher;

        public Builder(){
            dispatcher = new Dispatcher();
        }

        public TeaHttpClient build(){
            return new TeaHttpClient(this);
        }
    }
}
