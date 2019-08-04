package com.tea.teatool.teahttp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jiangtea on 2019/8/3.
 */
public class TeaRequestBody {

    public static final String FORM = "multipart/form-data";

    String type;

    private Map<String, Object> params;

    private String boundary = createBoundary();
    private String startBoundary = "--" + boundary;
    private String endBoundary = startBoundary + "--";

    public TeaRequestBody() {
        params = new HashMap<>();
    }

    private String createBoundary() {
        return "OkHttp" + UUID.randomUUID().toString();
    }

    public String getContentType() {
        return type + ";boundary = " + boundary;
    }

    public long getContentLength() {
        long length = 0;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                String postTextStr = getText(key, (String) value);
                length += postTextStr.getBytes().length;
            }

            if (value instanceof Bindry) {
                Bindry bindry = (Bindry) value;
                String postTextStr = getText(key, bindry);
                length += postTextStr.getBytes().length;
                length += bindry.fileLength() + "\r\n".getBytes().length;
            }
        }

        if (params.size() != 0) {
            length += endBoundary.getBytes().length;
        }
        return length;
    }

    private String getText(String key, String value) {
        return startBoundary + "\r\n" +
                "Content-Disposition: form-data; name = \"" + key + "\"\r\n" +
                "Context-Type: text/plain\r\n" +
                "\r\n" +
                value +
                "\r\n";
    }

    private String getText(String key, Bindry value) {
        return startBoundary+"\r\n"+
                "Content-Disposition: form-data; name = \""+key+"\" filename = \""+value.fileName()+"\""+
                "Context-Type: "+value.mimType()+"\r\n"+
                "\r\n";
    }

    public void onWriteBody(OutputStream outputStream) throws IOException {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                String postTextStr = getText(key, (String) value);
                outputStream.write(postTextStr.getBytes());
            }

            if(value instanceof Bindry){
                Bindry bindry = (Bindry) value;
                String postTextStr = getText(key,bindry);
                outputStream.write(postTextStr.getBytes());
                bindry.onWrite(outputStream);
                outputStream.write("\r\n".getBytes());
            }
        }

        if (params.size() != 0) {
            outputStream.write(endBoundary.getBytes());
        }
    }

    public TeaRequestBody addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public TeaRequestBody addParam(String key, Bindry value) {
        params.put(key,value);
        return this;
    }

    public TeaRequestBody type(String form) {
        this.type = form;
        return this;
    }
}
