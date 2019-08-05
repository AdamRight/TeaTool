package com.tea.teatool.teahttp.interceptor;
import com.tea.teatool.teahttp.TeaRequest;
import com.tea.teatool.teahttp.TeaResponse;

import java.io.IOException;

/**
 * Created by jiangtea on 2019/8/4.
 */

public interface Interceptor {
    TeaResponse intercept(Chain chain) throws IOException;
    interface Chain {
        TeaRequest request();

        TeaResponse proceed(TeaRequest request) throws IOException;
    }
}
