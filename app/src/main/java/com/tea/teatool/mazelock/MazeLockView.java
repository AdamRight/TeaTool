package com.tea.teatool.mazelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangtea on 2019/6/10.
 */

public class MazeLockView extends View {

    private boolean isInit = false;

    private float mDotRadius = 0;

    // 二维数组初始化，int[3][3]
    private Point[][] mPoints = new Point[3][3];

    // 画笔
    private Paint mLinePaint;
    private Paint mPressedPaint;
    private Paint mErrorPaint;
    private Paint mNormalPaint;
    private Paint mArrowPaint;
    // 颜色
    private int mOuterPressedColor = 0xff8cbad8;
    private int mInnerPressedColor = 0xff0596f6;
    private int mOuterNormalColor = 0xffd9d9d9;
    private int mInnerNormalColor = 0xff929292;
    private int mOuterErrorColor = 0xff901032;
    private int mInnerErrorColor = 0xffea0945;

    private float mMovingX = 0f;
    private float mMovingY = 0f;

    private boolean isTouchPoint = false;

    private List<Point> selectPoints = new ArrayList<>();
    private int i;

    public MazeLockView(Context context) {
        this(context, null);
    }

    public MazeLockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MazeLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initDot();
            initPaint();
            isInit = true;
        }

        drawMaze(canvas);
    }

    private void drawMaze(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {

                if (mPoints[i][j].statusIsPressed()) {
                    // 先绘制外圆
                    mPressedPaint.setColor(mOuterPressedColor);
                    canvas.drawCircle(mPoints[i][j].x, mPoints[i][j].y, mDotRadius, mPressedPaint);
                    // 后绘制内圆
                    mPressedPaint.setColor(mInnerPressedColor);
                    canvas.drawCircle(mPoints[i][j].x, mPoints[i][j].y, mDotRadius / 6, mPressedPaint);

                    mLinePaint.setColor(mInnerPressedColor);
                }

                if (mPoints[i][j].statusIsNormal()) {
                    // 先绘制外圆
                    mNormalPaint.setColor(mOuterNormalColor);
                    canvas.drawCircle(mPoints[i][j].x, mPoints[i][j].y, mDotRadius, mNormalPaint);
                    // 后绘制内圆
                    mNormalPaint.setColor(mInnerNormalColor);
                    canvas.drawCircle(mPoints[i][j].x, mPoints[i][j].y, mDotRadius / 6, mNormalPaint);
                }

                if (mPoints[i][j].statusIsError()) {
                    // 先绘制外圆
                    mErrorPaint.setColor(mOuterErrorColor);
                    canvas.drawCircle(mPoints[i][j].x, mPoints[i][j].y, mDotRadius, mErrorPaint);
                    // 后绘制内圆
                    mErrorPaint.setColor(mInnerErrorColor);
                    canvas.drawCircle(mPoints[i][j].x, mPoints[i][j].y, mDotRadius / 6, mErrorPaint);

                    mLinePaint.setColor(mOuterErrorColor);
                }
            }
        }


        drawLine(canvas, mLinePaint);
    }

    private void drawLine(Canvas canvas, Paint paint) {
        if (selectPoints.size() >= 1) {

            Point lastPoint = selectPoints.get(0);

            for (int i = 1; i < selectPoints.size(); i++) {
                drawLine(lastPoint, selectPoints.get(i), canvas, paint);

                if (selectPoints.get(i).statusIsError()) {
                    mArrowPaint.setColor(mOuterErrorColor);
                } else {
                    mArrowPaint.setColor(mInnerPressedColor);
                }

                drawArrow(canvas, mArrowPaint, lastPoint, selectPoints.get(i), mDotRadius / 3, 45);

                lastPoint = selectPoints.get(i);
            }


            boolean isInnerPoint = checkInRound(lastPoint.x, lastPoint.y, mDotRadius / 4, mMovingX, mMovingY);
            if (!isInnerPoint && isTouchPoint) {
                drawLine(lastPoint, new Point(mMovingX, mMovingY, -1), canvas, paint);
            }
        }
    }

    /**
     * 箭头
     */
    private void drawArrow(Canvas canvas, Paint paint, Point start, Point end, float arrowHeight, int angle) {
        double d = distance(start.x, start.y, end.x, end.y);
        double sin_B = ((end.x - start.x) / d);
        double cos_B = ((end.y - start.y) / d);
        double tan_A = Math.tan(Math.toRadians(angle));
        double h = (d - arrowHeight - mDotRadius * 1.1);
        double l = arrowHeight * tan_A;
        double a = l * sin_B;
        double b = l * cos_B;
        double x0 = h * sin_B;
        double y0 = h * cos_B;
        float x1 = (float) (start.x + (h + arrowHeight) * sin_B);
        float y1 = (float) (start.y + (h + arrowHeight) * cos_B);
        float x2 = (float) (start.x + x0 - b);
        float y2 = (float) (start.y + y0 + a);
        float x3 = (float) (start.x + x0 + b);
        float y3 = (float) (start.y + y0 - a);
        Path path = new Path();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.close();
        canvas.drawPath(path, paint);

    }

    private void drawLine(Point start, Point end, Canvas canvas, Paint paint) {

        double distance = distance(start.x, start.y, end.x, end.y);

        float dx = end.x - start.x;
        float dy = end.y - start.y;

        float rx = (float) (dx / distance * (mDotRadius / 6.0));
        float ry = (float) (dy / distance * (mDotRadius / 6.0));

        canvas.drawLine(start.x + rx, start.y + ry, end.x - rx, end.y - ry, paint);

    }

    private void initPaint() {
        // 线的画笔
        mLinePaint = new Paint();
        mLinePaint.setColor(mInnerPressedColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(mDotRadius / 9);
        // 按下的画笔
        mPressedPaint = new Paint();
        mPressedPaint.setStyle(Paint.Style.STROKE);
        mPressedPaint.setAntiAlias(true);
        mPressedPaint.setStrokeWidth(mDotRadius / 6);
        // 错误的画笔
        mErrorPaint = new Paint();
        mErrorPaint.setStyle(Paint.Style.STROKE);
        mErrorPaint.setAntiAlias(true);
        mErrorPaint.setStrokeWidth(mDotRadius / 6);
        // 默认的画笔
        mNormalPaint = new Paint();
        mNormalPaint.setStyle(Paint.Style.STROKE);
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setStrokeWidth(mDotRadius / 9);
        // 箭头的画笔
        mArrowPaint = new Paint();
        mArrowPaint.setColor(mInnerPressedColor);
        mArrowPaint.setStyle(Paint.Style.FILL);
        mArrowPaint.setAntiAlias(true);

    }

    private void initDot() {
        int width = getWidth();
        int heigh = getHeight();

        int offsetY = (heigh - width) / 2;
        int spaceWidth = width / 3;
        mDotRadius = width / 12;

        mPoints[0][0] = new Point(spaceWidth / 2, offsetY + spaceWidth / 2, 0);
        mPoints[0][1] = new Point(spaceWidth * 3 / 2, offsetY + spaceWidth / 2, 1);
        mPoints[0][2] = new Point(spaceWidth * 5 / 2, offsetY + spaceWidth / 2, 2);
        mPoints[1][0] = new Point(spaceWidth / 2, offsetY + spaceWidth * 3 / 2, 3);
        mPoints[1][1] = new Point(spaceWidth * 3 / 2, offsetY + spaceWidth * 3 / 2, 4);
        mPoints[1][2] = new Point(spaceWidth * 5 / 2, offsetY + spaceWidth * 3 / 2, 5);
        mPoints[2][0] = new Point(spaceWidth / 2, offsetY + spaceWidth * 5 / 2, 6);
        mPoints[2][1] = new Point(spaceWidth * 3 / 2, offsetY + spaceWidth * 5 / 2, 7);
        mPoints[2][2] = new Point(spaceWidth * 5 / 2, offsetY + spaceWidth * 5 / 2, 8);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMovingX = event.getX();
        mMovingY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Point point = getPoint();
                if (point != null && !selectPoints.contains(point)) {
                    isTouchPoint = true;
                    selectPoints.add(point);

                    point.setStatusPressed();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouchPoint) {
                    Point pointMove = getPoint();
                    if (pointMove != null && !selectPoints.contains(pointMove)) {
                        selectPoints.add(pointMove);
                        pointMove.setStatusPressed();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isTouchPoint) {

                    if (indexListener != null) {
                        int[] index = new int[selectPoints.size()];
                        for (i = 0; i < selectPoints.size(); i++) {
                            int in = selectPoints.get(i).index;
                            index[i] = in;
                        }
                        indexListener.setIndex(index);
                    }

                }
                break;

        }

        invalidate();
        return true;
    }

    /**
     * 获取按下的点
     *
     * @return 当前按下的点
     */
    private Point getPoint() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < mPoints.length; j++) {
                // for 循环九个点，判断手指位置是否在这个九个点里面
                if (checkInRound(mPoints[i][j].x, mPoints[i][j].y, mDotRadius, mMovingX, mMovingY)) {
                    return mPoints[i][j];
                }
            }
        }
        return null;
    }

    public static boolean checkInRound(float sx, float sy, float r, float x,
                                       float y) {
        // x的平方 + y的平方 开根号 < 半径
        return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2) + Math.abs(y1 - y2) * Math.abs(y1 - y2));
    }

    private Handler handler = new Handler();

    public void setErrorOrNormal(int caseInt) {
        switch (caseInt) {
            case 2:
                for (int j = 0; j < selectPoints.size(); j++) {
                    selectPoints.get(j).setStatusError();
                }

                isTouchPoint = false;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < selectPoints.size(); i++) {
                            selectPoints.get(i).setStatusNormal();
                        }
                        selectPoints.clear();
                        invalidate();
                    }
                },2000);

                break;
            case 1:
                isTouchPoint = false;
                for (int i = 0; i < selectPoints.size(); i++) {
                    selectPoints.get(i).setStatusNormal();
                }
                selectPoints.clear();
                break;


        }
    }

    private IndexListener indexListener;

    public void setOnIndexListener(IndexListener indexListener) {
        this.indexListener = indexListener;
    }

    public interface IndexListener {
        void setIndex(int[] index);
    }

    class Point {

        public float x;
        public float y;
        public int index;

        public Point(float x, float y, int index) {
            this.x = x;
            this.y = y;
            this.index = index;
        }

        private int STATUS_NORMAL = 1;
        private int STATUS_PRESSED = 2;
        private int STATUS_ERROR = 3;
        //当前点的状态 有三种状态
        private int status = STATUS_NORMAL;

        public void setStatusPressed() {
            status = STATUS_PRESSED;
        }

        public void setStatusNormal() {
            status = STATUS_NORMAL;
        }

        public void setStatusError() {
            status = STATUS_ERROR;
        }

        public boolean statusIsPressed() {
            return status == STATUS_PRESSED;
        }

        public boolean statusIsNormal() {
            return status == STATUS_NORMAL;
        }

        public boolean statusIsError() {
            return status == STATUS_ERROR;
        }
    }
}
