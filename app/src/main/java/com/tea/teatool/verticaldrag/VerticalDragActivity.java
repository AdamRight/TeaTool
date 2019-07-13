package com.tea.teatool.verticaldrag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tea.teatool.R;
import com.tea.teatool.flow.BaseFlowAdapter;
import com.tea.teatool.flow.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class VerticalDragActivity extends AppCompatActivity {

    private ListView listView;
    private FlowLayout drawFlow;
    private List<String> listItem;
    private List<String> flowItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_drag);

        listView = findViewById(R.id.list_view);
        drawFlow = findViewById(R.id.draw_flow);

        flowItem = getIteiteflowItem();
        addListItem();

        flowAdapter();
        listAdapter();
    }

    private void addListItem() {
        listItem = new ArrayList();
        for (int i = 0; i < 50; i++) {
            listItem.add("第" + i + "位置Itme");
        }
    }

    private void listAdapter() {
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return listItem.size();
            }

            @Override
            public Object getItem(int position) {
                return listItem.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) LayoutInflater.from(VerticalDragActivity.this).inflate(R.layout.drag_item_lv, parent, false);
                item.setText(listItem.get(position));
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(VerticalDragActivity.this,"第" + position + "位置Itme",Toast.LENGTH_SHORT).show();
                    }
                });
                return item;
            }
        });
    }

    private void flowAdapter() {
        drawFlow.setAdapter(new BaseFlowAdapter() {
            @Override
            public int getCount() {
                return flowItem.size();
            }
            @Override
            public View getView(final int position, ViewGroup parent) {
                TextView tagTv = (TextView) LayoutInflater.from(VerticalDragActivity.this).inflate(R.layout.flow_item_tag, parent, false);
                tagTv.setText(flowItem.get(position));
                tagTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv = (TextView) view;
                        Toast.makeText(VerticalDragActivity.this,tv.getText().toString() + position,Toast.LENGTH_SHORT).show();
                    }
                });
                return tagTv;
            }
        });
    }

    private List<String> getIteiteflowItem() {
        List<String> iteflowItem = new ArrayList<>();
        iteflowItem.add("电竞");
        iteflowItem.add("明星");
        iteflowItem.add("情感");
        iteflowItem.add("社会");
        iteflowItem.add("游戏");
        iteflowItem.add("汽车");
        iteflowItem.add("校园");
        iteflowItem.add("军事");
        iteflowItem.add("健康");
        iteflowItem.add("设计");
        iteflowItem.add("艺术");
        iteflowItem.add("辟谣");
        iteflowItem.add("家居");
        iteflowItem.add("政务");
        iteflowItem.add("育儿");
        iteflowItem.add("舞蹈");
        iteflowItem.add("宗教");
        iteflowItem.add("婚恋");
        iteflowItem.add("三农");
        iteflowItem.add("吃鸡");
        return iteflowItem;
    }
}
