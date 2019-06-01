package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.bean.ControlDepWorkBean2;

import java.util.List;

import androidx.annotation.Nullable;

public class ControlCardSafeAdapter extends BaseQuickAdapter<ControlDepWorkBean2, BaseViewHolder> {

    public ControlCardSafeAdapter(int layoutResId, @Nullable List<ControlDepWorkBean2> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ControlDepWorkBean2 item) {
//        helper.setText(R.id.tv, item.getContent());
//        CheckBox cb = helper.getView(R.id.cb);
//        if (item.isTag()) {
//            cb.setChecked(true);
//        } else {
//            cb.setChecked(false);
//        }
//        // 设置复选框的点击事件
//        helper.getView(R.id.cb).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                item.setTag(!item.isTag());
//                if (item.isTag()) {
//                    cb.setChecked(true);
//                } else {
//                    cb.setChecked(false);
//                }
//            }
//        });
    }
}
