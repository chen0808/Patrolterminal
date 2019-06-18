package com.patrol.terminal.adapter;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.WeekListBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

public class NextWeekPlanAdapter extends BaseQuickAdapter<WeekListBean, BaseViewHolder> {

    private String jobType;

    public NextWeekPlanAdapter(int layoutResId, @Nullable List<WeekListBean> data, String state, String jovType) {
        super(layoutResId, data);
        this.jobType = jovType;
    }

    public NextWeekPlanAdapter(int layoutResId, @Nullable List<WeekListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, WeekListBean item) {
        //图标
        TextView icon = viewHolder.getView(R.id.tv_icon);
        AdapterUtils.setIconText(icon, item.getDep_name());

        //线路
        viewHolder.setText(R.id.tv_line_name,item.getLine_name());

        //时间
        if (item.getStart_time() != null && item.getEnd_time() != null) {
            viewHolder.setText(R.id.tv_time, "开始时间：" + item.getStart_time() + "   结束时间：" + item.getEnd_time());
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

    }

}