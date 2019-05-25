package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddMonthPlanActivity;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

import androidx.annotation.Nullable;

public class DayPlanAdapter extends BaseQuickAdapter<DayListBean, BaseViewHolder> {
    private int type = 1;

    public DayPlanAdapter(int layoutResId, @Nullable List<DayListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DayListBean item) {
        if (item.getDay_id() != null) {
            if (item.getType_name().contains("巡")) {
                viewHolder.setText(R.id.item_plan_date_tv, "巡");
            } else if (item.getType_name().contains("特")) {
                viewHolder.setText(R.id.item_plan_date_tv, "特");
            } else {
                viewHolder.setText(R.id.item_plan_date_tv, "检");
            }
            viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_day_bg);

            viewHolder.setVisible(R.id.item_line_state, false);
            viewHolder.setVisible(R.id.month_plan_go, true);
            HorizontalLineView view = viewHolder.getView(R.id.item_plan_status);
            List<DayListBean.TowersBean> towers = item.getTowers();
            String tower = "";
            for (int j = 0; j < towers.size(); j++) {
                if (j  == 0) {
                    tower = towers.get(j).getName();
                } else {
                    tower = tower + "," +towers.get(j).getName();
                }
            }

            viewHolder.setText(R.id.item_line_status, "杆段 : " +tower);

            view.setVisibility(View.GONE);
            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name() + item.getYear() + "年" + item.getMonth() + "月" + item.getWeek() + "日计划")
                    .setVisible(R.id.plan_to_change, true)
                    .setText(R.id.item_plan_content, "类型 : " + item.getType_name());

            viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddMonthPlanActivity.class);
                    intent.putExtra("from", Constant.FROM_DAYPLAN_TO_ADDMONTH);
                    intent.putExtra("week_id", item.getWeek_id());
                    intent.putExtra("day_id", item.getDay_id());
                    intent.putExtra("line_id", item.getLine_id());
                    intent.putExtra("line_name", item.getLine_name());
                    intent.putExtra("year", item.getYear() + "");
                    intent.putExtra("month", item.getMonth() + "");
                    intent.putExtra("day", item.getDay() + "");
                    intent.putExtra("id", item.getId());
                    intent.putExtra("type", item.getType_name());
                    //intent.putExtra("from","week");
                    mContext.startActivity(intent);
                }
            });
        } else {
//            viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_day_bg);
//            String plan_type = item.getPlan_type();
//            switch (plan_type){
//                case "1":
//                    viewHolder.setText(R.id.item_plan_date_tv, "保").setVisible(R.id.plan_to_change,true);
//                    viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(mContext, ChangePlanActivity.class);
//                            intent.putExtra("from", Constant.FROM_DAYPLAN_TO_ADDMONTH);
//                            intent.putExtra("week_id",item.getW_id());
//                            intent.putExtra("day_id",item.getD_id());
//                            mContext.startActivity(intent);
//                        }
//                    });
//                    break;
//                case "2":
//                case "3":
//                    viewHolder.setText(R.id.item_plan_date_tv, "安").setVisible(R.id.plan_to_change,false);
//                    break;
//            }
//            viewHolder.setText(R.id.item_plan_device_name, item.getPlan_name())
//                     .setVisible(R.id.item_plan_status,false)
//                    .setText(R.id.item_plan_content, "开始时间 : " + item.getBegin_time())
//                    .setText(R.id.item_line_status, "结束时间 : " + item.getEnd_time());;

        }
    }
}