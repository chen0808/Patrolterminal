package com.patrol.terminal.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TaskContentBean;

import java.util.List;

public class TaskContentAdapter extends BaseQuickAdapter<TaskContentBean, BaseViewHolder> {

    public TaskContentAdapter(int layoutResId, @Nullable List<TaskContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskContentBean item) {
        helper.setText(R.id.tv_place, item.getWork_range())
                .setText(R.id.tv_content, item.getWork_content());
        if (item.getWork_name() != null && !item.getWork_name().equals("")) {
            helper.setText(R.id.tv_name, item.getWork_name());
        } else {
            helper.getView(R.id.tv_name).setVisibility(View.GONE);
            helper.getView(R.id.view_name).setVisibility(View.GONE);
        }
    }
}
