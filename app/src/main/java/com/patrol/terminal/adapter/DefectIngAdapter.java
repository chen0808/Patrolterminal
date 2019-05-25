package com.patrol.terminal.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DefectIngDetailActivity;
import com.patrol.terminal.bean.DefectFragmentBean;

import java.util.List;

import androidx.annotation.Nullable;

public class DefectIngAdapter extends BaseQuickAdapter<DefectFragmentBean, BaseViewHolder> {
    private int mType;

    public DefectIngAdapter(int layoutResId, @Nullable List<DefectFragmentBean> data, int type) {
        super(layoutResId, data);
        this.mType = type;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DefectFragmentBean item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DefectIngDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("bean",item);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        if ("0".equals(item.getAudit_status())) {
            viewHolder.setText(R.id.item_line_state, "未完成");
            viewHolder.setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.write_red));
            viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg);
        } else {
            viewHolder.setText(R.id.item_line_state, "已完成");
            viewHolder.setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.green));
            viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_green_bg);
        }
        viewHolder.setText(R.id.tv_name, "内容:" + item.getContent())
                .setText(R.id.tv_time, "处理措施：" + item.getDeal_notes())
                .setText(R.id.tv_detail, "发现时间：" + item.getFind_time());

        if (mType == 0) {
            viewHolder.setText(R.id.iv_icon, "缺陷");
        }else if (mType == 1) {
            viewHolder.setText(R.id.iv_icon, "隐患");
        }
    }
}