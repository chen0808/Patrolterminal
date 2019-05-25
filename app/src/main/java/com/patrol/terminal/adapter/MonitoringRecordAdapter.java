package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.MonitoringRecordActivity;

import java.util.List;

import androidx.annotation.Nullable;

public class MonitoringRecordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MonitoringRecordAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item);
    }


}
