package com.tea.teatool.tabmenu;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by jiangtea on 2019/7/29.
 */
public class TabMenuView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private LinearLayout tabView;
    private FrameLayout frameLayoutMenu;
    private View shadowView;

    private int shadowColor = 0x88888888;
    private FrameLayout menuView;


    public TabMenuView(Context context) {
        this(context, null);
    }

    public TabMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        tabView = new LinearLayout(mContext);
        tabView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(tabView);

        frameLayoutMenu = new FrameLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        frameLayoutMenu.setLayoutParams(params);
        addView(frameLayoutMenu);

        shadowView = new View(mContext);
        shadowView.setBackgroundColor(shadowColor);
        shadowView.setAlpha(0f);
        shadowView.setOnClickListener(this);
        shadowView.setVisibility(GONE);
        frameLayoutMenu.addView(shadowView);

        menuView = new FrameLayout(mContext);
        menuView.setBackgroundColor(Color.WHITE);
        frameLayoutMenu.addView(menuView);
    }

    @Override
    public void onClick(View v) {

    }
}
