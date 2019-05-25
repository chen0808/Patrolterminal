package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.PersonalTaskListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class PersonalTaskAdapter extends BaseQuickAdapter<PersonalTaskListBean, BaseViewHolder> {
    private int type = 1;

    public PersonalTaskAdapter(int layoutResId, @Nullable List<PersonalTaskListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, PersonalTaskListBean item) {
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

        viewHolder.setText(R.id.item_task_personal, "工作日期 :"+item.getYear()+"年"+item.getMonth()+"月"+item.getDay()+"日")
                .setText(R.id.item_task_name, item.getLine_name()+item.getTower_name()+ item.getType_name()+"任务")
                .setText(R.id.item_task_type,"班组 :"+item.getDep_name());


    }
}