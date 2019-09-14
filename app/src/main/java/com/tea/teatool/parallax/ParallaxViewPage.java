package com.tea.teatool.parallax;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.tea.teatool.R;

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

    public void setLayout(FragmentManager fm, int[] layoutIds) {
        parallaxFragment.clear();
        // 实例化Fragment
        for (int layoutId : layoutIds) {
            ParallaxFragment fragment = new ParallaxFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY, layoutId);
            fragment.setArguments(bundle);
            parallaxFragment.add(fragment);
        }
        setAdapter(new ParallaxPagerAdapter(fm));

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ParallaxFragment outFragment = parallaxFragment.get(position);
                List<View> parallaxViews = outFragment.getParallaxViews();
                for (View parallaxView : parallaxViews) {
                    ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                    parallaxView.setTranslationX((-positionOffsetPixels) * tag.translationXOut);
                    parallaxView.setTranslationY((-positionOffsetPixels) * tag.translationYOut);
                }
                try {
                    ParallaxFragment inFragment = parallaxFragment.get(position + 1);
                    parallaxViews = inFragment.getParallaxViews();
                    for (View parallaxView : parallaxViews) {
                        ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                        parallaxView.setTranslationX((getMeasuredWidth() - positionOffsetPixels) * tag.translationXIn);
                        parallaxView.setTranslationY((getMeasuredWidth() - positionOffsetPixels) * tag.translationYIn);
                    }
                } catch (Exception e) {
                }

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ParallaxPagerAdapter extends FragmentPagerAdapter {

        public ParallaxPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return parallaxFragment.get(position);
        }

        @Override
        public int getCount() {
            return parallaxFragment.size();
        }
    }
}
