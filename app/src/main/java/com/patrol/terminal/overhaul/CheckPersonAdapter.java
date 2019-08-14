package com.patrol.terminal.overhaul;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.CheckProjectBean;
import com.patrol.terminal.bean.UserBean;

import java.util.List;

import androidx.annotation.Nullable;

public class CheckPersonAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {
    //private List<CheckProjectBean> mData;

    public CheckPersonAdapter(int layoutResId, @Nullable List<UserBean> data) {
        super(layoutResId, data);
        //this.mData = data;

    }

    @Nullable
    @Override
    public UserBean getItem(int position) {
        if (mData != null) {
            return mData.get(position);
        }
        return super.getItem(position);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, UserBean item) {
        viewHolder.setText(R.id.project_tv, item.getName());
    }

}