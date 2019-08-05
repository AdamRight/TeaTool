package com.tea.teatool.teahttp.interceptor;

import com.tea.teatool.teahttp.TeaRequest;
import com.tea.teatool.teahttp.TeaResponse;

import java.io.IOException;

/**
 * Created by jiangtea on 2019/8/5.
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public TeaResponse intercept(Chain chain) throws IOException {
        TeaRequest request = chain.request();
        // 本地有没有缓存，如果有没过期
        return chain.proceed(request);
    }
}
