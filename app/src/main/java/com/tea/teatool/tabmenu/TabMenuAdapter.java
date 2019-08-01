package com.tea.teatool.tabmenu;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tea.teatool.R;

/**
 * Created by jiangtea on 2019/7/31.
 */
public class TabMenuAdapter extends BaseMenuAdapter {

    private Context context;

    private String[] items ={"热门","体育","娱乐","美女"};

    public TabMenuAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public View getTabView(int position, ViewGroup parent) {
        TextView tabView = (TextView) LayoutInflater.from(context).inflate(R.layout.menu_tab_item, parent, false);
        tabView.setText(items[position]);
        tabView.setTextColor(Color.BLACK);
        return tabView;
    }

    @Override
    public View getMenuView(int position, ViewGroup parent) {
        TextView menuView = (TextView) LayoutInflater.from(context).inflate(R.layout.menu_content_item, parent, false);
        menuView.setText(items[position]);
        menuView.setTextColor(Color.BLACK);
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        return menuView;
    }

    @Override
    public void menuOpen(View tabView) {
        TextView tabTv = (TextView) tabView;
        tabTv.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
    }

    @Override
    public void menuClose(View tabView) {
        TextView tabTv = (TextView) tabView;
        tabTv.setTextColor(Color.BLACK);
    }
}
