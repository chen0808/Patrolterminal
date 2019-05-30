package com.patrol.terminal.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TicketWork;

import java.util.List;

import androidx.annotation.Nullable;

public class TaskContentAdapter extends BaseQuickAdapter<TicketWork, BaseViewHolder> {

    public TaskContentAdapter(int layoutResId, @Nullable List<TicketWork> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketWork item) {
        helper.setText(R.id.tv_place, item.getWork_range())
                .setText(R.id.tv_content, item.getWork_content());
        if (item.getLine_name() != null && !item.getLine_name().equals("")) {
            helper.setText(R.id.tv_name, item.getLine_name());
        } else {
            helper.getView(R.id.tv_name).setVisibility(View.GONE);
            helper.getView(R.id.view_name).setVisibility(View.GONE);
        }
    }
}
