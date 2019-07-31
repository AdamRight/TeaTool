package com.tea.teatool.tabmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

    private int menuViewHeight;
    private BaseMenuAdapter menuAdapter;
    private int currentPosition = -1;
    private boolean animatorEnd = true;
    private long DURATION_TIME = 350;

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (menuViewHeight == 0 && height > 0) {
            menuViewHeight = (int) (height * 0.75);
            ViewGroup.LayoutParams params = menuView.getLayoutParams();
            params.height = menuViewHeight;
            menuView.setLayoutParams(params);

            menuView.setTranslationY(-menuViewHeight);
        }
    }

    public void setAdapter(BaseMenuAdapter adapter) {
        this.menuAdapter = adapter;
        for (int i = 0; i < menuAdapter.getCount(); i++) {
            View tab = menuAdapter.getTabView(i, tabView);
            tabView.addView(tab);

            LinearLayout.LayoutParams params = (LayoutParams) tab.getLayoutParams();
            params.weight = 1;
            tab.setLayoutParams(params);

            setTabClick(tab, i);

            View menu = menuAdapter.getMenuView(i, menuView);
            menu.setVisibility(GONE);
            menuView.addView(menu);
        }
    }

    private void setTabClick(final View tab, final int position) {
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition == -1) {
                    openMenu(tab, position);
                } else {
                    if (currentPosition == position) {
                        closeMenu();
                    } else {
                        View currentMenu = menuView.getChildAt(currentPosition);
                        currentMenu.setVisibility(GONE);
                        menuAdapter.menuClose(tabView.getChildAt(currentPosition));
                        currentPosition = position;
                        currentMenu = menuView.getChildAt(currentPosition);
                        currentMenu.setVisibility(VISIBLE);
                        menuAdapter.menuOpen(tabView.getChildAt(currentPosition));
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        closeMenu();
    }

    private void openMenu(final View tab, final int position) {
        if (!animatorEnd) {
            return;
        }

        shadowView.setVisibility(VISIBLE);
        View menu = menuView.getChildAt(position);
        menu.setVisibility(VISIBLE);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(menuView, "translationY", -menuViewHeight, 0);
        transAnimator.setDuration(DURATION_TIME);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(shadowView, "alpha", 0, 1);
        alphaAnimator.setDuration(DURATION_TIME);

        animatorSet.playTogether(transAnimator, alphaAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                animatorEnd = false;
                menuAdapter.menuOpen(tab);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorEnd = true;
                currentPosition = position;
            }
        });
        animatorSet.start();
    }

    private void closeMenu() {
        if (!animatorEnd) {
            return;
        }

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(menuView, "translationY", 0, -menuViewHeight);
        transAnimator.setDuration(DURATION_TIME);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(shadowView, "alpha", 1, 0);
        alphaAnimator.setDuration(DURATION_TIME);

        animatorSet.playTogether(transAnimator, alphaAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                animatorEnd = false;
                menuAdapter.menuClose(tabView.getChildAt(currentPosition));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                shadowView.setVisibility(GONE);
                View childAt = menuView.getChildAt(currentPosition);
                childAt.setVisibility(GONE);
                animatorEnd = true;
                currentPosition = -1;
            }
        });
        animatorSet.start();
    }
}
