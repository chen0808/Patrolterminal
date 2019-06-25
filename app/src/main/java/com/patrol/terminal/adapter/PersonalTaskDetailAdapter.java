package com.patrol.terminal.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;

import java.sql.Struct;
import java.util.List;

public class PersonalTaskDetailAdapter extends BaseQuickAdapter<PersonalTaskListBean, BaseViewHolder> {


    public PersonalTaskDetailAdapter(int layoutResId, @Nullable List<PersonalTaskListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PersonalTaskListBean item) {
        viewHolder.setText(R.id.item_plan_time, StringUtil.getPersonalState(item.getAudit_status()));
        viewHolder.setTextColor(R.id.item_plan_time,mContext.getResources().getColor(StringUtil.getPersonalColor(item.getAudit_status())));
        TextView tvContent = viewHolder.getView(R.id.item_plan_name);
        if (item.getTower_name() != null) {
            AdapterUtils.setText(tvContent, item.getTower_name() + StringUtil.getTypeSign(item.getType_sign()) + "任务");
        } else {
            AdapterUtils.setText(tvContent, StringUtil.getTypeSign(item.getType_sign()) + "任务");
        }

    }

}