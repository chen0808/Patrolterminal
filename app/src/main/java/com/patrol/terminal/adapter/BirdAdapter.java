package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TroubleDetailBean;

import java.util.List;

public class BirdAdapter extends BaseQuickAdapter<TroubleDetailBean, BaseViewHolder> {
    public BirdAdapter(int layoutResId, @Nullable List<TroubleDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TroubleDetailBean item) {
        helper.setText(R.id.tv_type_name, item.getType_name())
                .setText(R.id.tv_dep_name, item.getDep_name())
                .setText(R.id.tv_line_level, item.getLine_level())
                .setText(R.id.tv_line_name, item.getLine_name())
                .setText(R.id.tv_find_time, item.getFind_time())
                .setText(R.id.tv_tower_number, item.getTower_number())
                .setText(R.id.tv_towers, item.getTowers())
                .setText(R.id.tv_remarks, item.getRemarks() == null ? "" : item.getRemarks())
                .setText(R.id.tv_plan_time, item.getPlan_time())
                .setText(R.id.tv_installed, item.getInstalled().equals("0") ? "否" : "是")
                .setText(R.id.tv_arrival_time, item.getArrival_time())
                .setText(R.id.tv_orders_time, item.getOrders_time())
                .setText(R.id.tv_orders_company, item.getOrders_company())
                .setText(R.id.tv_batch, item.getBatch())
                .setText(R.id.tv_category, item.getCategory())
                .setText(R.id.tv_year, item.getYear())
                .setText(R.id.tv_total, item.getTotal())
                .setText(R.id.tv_deal_notes, item.getDeal_notes());
    }
}
