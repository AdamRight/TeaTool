package com.tea.teatool.rv.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jiangtea on 2019/11/18.
 */
public abstract class CommonRecyclerAdapter<DATA> extends RecyclerView.Adapter<CommonViewHolder> {

    private int mLayoutId;
    private List<DATA> mData;
    private Context mContext;
    private LayoutInflater mInflater;

    public CommonRecyclerAdapter(int mLayoutId, List<DATA> mData, Context mContext) {
        this.mLayoutId = mLayoutId;
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mLayoutId, parent, false);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, final int position) {
        onBindData(holder, mData.get(position), position);

        if (mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(position);
                }
            });
        }

        if (mItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mItemLongClickListener.onItemLongClick(position);
                }
            });
        }
    }

    protected abstract void onBindData(CommonViewHolder holder, DATA data, int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;

    public void setOnItemClickListener(ItemClickListener temClickListener) {
        this.mItemClickListener = temClickListener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;
    }
}
