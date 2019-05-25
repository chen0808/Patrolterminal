package com.patrol.terminal.training;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TrainingTempPlanBean;
import com.patrol.terminal.bean.TrainingYearPlanBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.widget.HorizontalLineView;
import com.patrol.terminal.widget.HorizontalLineViewForTrain;

import java.util.List;

import androidx.annotation.Nullable;

public class TrainingTempAdapter extends BaseQuickAdapter<TrainingTempPlanBean, BaseViewHolder> {

     private String year,month;

    public TrainingTempAdapter(int layoutResId, @Nullable List<TrainingTempPlanBean> data, String year, String month) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }

    public TrainingTempAdapter(int layoutResId, @Nullable List<TrainingTempPlanBean> data) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }
    @Override
    protected void convert(BaseViewHolder viewHolder, TrainingTempPlanBean item) {
            //viewHolder.setText(R.id.plan_to_change, "审批");
            viewHolder.setVisible(R.id.plan_to_change, false);
            //viewHolder.setVisible(R.id.item_plan_status, false);
//            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name());
//            viewHolder.setText(R.id.item_plan_content, item.getRepair_content());
//            viewHolder.setText(R.id.item_start_time, "开始时间：" + item.getStart_time());
//            viewHolder.setText(R.id.item_end_time, "结束时间：" + item.getEnd_time());

        viewHolder.setText(R.id.item_plan_device_name, item.getName());
        viewHolder.setText(R.id.item_start_time, "开始时间：" + item.getStart_time());
        viewHolder.setText(R.id.item_end_time, "结束时间：" + item.getEnd_time());
        viewHolder.setText(R.id.item_sponsor, "主办单位：" + item.getHost_unit());
        viewHolder.setText(R.id.item_type, "类型：" + item.getType_name());

        HorizontalLineViewForTrain view = viewHolder.getView(R.id.item_plan_status);
        view.setTrainTempStatus(item.getStatus());

//        viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, NewTrainPlanActivity.class);
//                intent.putExtra(Constant.ADD_TRAINING_PLAN_ENTER_TYPE, Constant.FROM_TEMP_TO_ENTER_TYPE);
//                if (item.getAudioStatus().equals("0") || item.getAudioStatus().equals("1")) {
//                    viewHolder.setVisible(R.id.plan_to_change, true);
//                    intent.putExtra(Constant.ADD_TRAIN_PLAN_IS_DISTRIBUTE, Constant.NOT_DISTRIBUTE);
//                }else {
//                    viewHolder.setVisible(R.id.plan_to_change, false);
//                    intent.putExtra(Constant.ADD_TRAIN_PLAN_IS_DISTRIBUTE, Constant.IS_DISTRIBUTE);
//                }
//
//                mContext.startActivity(intent);
//            }
//        });


    }

}