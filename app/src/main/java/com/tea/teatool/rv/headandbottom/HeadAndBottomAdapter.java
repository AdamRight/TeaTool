package com.tea.teatool.rv.headandbottom;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jiangtea on 2019/11/24.
 */
public class HeadAndBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter mAdapter;

    private SparseArray<View> mHeadViews, mBottomViews;

    private static int BASE_HEADER_KEY = 10000000;
    private static int BASE_BOTTOM_KEY = 20000000;

    public HeadAndBottomAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mHeadViews = new SparseArray<>();
        mBottomViews = new SparseArray<>();
    }

    @Override
    public int getItemViewType(int position) {

        int headSize = mHeadViews.size();
        if (headSize > position) {
            return mHeadViews.keyAt(position);
        }

        int adapterPosition = position - headSize;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adapterCount > adapterPosition) {
                //注意
                return mAdapter.getItemViewType(adapterPosition);
            }
        }
        return mBottomViews.keyAt(adapterPosition - adapterCount);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (mHeadViews.indexOfKey(viewType) >= 0) {
            return createHeadAndBottomView(mHeadViews.get(viewType));
        } else if (mBottomViews.indexOfKey(viewType) >= 0) {
            return createHeadAndBottomView(mBottomViews.get(viewType));
        }

        return mAdapter.createViewHolder(parent, viewType);
    }

    private RecyclerView.ViewHolder createHeadAndBottomView(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int headSize = mHeadViews.size();
        if (headSize > position) {
            return;
        }

        int adapterPosition = position - headSize;
        int adapterCount;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adapterCount > adapterPosition) {
                //注意
                mAdapter.onBindViewHolder(holder, adapterPosition);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mHeadViews.size() + mBottomViews.size();
    }

    public void addHeadView(View view) {
        if (mHeadViews.indexOfValue(view) == -1) {
            mHeadViews.put(BASE_HEADER_KEY++, view);
            notifyDataSetChanged();
        }
    }

    public void removeHeadView(View view) {
        if (mHeadViews.indexOfValue(view) >= 0) {
            mHeadViews.removeAt(mHeadViews.indexOfValue(view));
            notifyDataSetChanged();
        }
    }

    public void addBottomView(View view) {
        if (mBottomViews.indexOfValue(view) == -1) {
            mBottomViews.put(BASE_BOTTOM_KEY++, view);
            notifyDataSetChanged();
        }
    }

    public void removeBottomView(View view) {
        if (mBottomViews.indexOfValue(view) >= 0) {
            mBottomViews.removeAt(mBottomViews.indexOfValue(view));
            notifyDataSetChanged();
        }
    }
}
