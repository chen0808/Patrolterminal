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

public class GroupTaskDetailAdapter extends BaseQuickAdapter<DefectBean, BaseViewHolder> {


    public GroupTaskDetailAdapter(int layoutResId, @Nullable List<DefectBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, DefectBean item) {

        viewHolder.setText(R.id.item_plan_time, StringUtil.getGroupState(item.getType()+""));
        viewHolder.setTextColor(R.id.item_plan_time,mContext.getResources().getColor(StringUtil.getPersonalColor(item.getType()+"")));
        TextView tvContent = viewHolder.getView(R.id.item_plan_name);
        AdapterUtils.setText(tvContent, item.getContent());
    }
}
