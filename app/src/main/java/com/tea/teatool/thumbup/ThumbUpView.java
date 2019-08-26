package com.tea.teatool.thumbup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.tea.teatool.R;

import java.util.Random;

/**
 * Created by jiangtea on 2019/8/26.
 */
public class ThumbUpView extends RelativeLayout {

    private Random random;
    private int[] imgRes;

    public ThumbUpView(Context context) {
        this(context, null);
    }

    public ThumbUpView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ThumbUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        random = new Random();
        imgRes = new int[]{R.mipmap.pl_blue, R.mipmap.pl_red, R.mipmap.pl_yellow};

    }

    public void AddThumbUp(){

    }
}
