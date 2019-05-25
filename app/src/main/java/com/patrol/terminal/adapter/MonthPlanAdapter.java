package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddMonthPlanActivity;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

import androidx.annotation.Nullable;

public class MonthPlanAdapter extends BaseQuickAdapter<MonthPlanBean, BaseViewHolder> {

    private String year, month;
    private String state;

    public MonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data, String year, String month,String state) {
        super(layoutResId, data);
        this.year = year;
        this.month = month;
        this.state=state;
    }

    public MonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data) {
        super(layoutResId, data);
        this.year = year;
        this.month = month;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, MonthPlanBean item) {
       if (item.getMonth_id()!=null){
            if (item.getFull_plan().contains("巡")){
               viewHolder.setText(R.id.item_plan_date_tv, "巡");
           }else if (item.getFull_plan().contains("特")){
               viewHolder.setText(R.id.item_plan_date_tv, "特");
           }else {
               viewHolder.setText(R.id.item_plan_date_tv, "检");
           }
           if(state!=null){
               viewHolder.setVisible(R.id.plan_to_change, false);
           }else {
               if("0".equals(item.getAudit_status())){
                   viewHolder.setVisible(R.id.plan_to_change, true);
               }else {
                   viewHolder.setVisible(R.id.plan_to_change, false);
               }

           }
        viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_mon_bg);
        viewHolder.setVisible(R.id.item_line_state, false);
        HorizontalLineView horizontalLineView = viewHolder.getView(R.id.item_plan_status);
        viewHolder.setText(R.id.item_line_status, StringUtil.getYXBstate(item.getAudit_status()));
        horizontalLineView.setStatus(item.getAudit_status());
           horizontalLineView.setVisibility(View.VISIBLE);
        viewHolder.setText(R.id.item_plan_device_name, item.getLine_name() + year + "年" + month + "月计划")
                .setText(R.id.item_plan_content, "类型 : " + item.getFull_plan());
        viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddMonthPlanActivity.class);
                intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                intent.putExtra("month_id", item.getMonth_id());
                intent.putExtra("line_id", item.getLine_id());
                intent.putExtra("line_name", item.getLine_name());
                intent.putExtra("year", item.getYear()+"");
                intent.putExtra("month", item.getMonth()+"");
                intent.putExtra("type", item.getFull_plan());
                mContext.startActivity(intent);
            }
        });
    }else {
//           InnerPlanbean planCheck = item.getPlanCheck();
//           String plan_type = planCheck.getPlan_type();
//           switch (plan_type){
//               case "1":
//                   viewHolder.setText(R.id.item_plan_date_tv, "保")
//                      .setVisible(R.id.plan_to_change,true);
//                   viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
//                       @Override
//                       public void onClick(View v) {
//                           Intent intent = new Intent(mContext, ChangePlanActivity.class);
//                           intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
//                           intent.putExtra("month_id", item.getM_ID());
//                           intent.putExtra("line_id", item.getLINE_ID());
//                           mContext.startActivity(intent);
//                       }
//                   });
//                   break;
//               case "2":
//                   viewHolder.setText(R.id.item_plan_date_tv, "验") .setVisible(R.id.plan_to_change,true);
//                   break;
//               case "3":
//                   viewHolder.setText(R.id.item_plan_date_tv, "安") .setVisible(R.id.plan_to_change,true);
//                   break;
//           }
//           viewHolder.setText(R.id.item_plan_device_name, planCheck.getPlan_name())
//                   .setVisible(R.id.item_plan_status,false)
//                   .setText(R.id.item_plan_content, "开始时间 : " + planCheck.getBegin_time())
//                   .setText(R.id.item_line_status, "结束时间 : " + planCheck.getEnd_time());;

       }
    }

}