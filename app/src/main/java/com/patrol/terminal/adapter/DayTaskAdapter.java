package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.CreateDayTaskActivity;
import com.patrol.terminal.activity.DayPlanMakeActivity;
import com.patrol.terminal.bean.MonthPlanBean;

import java.util.List;

import androidx.annotation.Nullable;

public class DayTaskAdapter extends BaseQuickAdapter<MonthPlanBean, BaseViewHolder> {
    private int type = 1;

    public DayTaskAdapter(int layoutResId, @Nullable List<MonthPlanBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, MonthPlanBean item) {
        viewHolder.setText(R.id.item_plan_date_tv, "日");
        viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_day_bg);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, DayPlanMakeActivity.class));
            }
        });
        viewHolder.setOnClickListener(R.id.week_to_day, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CreateDayTaskActivity.class));
            }
        });
        viewHolder.setVisible(R.id.week_to_day,true);
        viewHolder.setText(R.id.item_plan_content, "执行日期: 无" )
                .setText(R.id.week_to_day, "生成任务" )
                .setText(R.id.item_plan_device_name, "创建时间: 无");
    }
}