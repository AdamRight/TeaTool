package com.tea.teatool.teahttp.interceptor;

import com.tea.teatool.teahttp.TeaRequest;
import com.tea.teatool.teahttp.TeaRequestBody;
import com.tea.teatool.teahttp.TeaResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jiangtea on 2019/8/5.
 */
public class CallServerInterceptor implements Interceptor {
    @Override
    public TeaResponse intercept(Chain chain) throws IOException {
        TeaRequest request = chain.request();
        URL url = new URL(request.url());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        if (urlConnection instanceof HttpsURLConnection) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlConnection;
            // https 的一些操作
            // httpsURLConnection.setHostnameVerifier();
            // httpsURLConnection.setSSLSocketFactory();
        }
        // urlConnection.setReadTimeout();

        urlConnection.setRequestMethod(request.method.name);
        urlConnection.setDoOutput(request.method.doOutput());
        urlConnection.connect();
        // 写内容
        TeaRequestBody requestBody = request.requestBody();
        if (requestBody != null) {
            requestBody.onWriteBody(urlConnection.getOutputStream());
        }

        int statusCode = urlConnection.getResponseCode();
        if (statusCode == 200) {
            InputStream inputStream = urlConnection.getInputStream();
            TeaResponse response = new TeaResponse(inputStream);
            return response;
        }
        // 进行一些列操作，状态码 200
        return null;
    }
}
