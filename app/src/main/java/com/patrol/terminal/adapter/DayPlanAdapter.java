package com.patrol.terminal.adapter;

import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.utils.StringUtil;
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

        switch (item.getType_sign()){
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
        if("1".equals(item.getAllot_status())){
            viewHolder.setGone(R.id.plan_progressbar_ll, true);
            viewHolder.setGone(R.id.plan_progressbar_ll,true);
            viewHolder.setText(R.id.plan_progressbar_tv,"计划进度("+item.getDone_num()+"/"+item.getAll_num()+") :")
                    .setText(R.id.plan_progressbar_num,item.getDone_rate()+"%");
            ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
            progressBar.setMax(item.getAll_num());
            progressBar.setProgress(item.getDone_num());
            if (Double.parseDouble(item.getDone_rate())==100){
                viewHolder.setText(R.id.item_line_status, "状态 : 已完成" );
            }else {
                viewHolder.setText(R.id.item_line_status, "状态 : 执行中" );
            }
        }else {
            viewHolder.setGone(R.id.plan_progressbar_ll, false);
            viewHolder.setText(R.id.item_line_status, "状态 : 未分配" );
        }
            viewHolder.setVisible(R.id.item_line_state, false);
            viewHolder.setVisible(R.id.plan_to_change, false);

            HorizontalLineView view = viewHolder.getView(R.id.item_plan_status);



            view.setVisibility(View.GONE);
            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name()+item.getName())
                    .setText(R.id.item_plan_content, "工作内容 : " + StringUtil.getTypeSign(item.getType_sign()));

//            viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, AddMonthPlanActivity.class);
//                    intent.putExtra("from", Constant.FROM_DAYPLAN_TO_ADDMONTH);
//                    intent.putExtra("week_id", item.getWeek_line_id());
//                    intent.putExtra("day_id", item.getDay_id());
//                    intent.putExtra("line_id", item.getLine_id());
//                    intent.putExtra("line_name", item.getLine_name());
//                    intent.putExtra("year", item.getYear() + "");
//                    intent.putExtra("month", item.getMonth() + "");
//                    intent.putExtra("day", item.getDay() + "");
//                    intent.putExtra("id", item.getId());
//                    intent.putExtra("type", item.getType_name());
//                    //intent.putExtra("from","week");
//                    mContext.startActivity(intent);
//                }
//            });

    }
}