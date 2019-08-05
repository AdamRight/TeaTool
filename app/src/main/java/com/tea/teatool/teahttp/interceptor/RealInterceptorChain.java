package com.tea.teatool.teahttp.interceptor;

import com.tea.teatool.teahttp.TeaRequest;
import com.tea.teatool.teahttp.TeaResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by xiaojiang on 2019/8/5.
 */

public class RealInterceptorChain implements Interceptor.Chain {
    final List<Interceptor> interceptors;
    final int index;
    final TeaRequest request;

    public RealInterceptorChain(List<Interceptor> interceptors, int index, TeaRequest request){
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
    }

    @Override
    public TeaRequest request() {
        return request;
    }


    @Override
    public TeaResponse proceed(TeaRequest request) throws IOException {
        RealInterceptorChain next = new RealInterceptorChain(interceptors,  index + 1, request);
        Interceptor interceptor = interceptors.get(index);
        TeaResponse response = interceptor.intercept(next);
        return response;
    }
}
