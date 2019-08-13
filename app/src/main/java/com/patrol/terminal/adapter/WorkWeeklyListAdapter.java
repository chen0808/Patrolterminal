package com.patrol.terminal.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.EngineeringBriefAddActivity;
import com.patrol.terminal.activity.WorkWeeklyAddActivity;
import com.patrol.terminal.bean.LocalGcjbBean;
import com.patrol.terminal.bean.LocalWorkWeeklyBean;
import com.patrol.terminal.utils.Constant;

import java.util.List;

/**
 * 周报 list
 */
public class WorkWeeklyListAdapter extends BaseQuickAdapter<LocalWorkWeeklyBean, BaseViewHolder> {
    public WorkWeeklyListAdapter(int layoutResId, @Nullable List<LocalWorkWeeklyBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, LocalWorkWeeklyBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkWeeklyAddActivity.class);
                Constant.isEditStatus = true;
                Bundle bundle = new Bundle();
                bundle.putSerializable("workBean",item);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        helper.setText(R.id.weekly_content,item.getWork_bzzj())
                .setText(R.id.weekly_tbr,item.getUser_name())
                .setText(R.id.weekly_date,item.getWork_date());

    }
}
