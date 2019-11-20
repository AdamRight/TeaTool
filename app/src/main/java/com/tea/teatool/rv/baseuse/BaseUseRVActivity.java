package com.tea.teatool.rv.baseuse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tea.teatool.R;
import com.tea.teatool.rv.common.ItemClickListener;
import com.tea.teatool.rv.common.ItemLongClickListener;
import com.tea.teatool.rv.common.LettersAdapter;

import java.util.ArrayList;

public class BaseUseRVActivity extends AppCompatActivity implements BaseUseRecyclerAdapter.ItemClickListener, ItemClickListener, ItemLongClickListener {

    private RecyclerView baseUseRv;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_use_rv);

        initData();
        baseUseRv = findViewById(R.id.base_use_rv);
        baseUseRv.setLayoutManager(new LinearLayoutManager(this));
//        BaseUseRecyclerAdapter baseUseRecyclerAdapter = new BaseUseRecyclerAdapter(data, this);
//        baseUseRv.setAdapter(baseUseRecyclerAdapter);
//        baseUseRv.addItemDecoration(new BaseUseDecoration());
        LettersAdapter lettersAdapter = new LettersAdapter(data, this);
        baseUseRv.setAdapter(lettersAdapter);
        baseUseRv.addItemDecoration(new BaseUseLinerlayoutDecoration(this, R.drawable.rv_item_decotation));
//        baseUseRecyclerAdapter.setOnItemClickListener(this);
        lettersAdapter.setOnItemClickListener(this);
        lettersAdapter.setOnItemLongClickListener(this);
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            data.add("" + (char) i);
        }
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, data.get(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(int position) {
        Toast.makeText(this, "长按" + data.get(position), Toast.LENGTH_SHORT).show();
        return true;
    }
}
