package com.patrol.terminal.overhaul;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.OverhaulMonthBean;

import java.util.List;

import androidx.annotation.Nullable;

public class OverhaulWeekTaskAdapter extends BaseQuickAdapter<OverhaulMonthBean, BaseViewHolder> {

     private String year,month;

    public OverhaulWeekTaskAdapter(int layoutResId, @Nullable List<OverhaulMonthBean> data, String year, String month) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }
    public OverhaulWeekTaskAdapter(int layoutResId, @Nullable List<OverhaulMonthBean> data) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }



    @Override
    protected void convert(BaseViewHolder viewHolder, OverhaulMonthBean item) {
        viewHolder.setText(R.id.item_plan_date_tv, "周");
        viewHolder.setVisible(R.id.plan_to_change, false);
        //viewHolder.setVisible(R.id.item_plan_status, false);
        if (item != null) {
            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name());
            viewHolder.setText(R.id.item_plan_content, item.getTask_content());
            viewHolder.setText(R.id.item_start_time, "开始时间：" + item.getStart_time());
            viewHolder.setText(R.id.item_end_time, "结束时间：" + item.getEnd_time());
        }

        //HorizontalLineView view = viewHolder.getView(R.id.item_plan_status);

        if ("1".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 待班长分发");
        } else if ("2".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 待负责人提交");
        } else if ("3".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 已提交");
        }

        //view.setStatus(item.getStatus());

    }

}