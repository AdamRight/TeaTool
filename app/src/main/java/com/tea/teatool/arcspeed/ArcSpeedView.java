package com.tea.teatool.arcspeed;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.tea.teatool.R;

/**
 * Created by Admin on 2019/5/26.
 */

public class ArcSpeedView extends View {

    private int bottomColor = Color.GREEN;
    private int topColor = Color.RED;
    private int textColor;
    private int textSize = 20;
    private int borderWidth;
    private Paint bottomPaint;
    private Paint topPaint;
    private Paint textPaint;
    private int totalSize = 0;
    private int currentSize = 0;

    public ArcSpeedView(Context context) {
        this(context, null);
    }

    public ArcSpeedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcSpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ArcSpeedView);
        bottomColor = array.getColor(R.styleable.ArcSpeedView_bottomColor, bottomColor);
        topColor = array.getColor(R.styleable.ArcSpeedView_topColor, topColor);
        textColor = array.getColor(R.styleable.ArcSpeedView_middleTextColor, textColor);
        borderWidth = (int) array.getDimension(R.styleable.ArcSpeedView_borderWidth, borderWidth);
        textSize = array.getDimensionPixelSize(R.styleable.ArcSpeedView_middleTestSize, sp2Px(textSize));
        array.recycle();

        bottomPaint = new Paint();
        initPaint(bottomPaint, bottomColor);
        topPaint = new Paint();
        initPaint(topPaint, topColor);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
    }

    private void initPaint(Paint paint, int color) {
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(borderWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
    }

    private int sp2Px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //确保正方形
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);
        int side = widthSize > heighSize ? heighSize : widthSize;
        setMeasuredDimension(side, side);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(borderWidth / 2, borderWidth / 2,
                getWidth() - borderWidth / 2, getHeight() - borderWidth / 2);
        //底部弧形
        canvas.drawArc(rectF, 135, 270, false, bottomPaint);
        //顶部弧形
        if (totalSize == 0) {
            return;
        }
        float rate = (float) currentSize / totalSize;
        canvas.drawArc(rectF, 135, rate * 270, false, topPaint);
        //文字
        String currentText = currentSize + "";
        Rect rect = new Rect();
        textPaint.getTextBounds(currentText, 0, currentText.length(), rect);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseLine = getHeight() / 2 + dy;
        int x = getWidth() / 2 - rect.width() / 2;
        canvas.drawText(currentText, x, baseLine, textPaint);
    }

    public synchronized void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public synchronized void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
        invalidate();
    }

}
