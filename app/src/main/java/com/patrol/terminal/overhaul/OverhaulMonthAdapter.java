package com.patrol.terminal.overhaul;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

import androidx.annotation.Nullable;

public class OverhaulMonthAdapter extends BaseQuickAdapter<OverhaulYearBean, BaseViewHolder> {

     private String year,month;

    public OverhaulMonthAdapter(int layoutResId, @Nullable List<OverhaulYearBean> data, String year, String month) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }

    public OverhaulMonthAdapter(int layoutResId,  @Nullable List<OverhaulYearBean> data) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, OverhaulYearBean item) {
        viewHolder.setText(R.id.item_plan_date_tv, "月");
        viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_mon_bg);

        viewHolder.setVisible(R.id.plan_to_change, false);
        //viewHolder.setVisible(R.id.item_plan_status, false);
        viewHolder.setText(R.id.item_plan_device_name, item.getLine_name());
        viewHolder.setText(R.id.item_plan_content, item.getRepair_content());
        viewHolder.setText(R.id.item_start_time, "开始时间：" + item.getStart_time());
        viewHolder.setText(R.id.item_end_time, "结束时间：" + item.getEnd_time());

       // HorizontalLineView view = viewHolder.getView(R.id.item_plan_status);
        //月检修计划:0:编制  1:待主管审核   2:审核通过    3:审核不通过
        if ("0".equals(item.getMonth_audit_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 待提交审核");
            viewHolder.setVisible(R.id.plan_to_change, true);   //提交审核后不可修改
        } else if ("1".equals(item.getMonth_audit_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 待主管审核");

        } else if ("2".equals(item.getMonth_audit_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 审核通过");
        } else if ("3".equals(item.getMonth_audit_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.green))
                    .setText(R.id.item_line_status, "状态 : 审核不通过");
        }
        //view.setOverStatus(item.getMonth_audit_status());
    }

}