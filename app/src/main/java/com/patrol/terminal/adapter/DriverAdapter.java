package com.patrol.terminal.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.bean.DriverBean;

import java.util.List;

public class DriverAdapter extends BaseQuickAdapter<DriverBean, BaseViewHolder> {
    private int type = 1;

    public DriverAdapter(int layoutResId, @Nullable List<DriverBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DriverBean item) {
        TextView name = (TextView)viewHolder.getView( R.id.name);

        if (item.isCheck()){
            name.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            name.setTextColor(mContext.getResources().getColor(R.color.color_33));
        }

        if(item.getIs_use().equals("1")){
            name.setTextColor(mContext.getResources().getColor(R.color.my_info));
        }

        viewHolder.setText(R.id.name, item.getName());
    }
}