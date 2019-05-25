package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TodoListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class YXTodoManageAdapter extends BaseQuickAdapter<TodoListBean, BaseViewHolder> {


    public YXTodoManageAdapter(int layoutResId, @Nullable List<TodoListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, TodoListBean item) {
        String deal_type = item.getPlan_type_sign();
        String audit_status = item.getAudit_status();
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

        }
        switch (audit_status){
            case "0":
                    viewHolder.setText(R.id.item_yxtodo_state, "审核中");
                viewHolder.setTextColor(R.id.item_yxtodo_state,mContext.getResources().getColor(R.color.write_red));
                    viewHolder.setBackgroundRes(R.id.item_yxtodo_state, R.drawable.state_red_bg);
                break;
            case "1":
                viewHolder.setText(R.id.item_yxtodo_state, "审核通过");
                viewHolder.setTextColor(R.id.item_yxtodo_state,mContext.getResources().getColor(R.color.green));
                viewHolder.setBackgroundRes(R.id.item_yxtodo_state, R.drawable.state_green_bg);
                break;
            case "2":
                viewHolder.setText(R.id.item_yxtodo_state, "审核不通过");
                viewHolder.setTextColor(R.id.item_yxtodo_state,mContext.getResources().getColor(R.color.green));
                viewHolder.setBackgroundRes(R.id.item_yxtodo_state, R.drawable.state_green_bg);
                break;
        }
            viewHolder.setText(R.id.item_todo_title, item.getContent());
            viewHolder.setText(R.id.item_todo_name,"提交人 :" +item.getUser_name());
        viewHolder.setText(R.id.item_todo_time, "提交时间 :"+item.getCreate_time());
    }

}