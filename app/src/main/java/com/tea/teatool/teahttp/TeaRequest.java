package com.tea.teatool.teahttp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangtea on 2019/8/3.
 */
public class TeaRequest {

    final String url;
    final Method method;
    final Map<String, String> headerMap;

    public TeaRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headerMap = builder.headerMap;
    }

    public static class Builder {
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
