package com.tea.teatool.teahttp;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tea.teatool.R;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TeaOkUpFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_ok_up_file);

        String url = "http://httpbin.org/post";
        File file = new File(Environment.getExternalStorageDirectory(),"1.png");
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("name","123");
        Log.e("Tag",file.getName());
        Log.e("Tag",file.getAbsolutePath());
        builder.addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())),file));

        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("Tag",response.body().string());
            }
        });
    }

    private String guessMimeType(String absoluteFile) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(absoluteFile);
        Log.e("Tag",contentTypeFor);
        return contentTypeFor;
    }
}
