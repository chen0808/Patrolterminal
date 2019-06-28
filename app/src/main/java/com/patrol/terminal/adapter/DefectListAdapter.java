package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DefectListBean;

import java.util.List;

public class DefectListAdapter extends BaseQuickAdapter<DefectListBean, BaseViewHolder> {
    public DefectListAdapter(int layoutResId, @Nullable List<DefectListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DefectListBean item) {
        helper.setText(R.id.tv, item.getGrade_name() + "   " + item.getContent());
    }
}
