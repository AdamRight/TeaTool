package com.tea.teatool.cliptext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.tea.teatool.R;

/**
 * Created by Admin on 2019/5/26.
 */

public class ClipTextView extends AppCompatTextView {

    private Paint originPaint;
    private Paint changePaint;
    private float mCurrentProgress = 0.0f;
    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    public ClipTextView(Context context) {
        this(context, null);
    }

    public ClipTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ClipTextView);
        int originColor = array.getColor(R.styleable.ClipTextView_originColor, getTextColors().getDefaultColor());
        int changeColor = array.getColor(R.styleable.ClipTextView_changeColor, getTextColors().getDefaultColor());
        array.recycle();

        originPaint = initPaint(originColor);
        changePaint = initPaint(changeColor);
    }

    private Paint initPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int middle = (int) (mCurrentProgress * getWidth());
        if (mDirection == Direction.LEFT_TO_RIGHT) {
            drawClipTextView(canvas, changePaint, 0, middle);
            drawClipTextView(canvas, originPaint, middle, getWidth());
        } else {
            drawClipTextView(canvas, changePaint, getWidth() - middle, getWidth());
            drawClipTextView(canvas, originPaint, 0, getWidth() - middle);
        }
    }

    private void drawClipTextView(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);

        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }

    public void setDirection(Direction direction){
        this.mDirection = direction;
    }

    public void setCurrentProgress(float currentProgress){
        this.mCurrentProgress = currentProgress;
        invalidate();
    }

    public void setChangeColor(int changeColor) {
        this.changePaint.setColor(changeColor);
    }

    public void setOriginColor(int originColor) {
        this.originPaint.setColor(originColor);
    }


}
