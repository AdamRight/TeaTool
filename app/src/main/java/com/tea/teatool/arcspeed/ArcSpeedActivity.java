package com.tea.teatool.arcspeed;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;

import com.tea.teatool.R;

public class ArcSpeedActivity extends AppCompatActivity {

    private ArcSpeedView arcSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_are_speed);
        arcSpeed = findViewById(R.id.arc_speed);
        arcSpeed.setTotalSize(100);

        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 80);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                arcSpeed.setCurrentSize((int)animatedValue);
            }
        });
        valueAnimator.start();

    }
}
