package com.patrol.terminal.adapter;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TicketSafeContent;

import java.util.List;

import androidx.annotation.Nullable;

public class SafeListAdapter extends BaseQuickAdapter<TicketSafeContent, BaseViewHolder> {


    private final List<TicketSafeContent> chooseList;

    public SafeListAdapter(int layoutResId, @Nullable List<TicketSafeContent> data, List<TicketSafeContent> chooseList) {
        super(layoutResId, data);
        this.chooseList = chooseList;
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketSafeContent item) {
        helper.setText(R.id.tv, item.getContent());
        for (int i = 0; i < chooseList.size(); i++) {
            if (item.getTicket_safe_id().equals(chooseList.get(i).getTicket_safe_id())) {
                item.setTag(true);
            }
        }
        helper.setChecked(R.id.cb, item.isTag());
        helper.setOnCheckedChangeListener(R.id.cb, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < chooseList.size(); i++) {
                    if (isChecked && !item.isTag()) {
                        item.setTag(true);
                    } else if (!isChecked && item.isTag()) {
                        item.setTag(false);
                    }
                }
            }
        });
    }
}
