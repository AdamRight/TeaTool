package com.tea.teatool.rv.common;

import android.content.Context;

import com.tea.teatool.R;

import java.util.List;

/**
 * Created by jiangtea on 2019/11/18.
 */
public class LettersAdapter extends CommonRecyclerAdapter<String> {

    public LettersAdapter(List<String> mData, Context mContext) {
        super(R.layout.base_use_item_rv, mData, mContext);
    }

    @Override
    protected void onBindData(CommonViewHolder holder, String s, int position) {
//        TextView letterTv = holder.getView(R.id.item_rv_tv);
//        letterTv.setText(s);
        holder.setText(R.id.item_rv_tv,s);
    }
}
