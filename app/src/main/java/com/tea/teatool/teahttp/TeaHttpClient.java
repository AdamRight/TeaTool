package com.tea.teatool.teahttp;

/**
 * Created by jiangtea on 2019/8/3.
 */
public class TeaHttpClient {

    public Call newCall(TeaRequest request){
        return RealCall.newCall(request,this);
    }
}
