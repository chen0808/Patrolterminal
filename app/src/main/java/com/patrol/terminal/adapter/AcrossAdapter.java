package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TroubleDetailBean;

import java.util.List;

public class AcrossAdapter extends BaseQuickAdapter<TroubleDetailBean, BaseViewHolder> {
    public AcrossAdapter(int layoutResId, @Nullable List<TroubleDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TroubleDetailBean item) {
        helper.setText(R.id.tv_type_name, item.getType_name())
                .setText(R.id.tv_dep_name, item.getDep_name() == null ? "" : item.getDep_name())
                .setText(R.id.tv_across_name, item.getAcross_name())
                .setText(R.id.tv_small_tower_name, item.getSmall_tower_name())
                .setText(R.id.tv_big_tower_name, item.getBig_tower_name())
                .setText(R.id.tv_tension, item.getTension())
                .setText(R.id.tv_is_independent, item.getIs_independent().equals("0") ? "否" : "是")
                .setText(R.id.tv_is_double, item.getIs_double().equals("0") ? "否" : "是")
                .setText(R.id.tv_is_drainage, item.getIs_drainage().equals("0") ? "否" : "是")
                .setText(R.id.tv_is_metal, item.getIs_metal().equals("0") ? "否" : "是")
                .setText(R.id.tv_is_video, item.getIs_video().equals("0") ? "否" : "是")
                .setText(R.id.tv_is_adss, item.getIs_adss().equals("0") ? "否" : "是")
                .setText(R.id.tv_remarks, item.getRemarks() == null ? "" : item.getRemarks())
                .setText(R.id.tv_status, item.getStatus().equals("0") ? "未完成" : "已完成");
    }
}
