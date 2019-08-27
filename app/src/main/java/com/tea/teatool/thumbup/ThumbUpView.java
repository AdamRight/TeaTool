package com.tea.teatool.thumbup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tea.teatool.R;

import java.util.Random;

/**
 * Created by jiangtea on 2019/8/26.
 */
public class ThumbUpView extends RelativeLayout {

    private Random random;
    private int[] imgRes;
    private int imgWidth;
    private int imgHeight;
    private Interpolator[] interpolators;
    private int width;
    private int height;

    public ThumbUpView(Context context) {
        this(context, null);
    }

    public ThumbUpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        random = new Random();
        imgRes = new int[]{R.mipmap.pl_blue, R.mipmap.pl_red, R.mipmap.pl_yellow};

        Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.pl_blue);
        imgWidth = drawable.getIntrinsicWidth();
        imgHeight = drawable.getIntrinsicHeight();

        interpolators = new Interpolator[]{new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(),
                new DecelerateInterpolator(), new LinearInterpolator()};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    public void AddThumbUp() {
        final ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(imgRes[random.nextInt(imgRes.length - 1)]);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(CENTER_HORIZONTAL);
        imageView.setLayoutParams(params);
        addView(imageView);

        AnimatorSet animatorSet = carryAnimator(imageView);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(imageView);
            }
        });
        animatorSet.start();
    }

    private AnimatorSet carryAnimator(ImageView imageView) {
        AnimatorSet animatorSet = new AnimatorSet();

        AnimatorSet readyAnimator = new AnimatorSet();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1.0f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 0.3f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 0.3f, 1.0f);
        readyAnimator.playTogether(alphaAnimator,scaleXAnimator,scaleYAnimator);
        readyAnimator.setDuration(1000);
        
        animatorSet.playSequentially(readyAnimator,getBezierAnimator(imageView));

        return animatorSet;
    }

    private Animator getBezierAnimator(ImageView imageView) {

        return null;
    }


}
