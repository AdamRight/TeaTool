package com.tea.teatool.flow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tea.teatool.R;

import java.util.ArrayList;
import java.util.List;


public class FlowActivity extends AppCompatActivity {

    private FlowLayout flow;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        mList = getIteitemList();
        flow = findViewById(R.id.flow);

        flow.setAdapter(new BaseFlowAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }
            @Override
            public View getView(final int position, ViewGroup parent) {
                TextView tagTv = (TextView) LayoutInflater.from(FlowActivity.this).inflate(R.layout.flow_item_tag, parent, false);
                tagTv.setText(mList.get(position));
                tagTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv = (TextView) view;
                        Toast.makeText(FlowActivity.this,tv.getText().toString() + position,Toast.LENGTH_SHORT).show();
                    }
                });
                return tagTv;
            }
        });
    }


    private List<String> getIteitemList() {
        List<String> itemList = new ArrayList<>();
        itemList.add("电竞");
        itemList.add("明星");
        itemList.add("情感");
        itemList.add("社会");
        itemList.add("游戏");
        itemList.add("汽车");
        itemList.add("新鲜事");
        itemList.add("电视剧");
        itemList.add("摄影");
        itemList.add("问答");
        itemList.add("音乐");
        itemList.add("美食");
        itemList.add("数码");
        itemList.add("体育");
        itemList.add("科技");
        itemList.add("综艺");
        itemList.add("动漫");
        itemList.add("时尚");
        itemList.add("法律");
        itemList.add("科普");
        itemList.add("校园");
        itemList.add("军事");
        itemList.add("美妆");
        itemList.add("读书");
        itemList.add("运动健身");
        itemList.add("旅游");
        itemList.add("星座");
        itemList.add("养生");
        itemList.add("萌宠");
        itemList.add("健康");
        itemList.add("设计");
        itemList.add("艺术");
        itemList.add("辟谣");
        itemList.add("家居");
        itemList.add("政务");
        itemList.add("育儿");
        itemList.add("舞蹈");
        itemList.add("宗教");
        itemList.add("婚恋");
        itemList.add("三农");
        itemList.add("吃鸡");
        return itemList;
    }
}
