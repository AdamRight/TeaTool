package com.tea.teatool.loadingshape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.tea.teatool.R;

/**
 * Created by Admin on 2019/6/23.
 */

public class ShapeView extends View {

    private Paint paint;
    private Shape currentShape = Shape.Circle;
    private Path path;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        switch (currentShape) {
            case Circle:
                int center = getWidth() / 2;
                paint.setColor(ContextCompat.getColor(getContext(), R.color.circle));
                canvas.drawCircle(center, center, center, paint);
                break;
            case Square:
                paint.setColor(ContextCompat.getColor(getContext(), R.color.rect));
                canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
                break;
            case Triangle:
                paint.setColor(ContextCompat.getColor(getContext(), R.color.triangle));
                if (path == null) {
                    path = new Path();
                    path.moveTo(getWidth() / 2, 0);
                    path.lineTo(0, (float) ((getWidth() / 2) * Math.sqrt(3)));
                    path.lineTo(getWidth(), (float) ((getWidth() / 2) * Math.sqrt(3)));
                    path.close();
                }
                canvas.drawPath(path, paint);
                break;
        }
    }

    public void exchange() {
        switch (currentShape) {
            case Circle:
                currentShape = Shape.Square;
                break;
            case Square:
                currentShape = Shape.Triangle;
                break;
            case Triangle:
                currentShape = Shape.Circle;
                break;
        }
        invalidate();
    }

    public enum Shape {
        Circle, Square, Triangle
    }

    public Shape getCurrentShape() {
        return currentShape;
    }
}
