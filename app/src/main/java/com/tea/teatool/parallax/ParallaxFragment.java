package com.tea.teatool.parallax;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.tea.teatool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangtea on 2019/9/11.
 */
public class ParallaxFragment extends Fragment implements LayoutInflaterFactory {

    public static final String LAYOUT_ID_KEY = "LAYOUT_ID_KEY";
    private CompatViewInflater compatViewInflater;
    private List<View> parallaxViews = new ArrayList<>();
    private int[] parallaxAttrs = new int[]{R.attr.translationXIn, R.attr.translationXOut, R.attr.translationYIn, R.attr.translationYOut};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getArguments().getInt(LAYOUT_ID_KEY);
        inflater = inflater.cloneInContext(getActivity());
        LayoutInflaterCompat.setFactory(inflater, this);
        return inflater.inflate(layoutId, container, false);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = createView(parent, name, context, attrs);
        if (view != null) {
            analysisAttrs(view, context, attrs);
        }
        return view;
    }

    private void analysisAttrs(View view, Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, parallaxAttrs);
        if (typedArray != null && typedArray.getIndexCount() != 0) {
            int index = typedArray.getIndexCount();
            ParallaxTag parallaxTag = new ParallaxTag();
            for (int i = 0; i < index; i++) {
                int attr = typedArray.getIndex(i);
                switch (attr) {
                    case 0:
                        parallaxTag.translationXIn = typedArray.getFloat(attr,0f);
                        break;
                    case 1:
                        parallaxTag.translationXOut = typedArray.getFloat(attr,0f);
                        break;
                    case 2:
                        parallaxTag.translationYIn = typedArray.getFloat(attr,0f);
                        break;
                    case 3:
                        parallaxTag.translationYOut = typedArray.getFloat(attr,0f);
                        break;
                }
            }
            view.setTag(R.id.parallax_tag,parallaxTag);
            parallaxViews.add(view);
        }
        typedArray.recycle();
    }

    private View createView(View parent, String name, Context context, AttributeSet attrs) {
        boolean isPre21 = Build.VERSION.SDK_INT < 21;
        if (compatViewInflater == null) {
            compatViewInflater = new CompatViewInflater();
        }
        boolean inheritContext = isPre21 && shouldInheritComtext((ViewParent) parent);
        return compatViewInflater.createView(parent, name, context, attrs, inheritContext, isPre21, true);
    }

    private boolean shouldInheritComtext(ViewParent parent) {
        if (parent == null) {
            return false;
        }
        while (true) {
            if (parent == null) {
                return true;
            } else if (!(parent instanceof View) || ViewCompat.isAttachedToWindow((View) parent)) {
                return false;
            }
            parent = parent.getParent();
        }
    }

    public List<View> getParallaxViews() {
        return parallaxViews;
    }
}
