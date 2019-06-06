package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.PersonalTaskListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class YXTodoManageAdapter extends BaseQuickAdapter<PersonalTaskListBean, BaseViewHolder> {


    public YXTodoManageAdapter(int layoutResId, @Nullable List<PersonalTaskListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PersonalTaskListBean item) {
        String deal_type = item.getType_sign();
        String remind_type = item.getPlan_type();
        String done_status = item.getDone_status();
        switch (deal_type) {
            case "1":
                viewHolder.setText(R.id.item_todo_type_tv, "巡");
                break;
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
                viewHolder.setText(R.id.item_todo_type_tv, "检");
                break;
            case "20":
                viewHolder.setText(R.id.item_todo_type_tv, "修");
                break;
        }
        if ("0".equals(done_status)) {
            viewHolder.setText(R.id.item_yxtodo_state, "未完成");
            viewHolder.setTextColor(R.id.item_yxtodo_state, mContext.getResources().getColor(R.color.write_red));
            viewHolder.setBackgroundRes(R.id.item_yxtodo_state, R.drawable.state_red_bg);
        } else {
            viewHolder.setText(R.id.item_yxtodo_state, "已完成");
            viewHolder.setTextColor(R.id.item_yxtodo_state, mContext.getResources().getColor(R.color.green));
            viewHolder.setBackgroundRes(R.id.item_yxtodo_state, R.drawable.state_green_bg);

        }

        viewHolder.setText(R.id.item_todo_title, item.getLine_name());
        viewHolder.setText(R.id.item_todo_name, "提交人 :"+item.getUser_name());
        viewHolder.setText(R.id.item_todo_time, "提交时间 :" + item.getDone_time());
    }
}