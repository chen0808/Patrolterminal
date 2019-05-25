package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DefectIngDetailActivity;
import com.patrol.terminal.bean.DefectDetailBean;
import com.patrol.terminal.bean.DefectFragmentBean;

import java.util.List;

import androidx.annotation.Nullable;

public class TroubleIngAdapter extends BaseQuickAdapter<DefectDetailBean, BaseViewHolder> {

    public TroubleIngAdapter(int layoutResId, @Nullable List<DefectDetailBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DefectDetailBean item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DefectIngDetailActivity.class);
                intent.putExtra("name", item.getName());
                mContext.startActivity(intent);
            }
        });
        if ("0".equals(item.getState())) {
            viewHolder.setText(R.id.item_line_state, "未完成");
            viewHolder.setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.write_red));
            viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg);
        } else {
            viewHolder.setText(R.id.item_line_state, "已完成");
            viewHolder.setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.green));
            viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_green_bg);
        }
        viewHolder.setText(R.id.tv_name, "内容:" + item.getName())
                .setText(R.id.tv_time, "处理措施：" + item.getState())
                .setText(R.id.tv_detail, "发现时间：" + item.getTime());
    }
}