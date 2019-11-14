package com.tea.teatool.rv.baseuse;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jiangtea on 2019/11/14.
 */
public class BaseUseDecoration extends RecyclerView.ItemDecoration {

    private Paint paint;
    private final int top = 10;

    public BaseUseDecoration() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //顶部预留分割线
        int position = parent.getChildAdapterPosition(view);
        if (position != 0){
            outRect.top = top;
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
            rect.top = rect.bottom - top;
            c.drawRect(rect,paint);
        }

    }
}
