package com.tea.teatool.teahttp.interceptor;

import com.tea.teatool.teahttp.TeaRequest;
import com.tea.teatool.teahttp.TeaRequestBody;
import com.tea.teatool.teahttp.TeaResponse;

import java.io.IOException;

/**
 * Created by jiangtea on 2019/8/4.
 */
public class BridgeInterceptor implements Interceptor{

    @Override
    public TeaResponse intercept(Chain chain) throws IOException {
        TeaRequest request = chain.request();
        request.header("Connection","keep-alive");
        if(request.requestBody() != null){
            TeaRequestBody requestBody = request.requestBody();
            request.header("Content-Type",requestBody.getContentType());
            request.header("Content-Length",Long.toString(requestBody.getContentLength()));
        }
        TeaResponse response = chain.proceed(request);
        return response;
    }
}
