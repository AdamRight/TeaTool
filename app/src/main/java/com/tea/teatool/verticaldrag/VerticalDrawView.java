package com.tea.teatool.verticaldrag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by jiangtea on 2019/7/13.
 */
public class VerticalDrawView extends FrameLayout {

    private ViewDragHelper dragHelper;

    private View dragListView;

    private int flowHeight;

    private boolean flowIsOpen = false;

    private float downY;

    public VerticalDrawView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDrawView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDrawView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragHelper = ViewDragHelper.create(this, drawHelpCallback);
    }

    private ViewDragHelper.Callback drawHelpCallback = new ViewDragHelper.Callback() {

        /**
         * @param child 指定该子view是否可以拖动
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return dragListView == child;
        }

        /**
         * 垂直拖动移动位置
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (top <= 0) {
                top = 0;
            }

            if (top >= flowHeight) {
                top = flowHeight;
            }
            return top;
        }

        /**
         * 水平拖动移动位置
         * @param child
         * @param left
         * @param dx
         * @return
         */
        /*@Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }*/

        /**
         * 松开手指处理
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (releasedChild == dragListView) {
                if (dragListView.getTop() > flowHeight / 2) {
                    dragHelper.settleCapturedViewAt(0, flowHeight);
                    flowIsOpen = true;
                } else {
                    dragHelper.settleCapturedViewAt(0,0);
                    flowIsOpen = false;
                }
                invalidate();
            }
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("只能包含两个布局");
        }

        dragListView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            View menuView = getChildAt(0);
            flowHeight = menuView.getMeasuredHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 拦截子listview
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (flowIsOpen){
//            return true;
//        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                //注意
                dragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                if ((moveY - downY) > 0 && !canChildScrollUp() || flowIsOpen){
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 判断View是否滚动到了最顶部,还能不能向上滚
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (dragListView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) dragListView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(dragListView, -1) || dragListView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(dragListView, -1);
        }
    }

    /**
     * 相应滚动
     */
    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)){
            invalidate();
        }
    }
}
