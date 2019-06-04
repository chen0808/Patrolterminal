package com.patrol.terminal.adapter;

import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.GroupTaskBean;

import java.util.List;

import androidx.annotation.Nullable;

public class GroupTaskAdapter extends BaseQuickAdapter<GroupTaskBean, BaseViewHolder> {
    private int type = 1;

    public GroupTaskAdapter(int layoutResId, @Nullable List<GroupTaskBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, GroupTaskBean item) {
        switch (item.getType_sign()) {
            case "4":
                viewHolder.setText(R.id.item_task_date_tv, "特殊");
                viewHolder.setBackgroundRes(R.id.item_task_date_tv, R.drawable.plan_week_bg);
                break;
            case "7":
                viewHolder.setText(R.id.item_task_date_tv, "保电");
                viewHolder.setBackgroundRes(R.id.item_task_date_tv, R.drawable.plan_qing_bg);
                break;
            case "2":
                viewHolder.setText(R.id.item_task_date_tv, "故障");
                viewHolder.setBackgroundRes(R.id.item_task_date_tv, R.drawable.plan_yellow_bg);
                break;
            case "1":
                viewHolder.setText(R.id.item_task_date_tv, "定巡");
                viewHolder.setBackgroundRes(R.id.item_task_date_tv, R.drawable.plan_mon_bg);
                break;
            default:
                viewHolder.setText(R.id.item_task_date_tv, "定检");
                viewHolder.setBackgroundRes(R.id.item_task_date_tv, R.drawable.plan_day_bg);
                break;
        }

        if ("1".equals(item.getIs_rob())){
            viewHolder.setText(R.id.item_task_date_tv, "抢");
            viewHolder.setBackgroundRes(R.id.item_task_date_tv, R.drawable.group_red_bg);
        }
        String allot_status = item.getAllot_status();
        if ("0".equals(allot_status)){
            viewHolder.setText(R.id.item_line_state, "未分配");
            viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg);
            viewHolder.setGone(R.id.plan_progressbar_ll,false);
        }else {
                viewHolder.setText(R.id.item_line_state, "已分配");
                viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg);
            viewHolder.setGone(R.id.plan_progressbar_ll,true);
            viewHolder.setText(R.id.plan_progressbar_tv,"任务进度("+item.getDone_num()+"/"+item.getAll_num()+") :")
                    .setText(R.id.plan_progressbar_num,item.getDone_rate()+"%");
            ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
            progressBar.setMax(item.getAll_num());
            progressBar.setProgress(item.getDone_num());
        }
        viewHolder.setText(R.id.item_task_personal, "小组负责人 :"+item.getDuty_user_name()==null?"暂无":item.getDuty_user_name())
                .setText(R.id.item_task_name, item.getLine_name()+item.getName()+"任务")
                .setText(R.id.item_task_type,"班组 :"+ item.getDep_name());



    }
}