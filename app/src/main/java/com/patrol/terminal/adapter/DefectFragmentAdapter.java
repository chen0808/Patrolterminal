package com.patrol.terminal.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DefectIngDetailActivity;
import com.patrol.terminal.bean.DefectFragmentBean;

import java.util.List;

public class DefectFragmentAdapter extends BaseQuickAdapter<DefectFragmentBean, BaseViewHolder> {

    public DefectFragmentAdapter(int layoutResId, @Nullable List<DefectFragmentBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DefectFragmentBean item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DefectIngDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("bean",item);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        if ("0".equals(item.getStatus())) {
            viewHolder.setText(R.id.item_line_state, "未分配");
            viewHolder.setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.write_red));
            viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg);
        } else {
            viewHolder.setText(R.id.item_line_state, "已分配");
            viewHolder.setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.green));
            viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_green_bg);
        }
        if ("0".equals(item.getAudit_status())) {
            viewHolder.setText(R.id.tv_detail, "审核情况：未审核");
        } else if ("1".equals(item.getAudit_status())) {
            viewHolder.setText(R.id.tv_detail, "审核情况：已审核");
        }
        viewHolder.setText(R.id.tv_name, "内容:" + item.getContent())
                .setText(R.id.tv_time, "发现时间：" + item.getFind_time())
                .setText(R.id.iv_icon, "缺陷");
        ;
    }
}