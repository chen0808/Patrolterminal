package com.patrol.terminal.adapter;

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
        viewHolder.setText(R.id.item_task_date_tv, "组");
        viewHolder.setBackgroundRes(R.id.item_task_date_tv, R.drawable.plan_day_bg);

        if ("1".equals(item.getIs_rob())){
            viewHolder.setText(R.id.item_task_date_tv, "抢");
            viewHolder.setBackgroundRes(R.id.item_task_date_tv, R.drawable.group_red_bg);
        }
        String allot_status = item.getAllot_status();
        if ("0".equals(allot_status)){
            viewHolder.setText(R.id.item_line_state, "未分配");
            viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg);
        }else {
                viewHolder.setText(R.id.item_line_state, "已分配");
                viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg);

        }
        viewHolder.setText(R.id.item_task_personal, "小组负责人 :"+item.getDuty_user_name())
                .setText(R.id.item_task_name, item.getLine_name()+item.getName()+item.getType_name()+"任务")
                .setText(R.id.item_task_type,"班组 :"+ item.getDep_name());



    }
}