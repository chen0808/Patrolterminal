package com.patrol.terminal.overhaul;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulYearBean;

import java.util.List;

import androidx.annotation.Nullable;

public class OverhaulYearAdapter extends BaseQuickAdapter<OverhaulYearBean, BaseViewHolder> {

     private String year,month;

    public OverhaulYearAdapter(int layoutResId, @Nullable List<OverhaulYearBean> data, String year, String month) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }

    public OverhaulYearAdapter(int layoutResId, @Nullable List<OverhaulYearBean> data) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }
    @Override
    protected void convert(BaseViewHolder viewHolder, OverhaulYearBean item) {
            viewHolder.setText(R.id.item_plan_date_tv, "年");
            //viewHolder.setVisible(R.id.plan_to_change, false);
            //viewHolder.setVisible(R.id.item_plan_status, false);
            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name());
            viewHolder.setText(R.id.item_plan_content, item.getRepair_content());
            viewHolder.setText(R.id.item_start_time, "开始时间：" + item.getStart_time());
            viewHolder.setText(R.id.item_end_time, "结束时间：" + item.getEnd_time());



//                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_mon_bg);
//        viewHolder.setVisible(R.id.item_line_state, false);
//        HorizontalLineView view = viewHolder.getView(R.id.item_plan_status);
//        if ("0".equals(item.getAudit_status())) {
//            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
//                    .setText(R.id.item_line_status, "状态 : 审核中");
//        } else if ("1".equals(item.getAudit_status())) {
//            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.green))
//                    .setText(R.id.item_line_status, "状态 : 审核通过");
//        } else if ("2".equals(item.getAudit_status())) {
//            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.green))
//                    .setText(R.id.item_line_status, "状态 : 审核不通过");
//        }
//        view.setStatus(item.getAudit_status());
//        viewHolder.setText(R.id.item_plan_name,item.getPlan_name()+item.getLine_name())
//                .setText(R.id.item_plan_type,"类型 : "+item.getType_name());
    }

}