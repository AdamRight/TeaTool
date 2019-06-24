package com.tea.teatool.letterindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jiangtea on 2019/5/31.
 */

public class TeaLetterIndex extends View {

    private Paint paintNormal;
    private Paint paintSelectd;
    // 定义26个字母
    public String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int widthText;
    private String currentLetter;

    public TeaLetterIndex(Context context) {
        this(context, null);
    }

    public TeaLetterIndex(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TeaLetterIndex(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintNormal = getLetterPaint(Color.BLACK);
        paintSelectd = getLetterPaint(Color.RED);
        widthText = (int) paintNormal.measureText(letters[0]);
    }

    private Paint getLetterPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(sp2px(12));
        paint.setColor(color);
        return paint;
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getPaddingLeft() + getPaddingRight() + widthText, MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int itemHeigh = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
        for (int i = 0; i < letters.length; i++) {
            int letterCenterY = itemHeigh * i + itemHeigh / 2 + getPaddingTop();
            Paint.FontMetrics fontMetrics = paintNormal.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
            int baseLine = letterCenterY + dy;
            int textWidth = (int) paintNormal.measureText(letters[i]);
            int x = getWidth() / 2 - textWidth / 2;

            if (letters[i].equals(currentLetter)) {
                canvas.drawText(letters[i], x, baseLine, paintSelectd);
            } else {
                canvas.drawText(letters[i], x, baseLine, paintNormal);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float currentY = event.getY();
                int itemHeigh = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
                int currentPosition = (int) (currentY / itemHeigh);
                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                if (currentPosition > letters.length - 1) {
                    currentPosition = letters.length - 1;
                }

                currentLetter = letters[currentPosition];

                if (listener != null) {
                    listener.selected(currentLetter);
                }

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                currentLetter = "";
                invalidate();
                if (listener != null) {
                    listener.selected(currentLetter);
                }
                break;
        }
        return true;
    }

    private LetterSelectedListener listener;

    public void setOnLetterSelectedListener(LetterSelectedListener listener) {
        this.listener = listener;
    }

    public interface LetterSelectedListener {
        void selected(String letter);
    }


}
