package com.tea.teatool.tabmenu;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangtea on 2019/7/29.
 */
public abstract class BaseMenuAdapter {

    public abstract int getCount();

    public abstract View getTabView(int position, ViewGroup parent);

    public abstract View getMenuView(int position, ViewGroup parent);

    public abstract void menuOpen(View tabView);

    public abstract void menuClose(View tabView);


    private List<MenuObserver> observers = new ArrayList<>();

    public void registerObserver(MenuObserver observer){
        observers.add(observer);
    }

    public void unregisterObserver(MenuObserver observer){
        observers.remove(observer);
    }

    public void closeMenu(){
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).closeMenu();
        }
    }

}
