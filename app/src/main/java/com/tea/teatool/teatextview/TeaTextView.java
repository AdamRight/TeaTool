package com.tea.teatool.teatextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.tea.teatool.R;

/**
 * Created by jiangtea on 2019/5/25.
 */

public class TeaTextView extends View {

    private String teaText;
    private int teaColor = Color.BLACK;
    private int teaSize = 15;
    private Paint paint;

    public TeaTextView(Context context) {
        this(context, null);
    }

    public TeaTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TeaTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TeaTextView);
        teaText = array.getString(R.styleable.TeaTextView_teaText);
        teaColor = array.getColor(R.styleable.TeaTextView_teaTextColor, teaColor);
        teaSize = array.getDimensionPixelSize(R.styleable.TeaTextView_teaTextSize, sp2Px(teaSize));
        array.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(teaSize);
        paint.setColor(teaColor);

    }

    private int sp2Px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);

        //计算wrap_content
        if (widthMode == MeasureSpec.AT_MOST){
            Rect rect = new Rect();
            paint.getTextBounds(teaText,0,teaText.length(), rect);
            //计算padding
            widthSize = rect.width() + getPaddingLeft() + getPaddingRight();
        }

        if (heighMode == MeasureSpec.AT_MOST){
            Rect rect = new Rect();
            paint.getTextBounds(teaText,0,teaText.length(), rect);
            heighSize = rect.height() + getPaddingBottom() + getPaddingTop();
        }

        setMeasuredDimension(widthSize,heighSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseLine = getHeight() / 2 + dy;
        //计算padding
        int x = getPaddingLeft();
        canvas.drawText(teaText,x,baseLine,paint);

    }

}
