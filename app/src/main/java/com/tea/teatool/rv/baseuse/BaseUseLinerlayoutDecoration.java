package com.tea.teatool.rv.baseuse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jiangtea on 2019/11/14.
 */
public class BaseUseLinerlayoutDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private Drawable drawable;

    public BaseUseLinerlayoutDecoration(Context context, int drawableId) {
        this.context = context;
        drawable = ContextCompat.getDrawable(context,drawableId);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //顶部预留分割线
        int position = parent.getChildAdapterPosition(view);
        if (position != 0){
            outRect.top = drawable.getIntrinsicHeight();
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 1; i < childCount; i++) {
            rect.bottom = parent.getChildAt(i).getTop();
            rect.top = rect.bottom - drawable.getIntrinsicHeight();
            drawable.setBounds(rect);
            drawable.draw(c);
        }

    }
}
