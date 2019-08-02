package com.tea.teatool.circleload;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.tea.teatool.utils.ScreenUtils;

/**
 * Created by jiangtea on 2019/8/2.
 */
public class CircleLoadingView extends RelativeLayout {

    private final long ANIMATOR_TIME = 350;
    private int circleDistance;

    private LittleCircleView leftCircle, middleCircle, rightCircle;
    private AnimatorSet animatorInSet;
    private AnimatorSet animatorOutSet;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        circleDistance = ScreenUtils.dip2px(context, 30);
        setBackgroundColor(Color.WHITE);

        leftCircle = getCircleView(context);
        leftCircle.exchangeColor(Color.CYAN);
        addView(leftCircle);

        rightCircle = getCircleView(context);
        rightCircle.exchangeColor(Color.RED);
        addView(rightCircle);

        middleCircle = getCircleView(context);
        middleCircle.exchangeColor(Color.MAGENTA);
        addView(middleCircle);

        post(new Runnable() {
            @Override
            public void run() {
                startOutAnimation();
            }
        });

    }

    private LittleCircleView getCircleView(Context context) {
        LittleCircleView littleCircleView = new LittleCircleView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ScreenUtils.dip2px(context, 10), ScreenUtils.dip2px(context, 10));
        layoutParams.addRule(CENTER_IN_PARENT);
        littleCircleView.setLayoutParams(layoutParams);
        return littleCircleView;
    }

    private void startOutAnimation() {

        ObjectAnimator leftTranslation = ObjectAnimator.ofFloat(leftCircle, "translationX", 0, -circleDistance);
        ObjectAnimator rightTranslation = ObjectAnimator.ofFloat(rightCircle, "translationX", 0, circleDistance);

        animatorOutSet = new AnimatorSet();
        animatorOutSet.setDuration(ANIMATOR_TIME);
        animatorOutSet.playTogether(leftTranslation, rightTranslation);
        animatorOutSet.setInterpolator(new DecelerateInterpolator());
        animatorOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startInAnimation();
            }
        });
        animatorOutSet.start();
    }

    private void startInAnimation() {
        final ObjectAnimator leftTranslation = ObjectAnimator.ofFloat(leftCircle, "translationX", -circleDistance, 0);
        ObjectAnimator rightTranslation = ObjectAnimator.ofFloat(rightCircle, "translationX", circleDistance, 0);

        animatorInSet = new AnimatorSet();
        animatorInSet.setDuration(ANIMATOR_TIME);
        animatorInSet.playTogether(leftTranslation, rightTranslation);
        animatorInSet.setInterpolator(new AccelerateInterpolator());
        animatorInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int leftColor = leftCircle.getCircleColor();
                int rightColor = rightCircle.getCircleColor();
                int middleColor = middleCircle.getCircleColor();

                middleCircle.exchangeColor(leftColor);
                rightCircle.exchangeColor(middleColor);
                leftCircle.exchangeColor(rightColor);
                startOutAnimation();
            }
        });
        animatorInSet.start();
    }

    public void loadingStop(){
        if (animatorInSet != null && animatorInSet.isRunning()){
            animatorInSet.cancel();
            animatorInSet = null;
        }

        if (animatorOutSet != null && animatorOutSet.isRunning()){
            animatorOutSet.cancel();
            animatorOutSet = null;
        }
    }
}
