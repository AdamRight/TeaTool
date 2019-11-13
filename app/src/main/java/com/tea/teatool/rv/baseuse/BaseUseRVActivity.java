package com.tea.teatool.rv.baseuse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tea.teatool.R;

import java.util.ArrayList;

public class BaseUseRVActivity extends AppCompatActivity {

    private RecyclerView baseUseRv;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_use_rv);

        initData();
        baseUseRv = findViewById(R.id.base_use_rv);
        baseUseRv.setLayoutManager(new LinearLayoutManager(this));
        baseUseRv.setAdapter(new BaseUseRecyclerAdapter(data,this));
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            data.add("" + (char) i);
        }
    }
}
