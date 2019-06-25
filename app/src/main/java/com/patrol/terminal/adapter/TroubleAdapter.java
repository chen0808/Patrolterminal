package com.patrol.terminal.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.TroubleDetailActivity;
import com.patrol.terminal.bean.TroubleFragmentBean;
import com.patrol.terminal.utils.AdapterUtils;

import java.util.List;

public class TroubleAdapter extends BaseQuickAdapter<TroubleFragmentBean, BaseViewHolder> {
    public TroubleAdapter(int layoutResId, @Nullable List<TroubleFragmentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TroubleFragmentBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TroubleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("line_id", item.getLine_id());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        helper.setText(R.id.tv_name, "线路名称：" + item.getLine_name())
                .setText(R.id.tv_time, "设备主人" + item.getOwner_name())
                .setText(R.id.tv_detail, "线路等级：：" + item.getLine_level());
        TextView ivIcon = helper.getView(R.id.iv_icon);
        AdapterUtils.setIconText(ivIcon, item.getDep_name());
    }
}
