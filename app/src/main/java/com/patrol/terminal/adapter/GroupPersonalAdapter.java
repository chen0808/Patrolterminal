package com.patrol.terminal.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DepUserBean;

import java.util.List;

import androidx.annotation.Nullable;

public class GroupPersonalAdapter extends BaseQuickAdapter<DepUserBean, BaseViewHolder> {
    private int type = 1;

    public GroupPersonalAdapter(int layoutResId, @Nullable List<DepUserBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DepUserBean item) {
        TextView personalName = (TextView)viewHolder.getView( R.id.people_name);
        if (item.isCheck()==true){
            personalName.setTextColor(mContext.getResources().getColor(R.color.green));
        }else {
            personalName.setTextColor(mContext.getResources().getColor(R.color.my_info));
        }
        viewHolder.setText(R.id.people_name,item.getName());
    }
}