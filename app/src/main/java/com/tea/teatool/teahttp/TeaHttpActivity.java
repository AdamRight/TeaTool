package com.tea.teatool.teahttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tea.teatool.R;

import java.io.File;
import java.io.IOException;

public class TeaHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_http);

        File file = new File("");

        TeaHttpClient teaHttpClient = new TeaHttpClient();

        TeaRequestBody requestBody = new TeaRequestBody().type(TeaRequestBody.FORM).addParam("num","123456");
//        TeaRequestBody requestBody = new TeaRequestBody().type(TeaRequestBody.FORM).addParam("file",TeaRequest.create(file));

        TeaRequest request = new TeaRequest.Builder().url("http://httpbin.org/post")
                .post(requestBody).build();

        Call call = teaHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, TeaResponse response) throws IOException {
                Log.d("dfsaaasd",response.string());
            }


        });
    }
}
