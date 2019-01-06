package com.tea.teatool.flow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 2018/12/2.
 */

public class FlowLayout extends ViewGroup {

    private Line mLine;

    private int usedWidth;

    private int mHorizontalSpacing = 6;



    public FlowLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthSize - getPaddingLeft() - getPaddingRight();
        int height = heightSize -getPaddingTop() - getPaddingBottom();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE){
                continue;
            }

            int childWidthSpec = MeasureSpec.makeMeasureSpec(width,widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode);
            int childHeighSpec = MeasureSpec.makeMeasureSpec(height,heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode);
            childView.measure(childWidthSpec,childHeighSpec);

            if (mLine == null){
                mLine = new Line();
            }

            int childWidth = childView.getMeasuredWidth();

            usedWidth += childWidth;
            if (usedWidth < width){
                mLine.addView(childView);
                usedWidth += mHorizontalSpacing;
                if (usedWidth >= width){

                }
            } else {


            }

        }

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    public class Line {
        private void addView(View view){

        }
    }
}
