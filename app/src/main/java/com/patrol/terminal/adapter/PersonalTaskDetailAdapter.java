package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class PersonalTaskDetailAdapter extends BaseQuickAdapter<PersonalTaskListBean, BaseViewHolder> {



    public PersonalTaskDetailAdapter(int layoutResId, @Nullable List<PersonalTaskListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PersonalTaskListBean item) {

         viewHolder.setText(R.id.item_plan_name,item.getTower_name()+ StringUtil.getTypeSign(item.getType_sign()) +"任务")
                 .setText(R.id.item_plan_time,"无");
    }

}