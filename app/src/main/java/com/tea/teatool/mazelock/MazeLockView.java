package com.tea.teatool.mazelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Admin on 2019/6/10.
 */

public class MazeLockView extends View {

    private boolean isInit = false;

    private int mDotRadius = 0;

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
                // 先绘制外圆
                mNormalPaint.setColor(mOuterNormalColor);
                canvas.drawCircle(mPoints[i][j].x, mPoints[i][j].y, mDotRadius, mNormalPaint);
                // 后绘制内圆
                mNormalPaint.setColor(mInnerNormalColor);
                canvas.drawCircle(mPoints[i][j].x, mPoints[i][j].y, mDotRadius / 6, mNormalPaint);
            }
        }
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
        mPoints[1][0] = new Point(spaceWidth / 2, offsetY + spaceWidth * 3/ 2, 3);
        mPoints[1][1] = new Point(spaceWidth * 3 / 2, offsetY + spaceWidth * 3/ 2, 4);
        mPoints[1][2] = new Point(spaceWidth * 5 / 2, offsetY + spaceWidth * 3/ 2, 5);
        mPoints[2][0] = new Point(spaceWidth / 2, offsetY + spaceWidth * 5/ 2, 6);
        mPoints[2][1] = new Point(spaceWidth * 3 / 2, offsetY + spaceWidth * 5/ 2, 7);
        mPoints[2][2] = new Point(spaceWidth * 5 / 2, offsetY + spaceWidth * 5/ 2, 8);

    }

    class Point {
        public int x, y,index;
        public Point(int x, int y, int index) {
            this.x = x ;
            this.y = y;
            this.index = index;
        }

        private int STATUS_NORMAL = 1;
        private int STATUS_PRESSED = 2;
        private int STATUS_ERROR = 3;
        //当前点的状态 有三种状态
        private int status = STATUS_NORMAL;
    }
}
