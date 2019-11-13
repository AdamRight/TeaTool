package com.tea.teatool.rv.baseuse;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tea.teatool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangtea on 2019/11/13.
 */
public class BaseUseRecyclerAdapter extends RecyclerView.Adapter<BaseUseRecyclerAdapter.ViewHolder> {

    private List<String> data;
    private Context context;

    public BaseUseRecyclerAdapter(ArrayList<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.base_use_item_rv,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView itemTv;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTv = itemView.findViewById(R.id.item_rv_tv);
        }
    }
}
