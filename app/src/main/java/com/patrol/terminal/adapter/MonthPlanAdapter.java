package com.patrol.terminal.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddMonthPlanActivity;
import com.patrol.terminal.activity.LineCheckActivity;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

import androidx.annotation.Nullable;

public class MonthPlanAdapter extends BaseQuickAdapter<MonthPlanBean, BaseViewHolder> {

    private String state;
    private String jobType;

    public MonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data,String state,String jovType) {
        super(layoutResId, data);
        this.state=state;
        this.jobType=jovType;
    }

    public MonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, MonthPlanBean item) {
       if (item.getMonth_id()!=null){
            if (item.getFull_plan().contains("巡")){
               viewHolder.setText(R.id.item_plan_date_tv, "巡");
           }else if (item.getFull_plan().contains("特")){
               viewHolder.setText(R.id.item_plan_date_tv, "特");
           }else if (item.getFull_plan().contains("保")){
               viewHolder.setText(R.id.item_plan_date_tv, "保");
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
        viewHolder.setText(R.id.item_plan_device_name, item.getLine_name())
                .setText(R.id.item_plan_content, "类型 : " + item.getFull_plan());
        viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddMonthPlanActivity.class);
                intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                intent.putExtra("line_name", item.getLine_name());
                intent.putExtra("year", item.getYear()+"");
                intent.putExtra("month", item.getMonth()+"");
                intent.putExtra("line_id", item.getLine_id()+"");
                intent.putExtra("type", item.getFull_plan());
                mContext.startActivity(intent);
            }
        });
    }else {

                   viewHolder.setText(R.id.item_plan_date_tv, "保")
                      .setVisible(R.id.plan_to_change,true);
                   if ("0".equals(item.getAllot_status())){
                       viewHolder.setVisible(R.id.plan_to_change,true);
                       viewHolder.setText(R.id.item_plan_content, "分配状态 : 未分配" );
                   }else {
                       viewHolder.setVisible(R.id.plan_to_change,false);
                       viewHolder.setText(R.id.item_plan_content, "分配状态 : 已分配" );
                   }
                   viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(mContext, LineCheckActivity.class);
                           intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                           intent.putExtra("id", item.getId());
                           intent.putExtra("year", item.getYear());
                           intent.putExtra("month", item.getMonth());
                           ((Activity)mContext).startActivityForResult(intent,10);
                       }
                   });
           viewHolder.setText(R.id.item_plan_device_name, "基于"+item.getLine_name()+"的保电计划")
                   .setText(R.id.item_line_status, "停电时间 : " + item.getStart_time() + " - " + item.getEnd_time());

       }
    }

}