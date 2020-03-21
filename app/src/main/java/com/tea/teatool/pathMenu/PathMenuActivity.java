package com.tea.teatool.pathMenu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.tea.teatool.R;

import java.util.ArrayList;
import java.util.List;

public class PathMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView pathMenu;
    private ImageView pathMenuItme1;
    private ImageView pathMenuItme2;
    private ImageView pathMenuItme3;
    private ImageView pathMenuItme4;
    private ImageView pathMenuItme5;

    private boolean mIsMenuOpen = false;
    private List<ImageView> pathMenuList;
    private int radius = 600;//半径
    private int dutationAnimation = 300;//动画时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_menu);

        initView();
    }

    private void initView() {
        pathMenu = findViewById(R.id.path_menu);
        pathMenuItme1 = findViewById(R.id.path_menu_item1);
        pathMenuItme2 = findViewById(R.id.path_menu_item2);
        pathMenuItme3 = findViewById(R.id.path_menu_item3);
        pathMenuItme4 = findViewById(R.id.path_menu_item4);
        pathMenuItme5 = findViewById(R.id.path_menu_item5);
        pathMenu.setOnClickListener(this);
        pathMenuItme1.setOnClickListener(this);
        pathMenuItme2.setOnClickListener(this);
        pathMenuItme3.setOnClickListener(this);
        pathMenuItme4.setOnClickListener(this);
        pathMenuItme5.setOnClickListener(this);
        pathMenuList = new ArrayList<>();
        pathMenuList.add(pathMenuItme1);
        pathMenuList.add(pathMenuItme2);
        pathMenuList.add(pathMenuItme3);
        pathMenuList.add(pathMenuItme4);
        pathMenuList.add(pathMenuItme5);

    }

    @Override
    public void onClick(View v) {
        if (!mIsMenuOpen) {
            mIsMenuOpen = true;
            openPathMenu();
        } else {
            //Toast.makeText(this, "你点击了" + v, Toast.LENGTH_SHORT).show();
            mIsMenuOpen = false;
            closePathMenu();
        }
    }

    private void closePathMenu() {
        if (pathMenuList == null || pathMenuList.size() <= 0) {
            return;
        }
        for (int i = 0; i < pathMenuList.size(); i++) {
            if (pathMenuList.get(i).getVisibility() != View.VISIBLE) {
                pathMenuList.get(i).setVisibility(View.VISIBLE);
            }
            double degree = Math.PI * i / ((pathMenuList.size() - 1) * 2);
            int translationX = -(int) (radius * Math.sin(degree));
            int translationY = -(int) (radius * Math.cos(degree));
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ObjectAnimator.ofFloat(pathMenuList.get(i), "translationX", translationX, 0),
                    ObjectAnimator.ofFloat(pathMenuList.get(i), "translationY", translationY, 0),
                    ObjectAnimator.ofFloat(pathMenuList.get(i), "scaleX", 1f, 0.1f),
                    ObjectAnimator.ofFloat(pathMenuList.get(i), "scaleY", 1f, 0.1f),
                    ObjectAnimator.ofFloat(pathMenuList.get(i), "alpha", 1f, 0f));
            animatorSet.setDuration(dutationAnimation).start();
        }
    }

    private void openPathMenu() {
        if (pathMenuList == null || pathMenuList.size() <= 0) {
            return;
        }
        for (int i = 0; i < pathMenuList.size(); i++) {
            if (pathMenuList.get(i).getVisibility() != View.VISIBLE) {
                pathMenuList.get(i).setVisibility(View.VISIBLE);
            }
            double degree = Math.toRadians(90) / (pathMenuList.size() - 1) * i;
            int translationX = -(int) (radius * Math.sin(degree));
            int translationY = -(int) (radius * Math.cos(degree));
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ObjectAnimator.ofFloat(pathMenuList.get(i), "translationX", 0, translationX),
                    ObjectAnimator.ofFloat(pathMenuList.get(i), "translationY", 0, translationY),
                    ObjectAnimator.ofFloat(pathMenuList.get(i), "scaleX", 0f, 1f),
                    ObjectAnimator.ofFloat(pathMenuList.get(i), "scaleY", 0f, 1f),
                    ObjectAnimator.ofFloat(pathMenuList.get(i), "alpha", 0f, 1f));
            animatorSet.setInterpolator(new AccelerateInterpolator());
            animatorSet.setDuration(dutationAnimation).start();
        }
    }
}
