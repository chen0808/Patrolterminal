package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TroubleDetailBean;

import java.util.List;

public class WindAdapter extends BaseQuickAdapter<TroubleDetailBean, BaseViewHolder> {
    public WindAdapter(int layoutResId, @Nullable List<TroubleDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TroubleDetailBean item) {
        helper.setText(R.id.tv_type_name, "隐患名字：" + item.getType_name())
                .setText(R.id.tv_dep_name, "班组名字：" + item.getDep_name())
                .setText(R.id.tv_line_level, "线路等级：" + item.getLine_level())
                .setText(R.id.tv_line_name, "线路名字：" + item.getLine_name())
                .setText(R.id.tv_find_time, "发现时间：" + item.getFind_time())
                .setText(R.id.tv_tower_number, "基数：" + item.getTower_number())
                .setText(R.id.tv_towers, "杆塔号：" + item.getTowers())
                .setText(R.id.tv_status, "完成状态：" + (item.getStatus().equals("0") ? "未完成" : "已完成"))
                .setText(R.id.tv_remarks, "备注：" + (item.getRemarks() == null ? "" : item.getRemarks()))
                .setText(R.id.tv_plan_time, "计划时间：" + item.getPlan_time())
                .setText(R.id.tv_deal_notes, "处理措施：" + item.getDeal_notes());
    }
}
