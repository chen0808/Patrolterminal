package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddMonthPlanActivity;
import com.patrol.terminal.bean.WeekListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

import androidx.annotation.Nullable;

public class WeekPlanAdapter extends BaseQuickAdapter<WeekListBean, BaseViewHolder> {

    public WeekPlanAdapter(int layoutResId, @Nullable List<WeekListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, WeekListBean item) {

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

        if ("0".equals(item.getAudit_status())) {
            viewHolder.setVisible(R.id.plan_to_change, true);
        } else {
            viewHolder.setVisible(R.id.plan_to_change, false);
        }
        if ("1".equals(item.getAllot_status())) {
            viewHolder.setGone(R.id.plan_progressbar_ll, true);
            viewHolder.setGone(R.id.plan_progressbar_ll, true);
            viewHolder.setText(R.id.plan_progressbar_tv, "计划进度(" + item.getDone_num() + "/" + item.getAll_num() + ") :")
                    .setText(R.id.plan_progressbar_num, item.getDone_rate() + "%");
            ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
            progressBar.setMax(item.getAll_num());
            progressBar.setProgress(item.getDone_num());
        } else {
            viewHolder.setGone(R.id.plan_progressbar_ll, false);
        }
        viewHolder.setVisible(R.id.item_line_state, false);
        viewHolder.setVisible(R.id.month_plan_go, true);
        viewHolder.setVisible(R.id.plan_to_change, false);
        HorizontalLineView view = viewHolder.getView(R.id.item_plan_status);
        view.setVisibility(View.VISIBLE);
        viewHolder.setText(R.id.item_line_status, StringUtil.getYxbWeekState(item.getAudit_status()));
        view.setStatus(item.getAudit_status());
        viewHolder.setText(R.id.item_plan_device_name, item.getLine_name() + item.getName())
                .setText(R.id.item_plan_content, "类型 : " + item.getType_name());
    }
}