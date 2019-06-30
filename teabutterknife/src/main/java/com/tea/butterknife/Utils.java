package com.tea.butterknife;

import android.app.Activity;
import android.view.View;

/**
 * Created by jiangtea on 2019/6/30.
 */
public class Utils {
    public static final <T extends View> T findViewById(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }
}
