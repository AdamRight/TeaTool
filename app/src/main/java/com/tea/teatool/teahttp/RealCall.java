package com.tea.teatool.teahttp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jiangtea on 2019/8/3.
 */
public class RealCall implements Call {

    private final TeaRequest originalRequest;

    private final TeaHttpClient client;

    public RealCall(TeaRequest request, TeaHttpClient teaHttpClient) {
        originalRequest = request;
        client = teaHttpClient;
    }

    public static Call newCall(TeaRequest request, TeaHttpClient teaHttpClient) {
        return new RealCall(request, teaHttpClient);
    }

    @Override
    public void enqueue(Callback callback) {
        AsyncCall asyncCall = new AsyncCall(callback);
        client.dispatcher.enqueue(asyncCall);
    }

    @Override
    public TeaResponse execute() {
        return null;
    }

    final class AsyncCall extends NameRunnable {

        Callback callback;

        public AsyncCall(Callback callback) {
            this.callback = callback;
        }

        @Override
        protected void execute() {

            final TeaRequest request = originalRequest;
            try {
                URL url = new URL(request.url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                if (urlConnection instanceof HttpsURLConnection) {
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlConnection;
                    //httpsURLConnection.setHostnameVerifier();
                    //httpsURLConnection.setSSLSocketFactory();
                }
//                urlConnection.setReadTimeout();

                urlConnection.setRequestMethod(request.method.name);
                urlConnection.setDoOutput(request.method.doOutput());

                TeaRequestBody requestBody = request.requestBody;
                if(requestBody != null){
                    // 头信息
                    urlConnection.setRequestProperty("Content-Type",requestBody.getContentType());
                    urlConnection.setRequestProperty("Content-Length",Long.toString(requestBody.getContentLength()));
                }

                urlConnection.connect();

                if(requestBody != null){
                    requestBody.onWriteBody(urlConnection.getOutputStream());
                }

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    InputStream inputStream = urlConnection.getInputStream();
                    TeaResponse response = new TeaResponse(inputStream);
                    callback.onResponse(RealCall.this,response);
                }


            } catch (IOException e) {
                callback.onFailure(RealCall.this, e);
            }
        }
    }


}
