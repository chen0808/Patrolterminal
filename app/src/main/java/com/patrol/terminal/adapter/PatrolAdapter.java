package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.PatrolDetailActivity;
import com.patrol.terminal.bean.PatrolListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class PatrolAdapter extends BaseQuickAdapter<PatrolListBean, BaseViewHolder> {

    public PatrolAdapter(int layoutResId, @Nullable List<PatrolListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, PatrolListBean item) {
        viewHolder.setText(R.id.item_patrol_date_tv, "巡");
        viewHolder.setBackgroundRes(R.id.item_patrol_date_tv, R.drawable.plan_mon_bg);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PatrolDetailActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });

        if ("2".equals(item.getIsConfirm())) {
            viewHolder.setVisible(R.id.item_patrol_state, true)
                    .setTextColor(R.id.item_patrol_state, mContext.getResources().getColor(R.color.write_red))
                    .setBackgroundRes(R.id.item_patrol_state, R.drawable.state_red_bg)
                    .setText(R.id.item_patrol_state, "审核未通过");

        } else if ("1".equals(item.getIsConfirm())) {
            viewHolder.setVisible(R.id.item_patrol_state, true)
                    .setTextColor(R.id.item_patrol_state, mContext.getResources().getColor(R.color.write_red))
                    .setBackgroundRes(R.id.item_patrol_state, R.drawable.state_red_bg)
                    .setText(R.id.item_patrol_state, "审核通过");

        } else {
            viewHolder.setVisible(R.id.item_patrol_state, true)
                    .setTextColor(R.id.item_patrol_state, mContext.getResources().getColor(R.color.green))
                    .setBackgroundRes(R.id.item_patrol_state, R.drawable.state_green_bg)
                    .setText(R.id.item_patrol_state, "审核中");
        }
        viewHolder.setText(R.id.item_patrol_name, "计划名称: " + item.getName())
                .setText(R.id.item_patrol_time, "执行日期: 2019-4-10" )
                .setText(R.id.item_patrol_content, "备注: 无" )
                .setText(R.id.item_patrol_creater,  item.getInspector());
    }
}