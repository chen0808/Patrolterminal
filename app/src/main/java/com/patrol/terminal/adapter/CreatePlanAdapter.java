package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.MonthPlanBean;

import java.util.List;

import androidx.annotation.Nullable;

public class CreatePlanAdapter extends BaseQuickAdapter<MonthPlanBean, BaseViewHolder> {


    public CreatePlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MonthPlanBean item) {
      viewHolder.setText(R.id.item_carete_plan_name,"2019年4月桃开一线计划");
        viewHolder.addOnClickListener(R.id.item_carete_plan_check);
    }

}