package com.tea.teatool.iocannotate;

import android.app.Activity;
import android.view.View;

/**
 * Created by jiangtea on 2019/9/17.
 */
public class FinderView {
    private Activity activity;
    private View view;

    public FinderView(Activity activity) {
        this.activity = activity;
    }

    public FinderView(View view) {
        this.view = view;
    }

    public View findViewById(int viewId){
        return activity != null ? activity.findViewById(viewId) : view.findViewById(viewId);
    }
}
