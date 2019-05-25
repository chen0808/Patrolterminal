package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DepChooseActivity;
import com.patrol.terminal.activity.NewMainActivity;
import com.patrol.terminal.utils.Constant;

import java.util.List;

import androidx.annotation.Nullable;

public class DepChooseAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DepChooseAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_dep, item);
//        helper.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, NewMainActivity.class));
//                ((DepChooseActivity) mContext).finish();
//            }
//        });
    }
}
