package com.tea.teatool.rv.type;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tea.teatool.R;

import java.util.ArrayList;

public class TypeRVActivity extends AppCompatActivity {

    private ArrayList<TypeBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_rv);

        initData();
        RecyclerView typeUseRv = findViewById(R.id.type_use_rv);
        typeUseRv.setLayoutManager(new LinearLayoutManager(this));
        typeUseRv.setAdapter(new TypeRecyclerAdapter(data,this));
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            data.add(new TypeBean("" + (char) i ,(int) i));
        }
    }
}
