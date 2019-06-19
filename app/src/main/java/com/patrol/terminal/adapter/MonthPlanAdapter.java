package com.patrol.terminal.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

public class MonthPlanAdapter extends BaseQuickAdapter<MonthPlanBean, BaseViewHolder> {

    private String state;
    private String jobType;

    public MonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data, String state, String jovType) {
        super(layoutResId, data);
        this.state = state;
        this.jobType = jovType;
    }

    public MonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, MonthPlanBean item) {
        //添加子控件的点击事件
        viewHolder.addOnClickListener(R.id.plan_to_change)
                .addOnClickListener(R.id.plan_deal);
        //判断是检修计划还是运行计划
        if (item.getRepair_content() == null) {
            //图标
            TextView icon = viewHolder.getView(R.id.tv_icon);
            AdapterUtils.setIconText(icon, item.getDep_name());

            //线路
            viewHolder.setText(R.id.tv_line_name, item.getLine_name());

            //时间
            if (item.getStart_time() != null && item.getEnd_time() != null) {
                viewHolder.setText(R.id.tv_time, "时间：" + item.getStart_time() + " ~ " + item.getEnd_time());
                viewHolder.setVisible(R.id.tv_time, true);
            }

            //编辑
            ImageView edit = viewHolder.getView(R.id.iv_edit);
            AdapterUtils.setStatus(edit, item.getAudit_status());

            //审核状态
            HorizontalLineView horizontalLineView = viewHolder.getView(R.id.hlv_plan_state);
            horizontalLineView.setVisibility(View.VISIBLE);
            horizontalLineView.setStatus(item.getAudit_status());

            //计划类型
            TextView tvContent = viewHolder.getView(R.id.tv_content);
            AdapterUtils.setText(tvContent, StringUtil.getTypeSign(item.getType_sign()));
        } else {
            viewHolder.setText(R.id.tv_icon, "保")
                    .setBackgroundRes(R.id.tv_icon, R.drawable.plan_green_bg);
            //判断分配状态
            viewHolder.setText(R.id.tv_allot_status, "分配状态：");
            if ("0".equals(item.getAllot_status())) {
                viewHolder.setVisible(R.id.iv_edit, true);
                viewHolder.setText(R.id.tv_allot_status, "未分配");
                viewHolder.setTextColor(R.id.tv_allot_status, mContext.getResources().getColor(R.color.home_red));
            } else {
                viewHolder.setVisible(R.id.iv_edit, false);
                viewHolder.setText(R.id.tv_allot_status, "已分配");
                viewHolder.setTextColor(R.id.tv_allot_status, mContext.getResources().getColor(R.color.line_green));
            }
//            if (item.getStart_time() != null) {
//                String startTime = "停电开始时间：" + item.getStart_time();
//                viewHolder.setText(R.id.tv_start_time, startTime);
//            }
//            if (item.getEnd_time() != null) {
//                String endTime = "结束时间：" + item.getEnd_time();
//                viewHolder.setText(R.id.tv_end_time, endTime);
//            }
            viewHolder.setText(R.id.tv_line_name, "基于" + item.getLine_name() + "的保电计划");
        }
    }
}