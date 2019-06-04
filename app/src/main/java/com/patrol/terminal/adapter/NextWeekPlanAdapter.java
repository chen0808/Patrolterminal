package com.patrol.terminal.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.WeekListBean;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

import androidx.annotation.Nullable;

public class NextWeekPlanAdapter extends BaseQuickAdapter<WeekListBean, BaseViewHolder> {

    private String jobType;

    public NextWeekPlanAdapter(int layoutResId, @Nullable List<WeekListBean> data, String state, String jovType) {
        super(layoutResId, data);
        this.jobType = jovType;
    }

    public NextWeekPlanAdapter(int layoutResId, @Nullable List<WeekListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, WeekListBean item) {
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

            viewHolder.setGone(R.id.item_line_state, false);
            HorizontalLineView horizontalLineView = viewHolder.getView(R.id.item_plan_status);
            horizontalLineView.setStatus(item.getAudit_status());
            horizontalLineView.setVisibility(View.VISIBLE);
            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name())
                    .setText(R.id.item_line_status, "审核状态 :"+StringUtil.getYXBstate(item.getAudit_status()))
                    .setText(R.id.item_plan_content, "计划内容 : " + StringUtil.getTypeSign(item.getType_sign()));

    }

}