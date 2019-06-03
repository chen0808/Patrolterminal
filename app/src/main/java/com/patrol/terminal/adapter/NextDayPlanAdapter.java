package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class NextDayPlanAdapter extends BaseQuickAdapter<DayListBean, BaseViewHolder> {

    private String jobType;

    public NextDayPlanAdapter(int layoutResId, @Nullable List<DayListBean> data, String state, String jovType) {
        super(layoutResId, data);
        this.jobType = jovType;
    }

    public NextDayPlanAdapter(int layoutResId, @Nullable List<DayListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DayListBean item) {
        //添加子控件的点击事件
        viewHolder.addOnClickListener(R.id.plan_to_change);

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

            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name())
                    .setText(R.id.item_line_status, "分配状态 : 未分配")
                    .setText(R.id.item_plan_content, "计划内容 : " + StringUtil.getTypeSign(item.getType_sign()));

    }

}