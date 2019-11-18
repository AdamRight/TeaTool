package com.tea.teatool.rv.common;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jiangtea on 2019/11/18.
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mView;

    public CommonViewHolder(View itemView) {
        super(itemView);
        mView = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = mView.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mView.put(viewId, view);
        }
        return (T)view;
    }

    /**
     * 通用方法设置，设置TextView
     */
    public CommonViewHolder setText(int viewId,CharSequence text){
        TextView textview = getView(viewId);
        textview.setText(text);
        return this;
    }
}
