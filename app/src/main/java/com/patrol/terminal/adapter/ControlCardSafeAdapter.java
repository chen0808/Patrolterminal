package com.patrol.terminal.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.ControlCardSafe;

import java.util.List;

import androidx.annotation.Nullable;

public class ControlCardSafeAdapter extends BaseQuickAdapter<ControlCardSafe, BaseViewHolder> {

    public ControlCardSafeAdapter(int layoutResId, @Nullable List<ControlCardSafe> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ControlCardSafe item) {
        helper.setText(R.id.tv, item.getContent());
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
