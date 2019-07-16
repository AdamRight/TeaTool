package com.tea.teatool.slidingmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.tea.teatool.R;
import com.tea.teatool.utils.ScreenUtils;

/**
 * Created by jiangtea on 2019/7/15.
 */
public class SlidingMenuView extends HorizontalScrollView {

    private int menuWidth;

    private View contentView, menuView;

    public SlidingMenuView(Context context) {
        this(context, null);
    }

    public SlidingMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenuView);
        float rightMargin = typedArray.getDimension(R.styleable.SlidingMenuView_menuRightMargin, ScreenUtils.dip2px(context, 50));
        menuWidth = (int) (ScreenUtils.getScreenWidth(context) - rightMargin);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ViewGroup linearLayout = (ViewGroup) getChildAt(0);

        int childCount = linearLayout.getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("只能放置两个子View");
        }

        menuView = linearLayout.getChildAt(0);
        contentView = linearLayout.getChildAt(1);

        ViewGroup.LayoutParams menuParams = menuView.getLayoutParams();
        menuParams.width = menuWidth;
        menuView.setLayoutParams(menuParams);

        ViewGroup.LayoutParams contentParams = contentView.getLayoutParams();
        contentParams.width = ScreenUtils.getScreenWidth(getContext());
        contentView.setLayoutParams(contentParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(menuWidth, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            int scrollX = getScrollX();
            if (scrollX > menuWidth / 2) {
                //回到主页
                smoothScrollTo(menuWidth, 0);
            } else {
                //打开menu
                smoothScrollTo(0, 0);
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float scale = 1f * l / menuWidth;
        //主页缩放
        float contentScale = 0.7f + 0.3f * scale;
        ViewCompat.setPivotX(contentView,0);
        ViewCompat.setPivotY(contentView,contentView.getMeasuredWidth());
        ViewCompat.setScaleX(contentView,contentScale);
        ViewCompat.setScaleY(contentView,contentScale);
        //菜单，缩放、透明度、平移
        float menuAlpha = 0.4f + (1-scale)*0.6f;
        ViewCompat.setAlpha(menuView,menuAlpha);

        float menuScale = 0.6f + (1-scale)*0.4f;
        ViewCompat.setScaleX(menuView,menuScale);
        ViewCompat.setScaleY(menuView, menuScale);

        ViewCompat.setTranslationX(menuView, 0.4f*l);
    }
}
