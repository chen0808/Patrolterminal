package com.patrol.terminal.training;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TrainingTaskBean;
import com.patrol.terminal.bean.TrainingYearPlanBean;

import java.util.List;

import androidx.annotation.Nullable;

public class TrainingTaskAdapter extends BaseQuickAdapter<TrainingTaskBean, BaseViewHolder> {

     private String year,month;

    public TrainingTaskAdapter(int layoutResId, @Nullable List<TrainingTaskBean> data, String year, String month) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }

    public TrainingTaskAdapter(int layoutResId, @Nullable List<TrainingTaskBean> data) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }
    @Override
    protected void convert(BaseViewHolder viewHolder, TrainingTaskBean item) {
            //viewHolder.setText(R.id.plan_to_change, "制定");
            viewHolder.setVisible(R.id.plan_to_change, false);
            viewHolder.setText(R.id.item_plan_device_name, item.getName());
            viewHolder.setText(R.id.item_sponsor, "主办单位：" + item.getHost_unit());
            viewHolder.setText(R.id.item_type, "类型：" + item.getType_name());
            viewHolder.setText(R.id.item_start_time, "开始时间：" + item.getStart_time());
            viewHolder.setText(R.id.item_end_time, "结束时间：" + item.getEnd_time());

            String userListStr = "";
            List<TrainingTaskBean.UserListBean> userListBeans = item.getUserList();
            if (item.getUserList() != null && item.getUserList().size() > 0) {
                for (int i = 0; i < userListBeans.size(); i++) {
                    userListStr += userListBeans.get(i).getUser_name();
                    if (i < (userListBeans.size() - 1)) {
                        userListStr += ",";
                    }
                }
            }


            viewHolder.setText(R.id.person_tv, "参加人员：" + userListStr);

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