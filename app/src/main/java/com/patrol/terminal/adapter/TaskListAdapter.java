package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.MonthPlanDetailActivity;
import com.patrol.terminal.bean.MonthPlanBean;

import java.util.List;

import androidx.annotation.Nullable;

public class TaskListAdapter extends BaseQuickAdapter<MonthPlanBean, BaseViewHolder> {
 private int type=1;

    public TaskListAdapter(int layoutResId, @Nullable List<MonthPlanBean> data, int type) {
        super(layoutResId, data);
        this.type=type;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, MonthPlanBean item) {
        viewHolder.setText(R.id.item_plan_date_tv, "日");
         viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_day_bg);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MonthPlanDetailActivity.class));
            }
        });
        if (type==1){
            viewHolder.setVisible(R.id.week_to_day,true);
            viewHolder.setText(R.id.week_to_day,"指派");
        }
        viewHolder .setText(R.id.item_plan_content, "执行日期: 无" )
                .setText(R.id.week_to_day, "生成任务" )
                .setText(R.id.item_plan_device_name, "创建时间: 无");

    }
}