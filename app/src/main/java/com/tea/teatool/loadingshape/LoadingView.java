package com.tea.teatool.loadingshape;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.tea.teatool.R;

/**
 * Created by Admin on 2019/6/23.
 */

public class LoadingView extends LinearLayout {

    private int translationDistance;
    private ShapeView shapeView;
    private View shadowView;

    private final long ANIMATOR_DURATION = 350;

    private boolean isStopAnimator = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        translationDistance = dp2Px(80);
        initView();

    }

    private void initView() {
        // this 代表把 ui_loading_view 加载到 LoadingView 中
        inflate(getContext(), R.layout.loading_view, this);
//         View loadView = inflate(getContext(),R.layout.loading_view,null);
        // 1.2 添加到该View
//         addView(loadView);
        shapeView = (ShapeView) findViewById(R.id.shape_view);
        shadowView = findViewById(R.id.shadow_view);

        post(new Runnable() {
            @Override
            public void run() {
                startFallAnimator();
            }
        });
    }

    private void startFallAnimator() {
        if (isStopAnimator) {
            return;
        }
        ObjectAnimator translationShape = ObjectAnimator.ofFloat(shapeView, "translationY", 0, translationDistance);
        ObjectAnimator scaleShadow = ObjectAnimator.ofFloat(shadowView, "scaleX", 1f, 0.3f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATOR_DURATION);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(translationShape, scaleShadow);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                shapeView.exchange();
                startUpAnimator();
            }
        });

    }

    private void startUpAnimator() {
        if (isStopAnimator) {
            return;
        }

        ObjectAnimator translationShape = ObjectAnimator.ofFloat(shapeView, "translationY", translationDistance, 0);
        ObjectAnimator scaleShadow = ObjectAnimator.ofFloat(shadowView, "scaleX", 0.3f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATOR_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(translationShape, scaleShadow);

        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                startFallAnimator();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                startRotationAnimator();
            }
        });

        animatorSet.start();
    }

    private void startRotationAnimator() {
        ObjectAnimator rotationAnimator = null;
        switch (shapeView.getCurrentShape()) {
            case Circle:
            case Square:
                rotationAnimator = ObjectAnimator.ofFloat(shapeView, "rotation", 0, 180);
                break;
            case Triangle:
                rotationAnimator = ObjectAnimator.ofFloat(shapeView, "rotation", 0, -120);
                break;
        }
        rotationAnimator.setDuration(ANIMATOR_DURATION);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.start();
    }

    private int dp2Px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.INVISIBLE);

        shapeView.clearAnimation();
        shadowView.clearAnimation();
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
            removeAllViews();
        }
        isStopAnimator = true;
    }
}
