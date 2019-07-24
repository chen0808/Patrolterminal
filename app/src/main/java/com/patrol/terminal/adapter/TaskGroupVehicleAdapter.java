package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.bean.TeamAndVehicleBean;

import java.util.List;

public class TaskGroupVehicleAdapter extends BaseQuickAdapter<TeamAndVehicleBean, BaseViewHolder> {
    private int type = 1;

    public TaskGroupVehicleAdapter(int layoutResId, @Nullable List<TeamAndVehicleBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, TeamAndVehicleBean item) {
        if (item.isCheck() == true){
            viewHolder.setImageResource(R.id.day_plan_check_iv, R.mipmap.circle);
        } else {
            viewHolder.setImageResource(R.id.day_plan_check_iv, R.mipmap.circle_no);
        }
        viewHolder.setText(R.id.car_number, item.getCar_number());
        viewHolder.setText(R.id.driver_name, item.getDriver_name());
        viewHolder.setText(R.id.group_name, item.getGroup_name());
    }
}