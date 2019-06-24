package com.tea.teatool.tearatingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tea.teatool.R;

/**
 * Created by jiangtea on 2019/5/29.
 */

public class TeaRatingBar extends View {

    private int starNum;
    private Bitmap starNormal;
    private Bitmap starSelected;
    private int starSpacing = 10;
    private int mCurrentGrade;

    public TeaRatingBar(Context context) {
        this(context, null);
    }

    public TeaRatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TeaRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TeaRatingBar);
        starNum = array.getInt(R.styleable.TeaRatingBar_starNum, 5);
        int starNormalId = array.getResourceId(R.styleable.TeaRatingBar_starNormal, 0);
        int starSelectedId = array.getResourceId(R.styleable.TeaRatingBar_starSelected, 0);
        if (starNormalId == 0 || starSelectedId == 0) {
            throw new RuntimeException("无资源");
        }
        starNormal = BitmapFactory.decodeResource(getResources(), starNormalId);
        starSelected = BitmapFactory.decodeResource(getResources(), starSelectedId);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = starNormal.getWidth() * starNum + (starNum - 1) * starSpacing;
        setMeasuredDimension(width, starNormal.getHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < starNum; i++) {
            int x = starNormal.getWidth() * i + i * starSpacing;
            if (mCurrentGrade > i) {
                canvas.drawBitmap(starSelected, x, 0, null);
            } else {
                canvas.drawBitmap(starNormal, x, 0, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                int currentGrade = (int) ((moveX / (starNormal.getWidth() + starSpacing)) + 1);

                if (currentGrade < 0) {
                    currentGrade = 0;
                }
                if (currentGrade > starNum) {
                    currentGrade = starNum;
                }
                if (currentGrade == mCurrentGrade) {
                    return true;
                }

                mCurrentGrade = currentGrade;
                invalidate();
                break;
        }
        return true;
    }
}
