package com.patrol.terminal.adapter;

import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.bean.PersonalTaskListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class PersonalTaskAdapter extends BaseQuickAdapter<GroupTaskBean, BaseViewHolder> {
    private int type = 1;

    public PersonalTaskAdapter(int layoutResId, @Nullable List<GroupTaskBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, GroupTaskBean item) {
        viewHolder.setText(R.id.item_task_date_tv, "人");
        viewHolder.setBackgroundRes(R.id.item_task_date_tv, R.drawable.plan_day_bg);

        String finish_status = item.getDone_status();

            if ("0".equals(finish_status)){
                viewHolder.setText(R.id.item_line_state, "未完成");
                viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg);
            }else {
                viewHolder.setText(R.id.item_line_state, "已完成");
                viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_green_bg);
            }
        viewHolder.setText(R.id.item_line_state, "已分配");
        viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg);
        viewHolder.setGone(R.id.plan_progressbar_ll,true);
        viewHolder.setText(R.id.plan_progressbar_tv,"任务进度("+item.getDone_num()+"/"+item.getAll_num()+") :")
                .setText(R.id.plan_progressbar_num,item.getDone_rate()+"%");
        ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
        progressBar.setMax(item.getAll_num());
        progressBar.setProgress(item.getDone_num());
        viewHolder.setText(R.id.item_task_personal, "工作日期 :"+item.getYear()+"年"+item.getMonth()+"月"+item.getDay()+"日")
                .setText(R.id.item_task_name, item.getLine_name()+item.getName()+ item.getType_name()+"任务")
                .setText(R.id.item_task_type,"班组 :"+item.getDep_name());


    }
}