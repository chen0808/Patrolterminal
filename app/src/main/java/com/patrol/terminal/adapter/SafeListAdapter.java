package com.patrol.terminal.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TicketSafeContent;

import java.util.List;

import androidx.annotation.Nullable;

public class SafeListAdapter extends BaseQuickAdapter<TicketSafeContent, BaseViewHolder> {

    public SafeListAdapter(int layoutResId, @Nullable List<TicketSafeContent> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketSafeContent item) {
        helper.setText(R.id.tv, item.getContent());
        helper.setText(R.id.name_tv, "(" + (helper.getPosition() + 1) + ")");
        CheckBox cb = helper.getView(R.id.cb);
        if (item.isTag()) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }
        // 设置复选框的点击事件
        helper.getView(R.id.cb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setTag(!item.isTag());
                if (item.isTag()) {
                    cb.setChecked(true);
                } else {
                    cb.setChecked(false);
                }
            }
        });

    }
}
