package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TroubleDetailBean;

import java.util.List;

public class DisasterAdapter extends BaseQuickAdapter<TroubleDetailBean, BaseViewHolder> {
    public DisasterAdapter(int layoutResId, @Nullable List<TroubleDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TroubleDetailBean item) {
        helper.setText(R.id.tv_type_name, item.getType_name())
                .setText(R.id.tv_dep_name, item.getDep_name())
                .setText(R.id.tv_line_level, item.getLine_level())
                .setText(R.id.line_name, item.getLine_name())
                .setText(R.id.tv_find_time, item.getFind_time())
                .setText(R.id.tv_tower_number, item.getTower_number())
                .setText(R.id.tv_towers, item.getTowers())
                .setText(R.id.tv_status, item.getStatus().equals("0") ? "未完成" : "已完成")
                .setText(R.id.tv_remarks, item.getRemarks() == null ? "" : item.getRemarks())
                .setText(R.id.tv_done_time, item.getDone_time())
                .setText(R.id.tv_plan_time, item.getPlan_time())
                .setText(R.id.tv_final_deal, item.getFinal_deal())
                .setText(R.id.tv_deal_notes, item.getDeal_notes())
                .setText(R.id.tv_scale, item.getScale())
                .setText(R.id.tv_main_reason, item.getMain_reason())
                .setText(R.id.tv_join_reason, item.getJoin_reason())
                .setText(R.id.tv_disaster_type, item.getDisaster_type());
    }
}
