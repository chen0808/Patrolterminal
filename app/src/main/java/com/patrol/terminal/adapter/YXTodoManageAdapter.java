package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TodoBean;
import com.patrol.terminal.utils.Utils;

import java.util.List;

public class YXTodoManageAdapter extends BaseQuickAdapter<TodoBean, BaseViewHolder> {


    public YXTodoManageAdapter(int layoutResId, @Nullable List<TodoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, TodoBean item) {

        String deal_type = item.getFlow_sign();
        String remind_type = item.getRemind_type();
        String done_status = item.getDone_status();
                viewHolder.setImageResource(R.id.item_todo_type_tv, Utils.getTodoIcon(deal_type));

        if ("0".equals(done_status)) {
            viewHolder.setText(R.id.item_yxtodo_state, "未完成");
            viewHolder.setTextColor(R.id.item_yxtodo_state, mContext.getResources().getColor(R.color.write_red));
            viewHolder.setBackgroundRes(R.id.item_yxtodo_state, R.drawable.state_red_bg);
        } else {
            viewHolder.setText(R.id.item_yxtodo_state, "已完成");
            viewHolder.setTextColor(R.id.item_yxtodo_state, mContext.getResources().getColor(R.color.green));
            viewHolder.setBackgroundRes(R.id.item_yxtodo_state, R.drawable.state_green_bg);

        }

        viewHolder.setText(R.id.item_todo_title, item.getTitle());
        viewHolder.setText(R.id.item_todo_name, "提交人：" + item.getFrom_user_name());
        viewHolder.setText(R.id.item_task_time, "任务日期：" + item.getYear()+"-"+item.getMonth()+"-"+item.getDay());
        viewHolder.setText(R.id.item_todo_time, "提交时间：" + item.getCreate_time());
    }
}