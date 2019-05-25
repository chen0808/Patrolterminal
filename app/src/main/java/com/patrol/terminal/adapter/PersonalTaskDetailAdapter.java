package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.PlanTypeBean;

import java.util.List;

import androidx.annotation.Nullable;

public class PersonalTaskDetailAdapter extends BaseQuickAdapter<PlanTypeBean, BaseViewHolder> {



    public PersonalTaskDetailAdapter(int layoutResId, @Nullable List<PlanTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PlanTypeBean item) {

         viewHolder.setText(R.id.item_plan_name,item.getName())
                 .setText(R.id.item_plan_time,item.getTime());
    }

}