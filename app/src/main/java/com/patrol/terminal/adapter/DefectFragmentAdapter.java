package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DefectIngDetailActivity;
import com.patrol.terminal.bean.DefectFragmentBean2;

import java.util.List;

public class DefectFragmentAdapter extends BaseQuickAdapter<DefectFragmentBean2, BaseViewHolder> {

    public DefectFragmentAdapter(int layoutResId, @Nullable List<DefectFragmentBean2> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DefectFragmentBean2 item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DefectIngDetailActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
       /* if ("0".equals(item.getStatus())) {
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
        }*/
        viewHolder.setText(R.id.tv_name, "线路名称：" + item.getLine_name() + " " + item.getStart_name() + "-" + item.getEnd_name() + "\n" + "缺陷内容：" + item.getContent())
                .setText(R.id.tv_time, "缺陷类型：" + item.getCategory_name())
                .setText(R.id.tv_detail, "发现时间：" + item.getFind_time())
                .setText(R.id.iv_icon, "缺陷");
        ;
    }
}