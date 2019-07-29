package com.tea.teatool.tabmenu;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jiangtea on 2019/7/29.
 */
public abstract class BaseMenuAdapter {

    public abstract int getCount();

    public abstract View getTabView(int position, ViewGroup parent);

    public abstract View getMenuView(int position, ViewGroup parent);

    public void menuOpen(View tabView){

    }

    public void menuClose(View tabView){

    }

}
