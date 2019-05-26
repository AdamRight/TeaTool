package com.tea.teatool.cliptext;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tea.teatool.R;

import java.util.ArrayList;
import java.util.List;

public class ClipTextActivity extends AppCompatActivity {

    private String[] items = {"推荐", "电影", "美女", "国际", "财经", "搞笑"};
    private LinearLayout mIndicatorContainer;// 变成通用的
    private List<ClipTextView> mIndicators;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_text);

        mIndicators = new ArrayList<>();
        mIndicatorContainer = findViewById(R.id.indicator_view);
        mViewPager = findViewById(R.id.view_pager);
        initIndicator();
        initViewPager();
    }
    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ClipTextView left = mIndicators.get(position);
                left.setDirection(ClipTextView.Direction.RIGHT_TO_LEFT);//原先黑，右到左变红
                left.setCurrentProgress(1 - positionOffset);
                Log.e("TAG", "left -> " + left.getText().toString());

                try {
                    ClipTextView right = mIndicators.get(position + 1);
                    right.setDirection(ClipTextView.Direction.LEFT_TO_RIGHT);//原先黑，左到右变红
                    right.setCurrentProgress(positionOffset);
                    Log.e("TAG", "right -> " + right.getText().toString());
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

    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            // 动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ClipTextView clipText = new ClipTextView(this);
            // 设置颜色
            clipText.setTextSize(20);
            clipText.setChangeColor(Color.RED);
            clipText.setText(items[i]);
            clipText.setLayoutParams(params);
            // 把新的加入LinearLayout容器
            mIndicatorContainer.addView(clipText);
            // 加入集合
            mIndicators.add(clipText);
        }
    }
}
