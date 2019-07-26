package com.patrol.terminal.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class GroupTaskPersonalAdapter extends BaseQuickAdapter<PersonalTaskListBean, BaseViewHolder> {


    public GroupTaskPersonalAdapter(int layoutResId, @Nullable List<PersonalTaskListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PersonalTaskListBean item) {
        TextView tvContent = viewHolder.getView(R.id.item_plan_name);
        if (item.getTower_name() != null) {
            AdapterUtils.setText(tvContent, item.getTower_name() + StringUtil.getTypeSign(item.getType_sign()) + "任务");
        } else {
            AdapterUtils.setText(tvContent, StringUtil.getTypeSign(item.getType_sign()) + "任务");
        }

        viewHolder.setText(R.id.item_plan_time, StringUtil.getPersonalState(item.getAudit_status()));
        viewHolder.setTextColor(R.id.item_plan_time, mContext.getResources().getColor(StringUtil.getPersonalColor(item.getAudit_status())));
    }
}
