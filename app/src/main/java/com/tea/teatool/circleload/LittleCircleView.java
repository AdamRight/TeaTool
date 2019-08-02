package com.tea.teatool.circleload;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jiangtea on 2019/8/2.
 */
public class LittleCircleView extends View {

    private Paint paint;
    private int color;

    public LittleCircleView(Context context) {
        this(context, null);
    }

    public LittleCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LittleCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int pointX = getWidth() / 2;
        int pointY = getHeight() / 2;
        canvas.drawCircle(pointX, pointY, pointX, paint);
    }

    public void exchangeColor(int color){
        this.color = color;
        paint.setColor(color);
        invalidate();
    }

    public int getCircleColor(){
        return color;
    }
}
