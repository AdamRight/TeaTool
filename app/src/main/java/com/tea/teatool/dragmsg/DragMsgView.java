package com.tea.teatool.dragmsg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tea.teatool.utils.ScreenUtils;

/**
 * Created by jiangtea on 2019/8/7.
 */
public class DragMsgView extends View {

    private int dragCircleRadius = 20;
    private int fixCircleRadius;
    private int fixCircleRadiusMin = 6;
    private int fixCircleRadiusMax = 14;
    private Paint paint;
    private PointF fixPoint, dragPoint;

    public DragMsgView(Context context) {
        this(context, null);
    }

    public DragMsgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragMsgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        dragCircleRadius = ScreenUtils.dip2px(context, dragCircleRadius);
        fixCircleRadiusMin = ScreenUtils.dip2px(context, fixCircleRadiusMin);
        fixCircleRadiusMax = ScreenUtils.dip2px(context, fixCircleRadiusMax);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (dragPoint == null || fixPoint == null) {
            return;
        }

        canvas.drawCircle(dragPoint.x, dragPoint.y, dragCircleRadius, paint);

        Path bezeierPath = getBezeierPath();
        if (bezeierPath != null) {
            canvas.drawCircle(fixPoint.x, fixPoint.y, fixCircleRadius, paint);
            canvas.drawPath(bezeierPath, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX();
                float downY = event.getY();
                initPoint(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                updataDragPoint(moveX, moveY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    private void updataDragPoint(float moveX, float moveY) {
        dragPoint.x = moveX;
        dragPoint.y = moveY;
    }

    private void initPoint(float downX, float downY) {
        dragPoint = new PointF(downX, downY);
        fixPoint = new PointF(downX, downY);
    }

    private Path getBezeierPath() {
        double distance = getDistance(dragPoint, fixPoint);
        fixCircleRadius = (int) (fixCircleRadiusMax - distance / 14);
        if (fixCircleRadius < fixCircleRadiusMin){
            return null;
        }

        Path bezeierPath = new Path();
        float dx = dragPoint.x - fixPoint.x;
        float dy = dragPoint.y - fixPoint.y;
        float tanA = dy / dx;
        double arcTanA = Math.atan(tanA);
        float p0x = (float) (fixPoint.x + fixCircleRadius*Math.sin(arcTanA));
        float p0y = (float) (fixPoint.y - fixCircleRadius*Math.cos(arcTanA));

        float p1x = (float) (dragPoint.x + dragCircleRadius*Math.sin(arcTanA));
        float p1y = (float) (dragPoint.y - dragCircleRadius*Math.cos(arcTanA));

        float p2x = (float) (dragPoint.x - dragCircleRadius*Math.sin(arcTanA));
        float p2y = (float) (dragPoint.y + dragCircleRadius*Math.cos(arcTanA));

        float p3x = (float) (fixPoint.x - fixCircleRadius*Math.sin(arcTanA));
        float p3y = (float) (fixPoint.y + fixCircleRadius*Math.cos(arcTanA));

        bezeierPath.moveTo(p0x,p0y);
        PointF controlPoint = getControlPoint();
        bezeierPath.quadTo(controlPoint.x,controlPoint.y,p1x,p1y);

        bezeierPath.lineTo(p2x,p2y);
        bezeierPath.quadTo(controlPoint.x,controlPoint.y,p3x,p3y);
        bezeierPath.close();

        return bezeierPath;
    }

    private double getDistance(PointF point1, PointF point2) {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));
    }

    public PointF getControlPoint() {
        return new PointF((dragPoint.x + fixPoint.x) / 2, (dragPoint.y + fixPoint.y) / 2);
    }
}
