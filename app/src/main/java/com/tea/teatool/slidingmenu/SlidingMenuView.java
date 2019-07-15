package com.tea.teatool.slidingmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.tea.teatool.R;
import com.tea.teatool.utils.ScreenUtils;

/**
 * Created by jiangtea on 2019/7/15.
 */
public class SlidingMenuView extends HorizontalScrollView {

    private int menuWidth;

    private View cotentView,menuView;

    public SlidingMenuView(Context context) {
        this(context,null);
    }

    public SlidingMenuView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidingMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenuView);
        float rightMargin = typedArray.getDimension(R.styleable.SlidingMenuView_menuRightMargin, ScreenUtils.dip2px(context, 50));
        menuWidth = (int) (ScreenUtils.getScreenWidth(context) - rightMargin);
        typedArray.recycle();
    }
}
