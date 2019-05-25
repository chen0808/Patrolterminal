package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.TodoListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class BackTodoYXAdapter extends BaseQuickAdapter<TodoListBean, BaseViewHolder> {


    public BackTodoYXAdapter(int layoutResId, @Nullable List<TodoListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodoListBean item) {
        int position = helper.getPosition();
        if (item!= null) {
            helper.setText(R.id.tv_title, position+1+". "+item.getContent());
        }
    }
}
