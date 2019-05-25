package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.OvaTodoBean;

import java.util.List;

public class BackLogAdapter extends BaseQuickAdapter<OvaTodoBean, BaseViewHolder> {


    public BackLogAdapter(int layoutResId, @Nullable List<OvaTodoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OvaTodoBean item) {
        if (item.getPlanRepair() != null) {
            helper.setText(R.id.tv_title, item.getPlanRepair().getRepair_content());
        }
    }
}
