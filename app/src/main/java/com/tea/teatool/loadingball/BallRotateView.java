package com.tea.teatool.loadingball;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;

import com.tea.teatool.R;

/**
 * Created by jiangtea on 2019/9/15.
 */
public class BallRotateView extends View {

    private int[] circleColors;
    private boolean initParams = false;
    private BallLoadingState ballLoadingState;
    private int rotationRadius;
    private int circleRadius;
    private Paint paint;
    private int centerX;
    private int centerY;
    private float mDiagonalDist;

    private final long BALL_ROTATION_TIME = 3400;
    private float currentRotationAngle = 0F;
    private int mSplashColor = Color.WHITE;
    private float currentRotationRadius = rotationRadius;
    private float holeRadius = 0F;

    public BallRotateView(Context context) {
        this(context, null);
    }

    public BallRotateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        circleColors = context.getResources().getIntArray(R.array.splash_circle_colors);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!initParams) {
            initParams();
        }
        if (ballLoadingState == null) {
            ballLoadingState = new StateRotation();
        }
        ballLoadingState.draw(canvas);
    }

    private void initParams() {
        rotationRadius = getMeasuredWidth() / 4;
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
        mDiagonalDist = (float) Math.sqrt(centerX * centerX + centerY * centerY);
        circleRadius = rotationRadius / 8;

        initParams = true;
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
    }

    public void disappear() {
        if (ballLoadingState instanceof StateRotation) {
            StateRotation stateRotation = (StateRotation) ballLoadingState;
            stateRotation.cancel();
        }
        ballLoadingState = new StateMerge();
    }

    public abstract class BallLoadingState {
        public abstract void draw(Canvas canvas);
    }

    public class StateRotation extends BallLoadingState {
        private ValueAnimator animator;

        public StateRotation() {
            animator = ObjectAnimator.ofFloat(0f, 2 * (float) Math.PI);
            animator.setDuration(BALL_ROTATION_TIME);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentRotationAngle = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(-1);
            animator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(mSplashColor);
            double percentAngle = Math.PI * 2 / circleColors.length;
            for (int i = 0; i < circleColors.length; i++) {
                paint.setColor(circleColors[i]);
                double currentAngle = percentAngle * i + currentRotationAngle;
                int cx = (int) (centerX + rotationRadius * Math.cos(currentAngle));
                int cy = (int) (centerY + rotationRadius * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, circleRadius, paint);
            }
        }

        public void cancel() {
            animator.cancel();
        }
    }

    public class StateMerge extends BallLoadingState {
        private ValueAnimator animator;
        public StateMerge() {
            animator = ObjectAnimator.ofFloat(rotationRadius, 0);
            animator.setDuration(BALL_ROTATION_TIME / 2);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentRotationRadius = (float) animation.getAnimatedValue();// 最大半径到 0
                    invalidate();
                }
            });
            animator.setInterpolator(new AnticipateInterpolator(3f));
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ballLoadingState = new ExpendState();

                }
            });
            animator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(mSplashColor);
            double percentAngle = Math.PI * 2 /circleColors.length;
            for (int i = 0; i < circleColors.length; i++) {
                paint.setColor(circleColors[i]);
                // 当前的角度 = 初始角度 + 旋转的角度
                double currentAngle = percentAngle * i + currentRotationAngle;
                int cx = (int) (centerX + currentRotationRadius * Math.cos(currentAngle));
                int cy = (int) (centerY + currentRotationRadius * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, circleRadius, paint);
            }
        }
    }

    public class ExpendState extends BallLoadingState {
        private ValueAnimator animator;
        public ExpendState() {
            animator = ObjectAnimator.ofFloat(0, mDiagonalDist);
            animator.setDuration(BALL_ROTATION_TIME / 2);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    holeRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            float strokeWidth = mDiagonalDist - holeRadius;
            paint.setStrokeWidth(strokeWidth);
            paint.setColor(mSplashColor);
            paint.setStyle(Paint.Style.STROKE);
            Log.e("TAG", "mHoleRadius -> " + holeRadius);
            float radius = strokeWidth / 2 + holeRadius;
            // 绘制一个圆
            canvas.drawCircle(centerX, centerY, radius, paint);
        }
    }
}
