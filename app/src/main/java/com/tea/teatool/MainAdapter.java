package com.tea.teatool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jiangtea on 2018/12/2.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<MainListBean> mDatas;
    private OnItemClickListener mItemClickListener;

    public MainAdapter(Context context, ArrayList<MainListBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
//        错误写法
//        LayoutInflater.from(context).inflate(R.layout.item_view,null);
        View view = LayoutInflater.from(mContext).inflate( R.layout.item_layout, parent,false);
        RecyclerView.ViewHolder viewHolder = new NormalHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mTV.setText(mDatas.get(position).getItemName());
        normalHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) view.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public class NormalHolder extends RecyclerView.ViewHolder {
        public TextView mTV;

        public NormalHolder(View itemView) {
            super(itemView);

            mTV = itemView.findViewById(R.id.item_tv);

        }
    }
}
