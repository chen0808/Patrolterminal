package com.patrol.terminal.overhaul;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.OverhaulZzTaskBean;

import java.util.List;

import androidx.annotation.Nullable;

public class OvhaulHomeZzTaskAdapter extends BaseQuickAdapter<OverhaulZzTaskBean, BaseViewHolder> {

     private String year,month;

    public OvhaulHomeZzTaskAdapter(int layoutResId, @Nullable List<OverhaulZzTaskBean> data, String year, String month) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }

    public OvhaulHomeZzTaskAdapter(int layoutResId, @Nullable List<OverhaulZzTaskBean> data) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }
    @Override
    protected void convert(BaseViewHolder viewHolder, OverhaulZzTaskBean item) {
            viewHolder.setText(R.id.tv_title, item.getLine_name());

        //专责审核状态:  1:待班长分发   2:待负责人分发    3.已分发
        if ("1".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.state_title, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.state_title, "状态 : 待班长分发");
        }  else if ("2".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.state_title, mContext.getResources().getColor(R.color.green))
                    .setText(R.id.state_title, "状态 : 待负责人提交");
        }else if ("3".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.state_title, mContext.getResources().getColor(R.color.green))
                    .setText(R.id.state_title, "状态 : 负责人已提交");
        }else if ("4".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.state_title, mContext.getResources().getColor(R.color.green))
                    .setText(R.id.state_title, "状态 : 待班长审核");
        }else if ("5".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.state_title, mContext.getResources().getColor(R.color.green))
                    .setText(R.id.state_title, "状态 : 班长审核通过");
        }else if ("6".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.state_title, mContext.getResources().getColor(R.color.green))
                    .setText(R.id.state_title, "状态 : 班长审核不通过");
        }

    }

}