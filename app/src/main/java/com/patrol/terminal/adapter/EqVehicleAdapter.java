package com.patrol.terminal.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.EqVehicleBean;

import java.util.List;

public class EqVehicleAdapter extends BaseQuickAdapter<EqVehicleBean, BaseViewHolder> {
    private int type = 1;

    public EqVehicleAdapter(int layoutResId, @Nullable List<EqVehicleBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, EqVehicleBean item) {
        TextView carNumber = (TextView)viewHolder.getView( R.id.car_number);

        if (item.isCheck()){
            carNumber.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            carNumber.setTextColor(mContext.getResources().getColor(R.color.color_33));
        }

        if(item.getIs_use().equals("1")){
            carNumber.setTextColor(mContext.getResources().getColor(R.color.my_info));
        }

        viewHolder.setText(R.id.car_number, item.getCar_number());
    }
}