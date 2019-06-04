package com.tea.teatool.flow;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 2019/6/4.
 */

public abstract class BaseFlowAdapter {

    public abstract int getCount();

    public abstract View getView(int position, ViewGroup parent);


}
