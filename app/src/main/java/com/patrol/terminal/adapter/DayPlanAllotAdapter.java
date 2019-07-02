package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.GroupOfDayBean;

import java.util.List;

import androidx.annotation.Nullable;

public class DayPlanAllotAdapter extends BaseQuickAdapter<GroupOfDayBean, BaseViewHolder> {
    private int type = 1;

    public DayPlanAllotAdapter(int layoutResId, @Nullable List<GroupOfDayBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, GroupOfDayBean item) {
        if (item.isCheck()==true){
            viewHolder.setImageResource(R.id.day_plan_check_iv, R.mipmap.circle);
        }else {
            viewHolder.setImageResource(R.id.day_plan_check_iv, R.mipmap.circle_no);
        }
        viewHolder.setText(R.id.day_plan_name,item.getLine_name()+item.getName()+"任务");
    }
}