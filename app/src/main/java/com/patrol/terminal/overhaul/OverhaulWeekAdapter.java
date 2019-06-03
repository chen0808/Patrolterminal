package com.patrol.terminal.overhaul;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.OverhaulYearBean;

import java.util.List;

import androidx.annotation.Nullable;

public class OverhaulWeekAdapter extends BaseQuickAdapter<OverhaulYearBean, BaseViewHolder> {

    private String year, month;

    public OverhaulWeekAdapter(int layoutResId, @Nullable List<OverhaulYearBean> data, String year, String month) {
        super(layoutResId, data);
        this.year = year;
        this.month = month;
    }

    public OverhaulWeekAdapter(int layoutResId, @Nullable List<OverhaulYearBean> data) {
        super(layoutResId, data);
        this.year = year;
        this.month = month;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, OverhaulYearBean item) {
        viewHolder.setText(R.id.item_plan_date_tv, "周");
//        viewHolder.setVisible(R.id.plan_to_change, true);
        //viewHolder.setVisible(R.id.item_plan_status, false);
        if (item != null) {
            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name());
            viewHolder.setText(R.id.item_plan_content, item.getRepair_content());
            viewHolder.setText(R.id.item_start_time, "开始时间：" + item.getStart_time());
            viewHolder.setText(R.id.item_end_time, "结束时间：" + item.getEnd_time());
        }

        //HorizontalLineView view = viewHolder.getView(R.id.item_plan_status);

        //专责审核状态:  1:待专责分发   2:已分发
        if ("1".equals(item.getWeek_audit_status())) {
            viewHolder.setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 待分发");
        } else if ("2".equals(item.getWeek_audit_status())) {
            viewHolder.setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.green))
                    .setText(R.id.item_line_status, "状态 : 已分发");
        }

        //view.setStatus(item.getStatus());

    }

}