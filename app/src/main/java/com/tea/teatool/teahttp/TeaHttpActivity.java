package com.tea.teatool.teahttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tea.teatool.R;

import java.io.IOException;

public class TeaHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_http);

        TeaHttpClient teaHttpClient = new TeaHttpClient();

        TeaRequest request = new TeaRequest.Builder().build();

        Call call = teaHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, TeaResponse response) throws IOException {

            }
        });
    }
}
