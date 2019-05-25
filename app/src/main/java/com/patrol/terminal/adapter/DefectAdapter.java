package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.bean.DefectDetailBean;

import java.util.List;

public class DefectAdapter extends BaseQuickAdapter<List<DefectDetailBean>, BaseViewHolder> {
    public DefectAdapter(int layoutResId, @Nullable List<List<DefectDetailBean>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, List<DefectDetailBean> item) {

    }
}
