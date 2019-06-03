package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class MonthPlanAdapter extends BaseQuickAdapter<MonthPlanBean, BaseViewHolder> {

    private String state;
    private String jobType;

    public MonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data, String state, String jovType) {
        super(layoutResId, data);
        this.state = state;
        this.jobType = jovType;
    }

    public MonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, MonthPlanBean item) {
        //添加子控件的点击事件
        viewHolder.addOnClickListener(R.id.plan_to_change)
                .addOnClickListener(R.id.plan_deal);
        //判断是检修计划还是运行计划
        if (item.getRepair_content() == null) {
            switch (item.getType_sign()) {
                case "4":
                    viewHolder.setText(R.id.item_plan_date_tv, "特殊");
                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_week_bg);
                    break;
                case "7":
                    viewHolder.setText(R.id.item_plan_date_tv, "保电");
                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_qing_bg);
                    break;
                case "2":
                    viewHolder.setText(R.id.item_plan_date_tv, "故障");
                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_yellow_bg);
                    break;
                case "1":
                    viewHolder.setText(R.id.item_plan_date_tv, "定巡");
                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_mon_bg);
                    break;
                default:
                    viewHolder.setText(R.id.item_plan_date_tv, "定检");
                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_day_bg);
                    break;
            }
            //判断分配状态
            if ("0".equals(item.getAllot_status())) {
                viewHolder.setText(R.id.item_line_status, "分配状态 : 未分配");
            } else {
                viewHolder.setText(R.id.item_line_status, "分配状态 : 已分配");
            }
            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name())
                    .setText(R.id.item_plan_content, "计划内容 : " + StringUtil.getTypeSign(item.getType_sign()));
        } else {

            viewHolder.setText(R.id.item_plan_date_tv, "保")
                    .setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_green_bg);
            //判断分配状态
            if ("0".equals(item.getAllot_status())) {
                viewHolder.setVisible(R.id.plan_to_change, true);
                viewHolder.setText(R.id.item_plan_content, "分配状态 : 未分配");
            } else {
                viewHolder.setVisible(R.id.plan_to_change, false);
                viewHolder.setText(R.id.item_plan_content, "分配状态 : 已分配");
            }
            viewHolder.setText(R.id.item_plan_device_name, "基于" + item.getLine_name() + "的保电计划")
                    .setText(R.id.item_line_status, "停电时间 : " + item.getStart_time() + " - " + item.getEnd_time());

        }
    }

}