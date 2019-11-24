package com.tea.teatool.rv.headandbottom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.tea.teatool.R;
import com.tea.teatool.rv.common.LettersAdapter;

import java.util.ArrayList;

public class AddHeadRecyclerViewActivity extends AppCompatActivity {

    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_head_recycler_view);
        initData();
        RecyclerView typeUseRv = findViewById(R.id.type_use_rv);
        typeUseRv.setLayoutManager(new LinearLayoutManager(this));

        HeadAndBottomAdapter headAndBottomAdapter = new HeadAndBottomAdapter(new LettersAdapter(data, this));
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_head_and_bottom_rv, typeUseRv, false);
        headAndBottomAdapter.addHeadView(headView);
        headAndBottomAdapter.addBottomView(headView);
        typeUseRv.setAdapter(headAndBottomAdapter);
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            data.add("" + (char) i);
        }
    }
}
