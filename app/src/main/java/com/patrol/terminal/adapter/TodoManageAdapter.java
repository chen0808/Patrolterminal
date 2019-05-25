package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.OvaTodoBean;

import java.util.List;

import androidx.annotation.Nullable;

public class TodoManageAdapter extends BaseQuickAdapter<OvaTodoBean, BaseViewHolder> {


    public TodoManageAdapter(int layoutResId, @Nullable List<OvaTodoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, OvaTodoBean item) {
        OvaTodoBean.PlanRepairBean planRepair = item.getPlanRepair();
        viewHolder.setText(R.id.item_todo_type_tv,"检");
        if (planRepair != null) {
            viewHolder.setText(R.id.item_todo_name_01, planRepair.getRepair_content());
            viewHolder.setText(R.id.item_todo_name_02, planRepair.getLine_name());
        }
    }

}