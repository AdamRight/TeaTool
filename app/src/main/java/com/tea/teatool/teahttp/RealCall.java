package com.tea.teatool.teahttp;

/**
 * Created by jiangtea on 2019/8/3.
 */
public class RealCall implements Call{

    public RealCall(TeaRequest request, TeaHttpClient teaHttpClient) {

    }

    public static Call newCall(TeaRequest request, TeaHttpClient teaHttpClient) {
        return new RealCall(request,teaHttpClient);
    }
}
