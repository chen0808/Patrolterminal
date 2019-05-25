package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;

import java.util.List;

import androidx.annotation.Nullable;

public class PerformanceAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public PerformanceAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        helper.setText(R.id.tv_month, "2019年" + (helper.getLayoutPosition() + 1) + "月");
    }
}
