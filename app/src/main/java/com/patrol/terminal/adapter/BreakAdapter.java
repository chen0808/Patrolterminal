package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TroubleDetailBean;

import java.util.List;

public class BreakAdapter extends BaseQuickAdapter<TroubleDetailBean, BaseViewHolder> {
    public BreakAdapter(int layoutResId, @Nullable List<TroubleDetailBean> data) {
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
                .setText(R.id.tv_is_video, item.getIs_video().equals("0") ? "否" : "是")
                .setText(R.id.tv_deal_notes_first, item.getDeal_notes_first() == null ? "" : item.getDeal_notes_first())
                .setText(R.id.tv_deal_notes_second, item.getDeal_notes_second() == null ? "" : item.getDeal_notes_second())
                .setText(R.id.tv_deal_notes_third, item.getDeal_notes_third() == null ? "" : item.getDeal_notes_third())
                .setText(R.id.tv_deal_notes_fourth, item.getDeal_notes_fourth() == null ? "" : item.getDeal_notes_fourth())
                .setText(R.id.tv_deal_notes_fifth, item.getDeal_notes_fifth() == null ? "" : item.getDeal_notes_fifth())
                .setText(R.id.tv_duty_unit, item.getDuty_unit())
                .setText(R.id.tv_trouble_wares, item.getTrouble_wares())
                .setText(R.id.tv_content, item.getContent());
    }
}
