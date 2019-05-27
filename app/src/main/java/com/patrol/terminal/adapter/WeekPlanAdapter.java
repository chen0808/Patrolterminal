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

        if (item.getWeek_id()!=null){
            if (item.getType_name().contains("特")) {
                viewHolder.setText(R.id.item_plan_date_tv, "特");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_week_bg);
            }else if (item.getType_name().contains("保")) {
                viewHolder.setText(R.id.item_plan_date_tv, "保");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_qing_bg);
            } else if (item.getType_name().contains("事")) {
                viewHolder.setText(R.id.item_plan_date_tv, "事");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_yellow_bg);
            } else if (item.getType_name().contains("巡")) {
                viewHolder.setText(R.id.item_plan_date_tv, "巡");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_mon_bg);
            } else {
                viewHolder.setText(R.id.item_plan_date_tv, "检");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_day_bg);
            }

                if("0".equals(item.getAudit_status())){
                    viewHolder.setVisible(R.id.plan_to_change, true);
                }else {
                    viewHolder.setVisible(R.id.plan_to_change, false);
                }
            if("1".equals(item.getAllot_status())){
                viewHolder.setGone(R.id.plan_progressbar_ll, true);
                viewHolder.setGone(R.id.plan_progressbar_ll,true);
                viewHolder.setText(R.id.plan_progressbar_tv,"计划进度("+item.getDone_num()+"/"+item.getAll_num()+") :")
                        .setText(R.id.plan_progressbar_num,item.getDone_rate()+"%");
                ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
                progressBar.setMax(item.getAll_num());
                progressBar.setProgress(item.getDone_num());
            }else {
                viewHolder.setGone(R.id.plan_progressbar_ll, false);
            }
        viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_week_bg);
        viewHolder.setVisible(R.id.item_line_state, false);
        viewHolder.setVisible(R.id.month_plan_go, true);
            viewHolder.setVisible(R.id.plan_to_change, false);
        HorizontalLineView view = viewHolder.getView(R.id.item_plan_status);
            view.setVisibility(View.VISIBLE);
        viewHolder.setText(R.id.item_line_status, StringUtil.getYxbWeekState(item.getAudit_status()));
        view.setStatus(item.getAudit_status());
        viewHolder.setText(R.id.item_plan_device_name,item.getLine_name()+item.getName())
                .setText(R.id.item_plan_content,"类型 : "+item.getType_name());
//        viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, AddMonthPlanActivity.class);
//                intent.putExtra("from", Constant.FROM_WEEKPLAN_TO_ADDMONTH);
//                intent.putExtra("month_id", item.getMonth_line_id());
//                intent.putExtra("week_id",item.getWeek_id());
//                intent.putExtra("line_id",item.getLine_id());
//                intent.putExtra("line_name",item.getLine_name());
//                intent.putExtra("year",item.getYear()+"");
//                intent.putExtra("month",item.getMonth()+"");
//                intent.putExtra("week",item.getWeek()+"");
//                intent.putExtra("id",item.getId()+"");
//                intent.putExtra("type",item.getType_name());
//                //intent.putExtra("from","week");
//                mContext.startActivity(intent);
//            }
//        });
    }else {
//            viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_week_bg);
//            String plan_type = item.getPlan_type();
//            switch (plan_type){
//                case "1":
//                    viewHolder.setText(R.id.item_plan_date_tv, "保").setVisible(R.id.plan_to_change,true);
//                    viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(mContext, ChangePlanActivity.class);
//                            intent.putExtra("from", Constant.FROM_WEEKPLAN_TO_ADDMONTH);
//                            intent.putExtra("month_id", item.getMonth_id());
//                            intent.putExtra("line_id",item.getLine_id());
//                            mContext.startActivity(intent);
//                        }
//                    });
//                    break;
//                case "2":
//                    viewHolder.setText(R.id.item_plan_date_tv, "验").setVisible(R.id.plan_to_change,false);
//                    break;
//                case "3":
//                    viewHolder.setText(R.id.item_plan_date_tv, "安").setVisible(R.id.plan_to_change,false);
//                    break;
//            }
//            viewHolder.setText(R.id.item_plan_device_name, item.getPlan_name())
//                    .setVisible(R.id.item_plan_status,false)
//                    .setText(R.id.item_plan_content, "开始时间 : " + item.getBegin_time())
//                    .setText(R.id.item_line_status, "结束时间 : " + item.getEnd_time());;

        }
    }
}