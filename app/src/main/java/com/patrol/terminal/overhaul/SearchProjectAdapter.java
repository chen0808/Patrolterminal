package com.patrol.terminal.overhaul;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.CheckProjectServiceBean;
import com.patrol.terminal.bean.InitiateProjectBean;
import com.patrol.terminal.bean.InitiateProjectBean2;

import java.util.List;

import androidx.annotation.Nullable;

public class SearchProjectAdapter extends BaseQuickAdapter<InitiateProjectBean2, BaseViewHolder> {
    //private List<CheckProjectBean> mData;

    public SearchProjectAdapter(int layoutResId, @Nullable List<InitiateProjectBean2> data) {
        super(layoutResId, data);
        //this.mData = data;

    }

    @Nullable
    @Override
    public InitiateProjectBean2 getItem(int position) {
        if (mData != null) {
            return mData.get(position);
        }
        return super.getItem(position);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, InitiateProjectBean2 item) {
        viewHolder.setText(R.id.project_tv, item.getName());

    }

}