package com.tea.teatool.flow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangtea on 2018/12/2.
 */

public class FlowLayout extends ViewGroup {

    private List<List<View>> childViews = new ArrayList<>();
    private BaseFlowAdapter mAdapter;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        childViews.clear();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getPaddingTop() + getPaddingBottom();
        int lineWidth = getPaddingLeft();
        List<View> childView = new ArrayList<>();
        childViews.add(childView);
        int maxHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childV = getChildAt(i);
            if (childV.getVisibility() == GONE) {
                continue;
            }
            measureChild(childV, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childV.getLayoutParams();
            if (childV.getMeasuredWidth() + layoutParams.rightMargin + layoutParams.leftMargin + lineWidth > width) {
                //换行
                height += maxHeight;
                lineWidth = childV.getMeasuredWidth() + layoutParams.rightMargin + layoutParams.leftMargin;
                childView = new ArrayList<>();
                childViews.add(childView);
            } else {
                lineWidth += childV.getMeasuredWidth() + layoutParams.rightMargin + layoutParams.leftMargin;
                maxHeight = Math.max(childV.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin, maxHeight);
            }
            childView.add(childV);
        }

        height += maxHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        int left, top = getPaddingTop(), right, bottom;

        for (List<View> childViews : childViews) {
            left = getPaddingLeft();
            int maxHeight = 0;

            for (View childView : childViews) {
                if (childView.getVisibility() == GONE) {
                    continue;
                }
                MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                left += params.leftMargin;
                int childTop = top + params.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = childTop + childView.getMeasuredHeight();

                childView.layout(left, childTop, right, bottom);

                left += childView.getMeasuredWidth() + params.rightMargin;
                int childHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                maxHeight = Math.max(maxHeight, childHeight);
            }
            top += maxHeight;
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setAdapter(BaseFlowAdapter adapter) {
        if (adapter == null) {
            //抛异常
            throw new RuntimeException("空指针异常");
        }

        removeAllViews();
        this.mAdapter = adapter;
        int childCount = mAdapter.getCount();
        for (int i = 0; i < childCount; i++) {
            View childView = mAdapter.getView(i, this);
            addView(childView);
        }
    }
}
