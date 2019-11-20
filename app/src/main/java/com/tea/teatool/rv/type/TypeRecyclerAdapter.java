package com.tea.teatool.rv.type;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.tea.teatool.R;
import com.tea.teatool.rv.common.CommonRecyclerAdapter;
import com.tea.teatool.rv.common.CommonViewHolder;
import com.tea.teatool.rv.common.MulitiTypeSupport;

import java.util.List;

/**
 * Created by jiangtea on 2019/11/20.
 */
public class TypeRecyclerAdapter extends CommonRecyclerAdapter<TypeBean> {

    public TypeRecyclerAdapter(final List<TypeBean> mData, final Context mContext) {
        super(mData, mContext, new MulitiTypeSupport<TypeBean>() {
            @Override
            public int getLayoutId(TypeBean item) {
                //判断，根据item，加载不同布局,这里假装两种布局
                return R.layout.base_use_item_rv;
            }
        });
    }

    @Override
    protected void onBindData(CommonViewHolder holder, TypeBean item, int position) {
        //这里假装处理两种布局
        TextView textView = holder.getView(R.id.item_rv_tv);
        if (item.getNum() % 2 == 0) {
            textView.setGravity(Gravity.RIGHT);
        } else {
            textView.setGravity(Gravity.LEFT);
        }
        holder.setText(R.id.item_rv_tv, item.getLetter());


    }
}
