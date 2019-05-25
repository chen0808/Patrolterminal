package com.patrol.terminal.training;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TrainingMonthPlanBean;
import com.patrol.terminal.bean.TrainingYearPlanBean;

import java.util.List;

import androidx.annotation.Nullable;

public class TrainingMonthAdapter extends BaseQuickAdapter<TrainingMonthPlanBean, BaseViewHolder> {

     private String year,month;

    public TrainingMonthAdapter(int layoutResId, @Nullable List<TrainingMonthPlanBean> data, String year, String month) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }

    public TrainingMonthAdapter(int layoutResId, @Nullable List<TrainingMonthPlanBean> data) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }
    @Override
    protected void convert(BaseViewHolder viewHolder, TrainingMonthPlanBean item) {
            //viewHolder.setText(R.id.plan_to_change, "制定");
            viewHolder.setText(R.id.item_plan_date_tv, "月");
            viewHolder.setVisible(R.id.plan_to_change, false);
            viewHolder.setText(R.id.item_plan_device_name, item.getName());
            viewHolder.setText(R.id.item_plan_content, "时间：" + item.getYear() + "年" + item.getMonth() + "月");
            viewHolder.setText(R.id.item_sponsor, "主办单位：" + item.getHost_unit());
            viewHolder.setText(R.id.item_type, "类型：" + item.getType_name());

            //viewHolder.setVisible(R.id.item_plan_status, false);
//            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name());
//            viewHolder.setText(R.id.item_plan_content, item.getRepair_content());
//            viewHolder.setText(R.id.item_start_time, "开始时间：" + item.getStart_time());
//            viewHolder.setText(R.id.item_end_time, "结束时间：" + item.getEnd_time());

//        viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, NewTrainPlanActivity.class);
//                intent.putExtra(Constant.ADD_TRAINING_PLAN_ENTER_TYPE, Constant.FROM_YEAR_TO_ENTER_TYPE);
//                mContext.startActivity(intent);
//
//            }
//        });


    }

}