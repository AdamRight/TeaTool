package com.tea.teatool.parallax;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangtea on 2019/9/11.
 */
public class ParallaxViewPage extends ViewPager {

    private List<ParallaxFragment> parallaxFragment;

    public ParallaxViewPage(@NonNull Context context) {
        this(context, null);
    }

    public ParallaxViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parallaxFragment = new ArrayList<>();
    }
}
