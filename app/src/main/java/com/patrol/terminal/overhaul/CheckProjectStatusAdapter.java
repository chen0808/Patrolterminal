package com.patrol.terminal.overhaul;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.CheckProjectStatusBean;

import java.util.List;

public class CheckProjectStatusAdapter extends BaseQuickAdapter<CheckProjectStatusBean, BaseViewHolder> {

    public CheckProjectStatusAdapter(int layoutResId, @Nullable List<CheckProjectStatusBean> data, int type) {
        super(layoutResId, data);
    }

    @Nullable
    @Override
    public CheckProjectStatusBean getItem(int position) {
        if (mData != null) {
            return mData.get(position);
        }
        return super.getItem(position);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CheckProjectStatusBean item) {
        viewHolder.setText(R.id.project_tv, item.getName());
    }

}