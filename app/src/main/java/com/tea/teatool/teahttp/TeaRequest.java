package com.tea.teatool.teahttp;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangtea on 2019/8/3.
 */
public class TeaRequest {

    final String url;
    public final Method method;
    final Map<String, String> headerMap;
    TeaRequestBody requestBody;

    public TeaRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headerMap = builder.headerMap;
        this.requestBody = builder.requestBody;
    }

    public TeaRequestBody requestBody() {
        return requestBody;
    }

    public String url(){
        return url;
    }

    public static Bindry create(final File file) {
        return new Bindry() {
            @Override
            public String fileName() {
                return file.getName();
            }

            @Override
            public long fileLength() {
                return file.length();
            }

            @Override
            public String mimType() {
                FileNameMap fileNameMap = URLConnection.getFileNameMap();
                String mimType = fileNameMap.getContentTypeFor(file.getAbsolutePath());
                if(TextUtils.isEmpty(mimType)){
                    return "application/octet-stream";
                }
                return mimType;
            }

            @Override
            public void onWrite(OutputStream outputStream) throws IOException {
                InputStream inputStream = new FileInputStream(file);
                byte[] buffer = new byte[2048];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                inputStream.close();
            }
        };
    }

    public void header(String key, String value) {
        headerMap.put(key, value);
    }

    public static class Builder {
        TeaRequestBody requestBody;
        String url;
        Method method;
        Map<String, String> headerMap;

        public Builder() {
            method = Method.GET;
            headerMap = new HashMap<>();
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder get() {
            method = Method.GET;
            return this;
        }

        public Builder post(TeaRequestBody body) {
            method = Method.POST;
            requestBody = body;
            return this;
        }

        public void header(String key, String value) {
            headerMap.put(key, value);
        }

        public TeaRequest build() {
            return new TeaRequest(this);
        }
    }

}
