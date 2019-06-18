package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.LineCheckActivity;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

public class NextMonthPlanAdapter extends BaseQuickAdapter<MonthPlanBean, BaseViewHolder> {

    private String jobType;

    public NextMonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data, String jobType) {
        super(layoutResId, data);
        this.jobType = jobType;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MonthPlanBean item) {
        //添加子控件的点击事件
        viewHolder.addOnClickListener(R.id.iv_edit);
        if (item.getRepair_content() == null) {
            //图标
            TextView icon = viewHolder.getView(R.id.tv_icon);
            AdapterUtils.setIconText(icon, item.getDep_name());

            //线路
            viewHolder.setText(R.id.tv_line_name, item.getLine_name());

            //时间
            if (item.getStart_time() != null && item.getEnd_time() != null) {
                viewHolder.setText(R.id.tv_time, "开始时间：" + item.getStart_time() + "   结束时间：" + item.getEnd_time());
            } else {
                viewHolder.setVisible(R.id.tv_time, true);
            }

            //编辑
            ImageView edit = viewHolder.getView(R.id.iv_edit);
            AdapterUtils.setStatus(edit, item.getAudit_status());

            //审核状态
            HorizontalLineView horizontalLineView = viewHolder.getView(R.id.hlv_plan_state);
            horizontalLineView.setStatus(item.getAudit_status());

            //计划类型
            TextView tvContent = viewHolder.getView(R.id.tv_content);
            AdapterUtils.setText(tvContent, StringUtil.getTypeSign(item.getType_sign()));
        } else {
            viewHolder.setText(R.id.item_plan_date_tv, "保")
                    .setVisible(R.id.plan_to_change, true);
            viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LineCheckActivity.class);
                    intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("year", item.getYear());
                    intent.putExtra("month", item.getMonth());
                    mContext.startActivity(intent);
                }
            });
            View view = viewHolder.getView(R.id.item_plan_status);
            view.setVisibility(View.GONE);
            viewHolder.setText(R.id.item_plan_device_name, item.getRepair_content())
                    .setText(R.id.item_plan_content, "停电区域 : " + item.getBlackout_range())
                    .setText(R.id.item_line_status, "停电时间 : " + item.getStart_time() + " - " + item.getEnd_time());
        }
    }
}